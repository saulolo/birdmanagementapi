package edu.education.birdmanagementapi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtils {


    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    /**
     * Crea y firma un JWT; el subject es el username.
     *
     * @param authentication Authentication (principal o name).
     * @return Token JWT firmado.
     */
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = authentication.getName();
        }

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1_800_000)) // 30 min
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }

    /**
     * Valida el token y devuelve el DecodedJWT si es válido.
     * Si no es válido devuelve null (no lanza excepción) y registra la causa.
     */
    public DecodedJWT validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .acceptLeeway(2)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            log.warn("JWT inválido/expirado: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Error validando JWT", e);
            return null;
        }
    }

    /**
     * Extrae el subject (username) de un DecodedJWT.
     *
     * @param decodedJWT DecodedJWT.
     * @return username o null si decodedJWT es null.
     */
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT == null ? null : decodedJWT.getSubject();
    }

    /**
     * Obtiene una claim específica del token.
     *
     * @param decodedJWT DecodedJWT.
     * @param claimName  Nombre de la claim.
     * @return Claim o null si no existe.
     */
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT == null ? null : decodedJWT.getClaim(claimName);
    }

    /**
     * Retorna todas las claims del token en un Map.
     *
     * @param decodedJWT DecodedJWT.
     * @return Map de claims, vacío si decodedJWT es null.
     */
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT == null ? Map.of() : decodedJWT.getClaims();
    }

}
