package edu.education.birdmanagementapi.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import edu.education.birdmanagementapi.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {


    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;


    /**
     * Filtrado que valida el token JWT presente en el header Authorization (Bearer &lt;token&gt;).
     * Si el token es válido establece la Authentication en el SecurityContext; primero intenta
     * cargar UserDetails desde la base de datos y, si falla, realiza un fallback usando el
     * username y las autorizaciones extraídas del token.
     *
     * @param request     Petición HTTP entrante.
     * @param response    Respuesta HTTP.
     * @param filterChain Cadena de filtros a invocar tras este filtro.
     * @throws ServletException Si ocurre un error de servlet durante el filtrado.
     * @throws IOException      Si ocurre un error de I/O durante el filtrado.
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String jwtToken = header.substring(7).trim();

            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            if (decodedJWT != null) {
                String username = jwtUtils.extractUsername(decodedJWT);
                String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities") != null
                        ? jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString()
                        : "";

                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    log.warn("No se pudo cargar UserDetails (fallback to username): {}", e.getMessage());
                    Collection<? extends GrantedAuthority> authorities =
                            AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                log.debug("Token JWT inválido o expirado para la petición {}", request.getRequestURI());
            }
        }

        filterChain.doFilter(request, response);
    }
}