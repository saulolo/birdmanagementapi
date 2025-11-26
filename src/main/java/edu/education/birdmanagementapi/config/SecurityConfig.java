package edu.education.birdmanagementapi.config;

import edu.education.birdmanagementapi.config.filter.JwtTokenValidator;
import edu.education.birdmanagementapi.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuracin principal de Spring Security para la API Gestión de Avistamientos de Aves.
 * Define las reglas de autorizacin, el mtodo de autenticacin (Basic Auth) y el codificador de contraseas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define la cadena de filtros de seguridad HTTP, incluyendo la gestin de CSRF, autenticacin,
     * poltica de sesin y reglas de autorizacin por URL.
     *
     * @param httpSecurity Objeto HttpSecurity para configurar la seguridad a nivel HTTP.
     * @return SecurityFilterChain configurada.
     * @throws Exception Si ocurre un error durante la configuracin.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtTokenValidator jwtTokenValidator) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {

                    /*---------------- Configuración de endpoints PÚBLICOS ----------------*/
                    http.requestMatchers(HttpMethod.GET, "/method/test").permitAll();
                    http.requestMatchers("/swagger-ui/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/users/create").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll();

                    // Permiso para view Sighting, es publico/INVITED
                    http.requestMatchers(HttpMethod.GET, "/sightings").hasAnyAuthority("READ", "INVITED");

                    /*---------------- Configuración de endpoints PRIVADOS (por Permiso o Rol) ----------------*/

                    // Endpoint de prueba asegurado
                    http.requestMatchers(HttpMethod.GET, "/method/test-secured").hasAuthority("READ");

                    // Endpoints de Sighting
                    http.requestMatchers(HttpMethod.POST, "/sightings").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/sightings/{id}").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/sightings/{id}").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/sightings/birds/{idBird}/sightings").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/sightings/users/{idUser}/sightings").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/sightings/countries/{idCountry}/sightings").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/sightings/habitats/{idHabitat}/sightings").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/sightings/date-range").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.DELETE, "/sightings/{id}").hasAnyRole("ADMIN", "DEVELOPER");

                    // Endpoints de families
                    http.requestMatchers(HttpMethod.POST, "/families").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/families/{id}").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/families").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/families/{id}").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/families/by-name").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.DELETE, "/families/{id}").hasAnyRole("ADMIN", "DEVELOPER");

                    // Endpoints de birds
                    http.requestMatchers(HttpMethod.POST, "/birds").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/birds/{id}").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/birds").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/birds/{id}").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/birds/by-name").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.DELETE, "/birds/{id}").hasAnyRole("ADMIN", "DEVELOPER");

                    // Endpoints de habitats
                    http.requestMatchers(HttpMethod.POST, "/habitats").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/habitats/{id}").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/habitats").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/habitats/{id}").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.GET, "/habitats/by-name").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.DELETE, "/habitats/{id}").hasAnyRole("ADMIN", "DEVELOPER");

                    // Endpoints de users (Gestión - Requiere permisos altos)
                    http.requestMatchers("/users/**").hasAnyRole("ADMIN", "DEVELOPER");

                    /*---------------- Configuración del resto de endpoints ----------------*/
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(jwtTokenValidator, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Bean que proporciona el AuthenticationManager, utilizado para realizar la autenticacin.
     *
     * @param authenticationConfiguration La configuracin base de autenticacin.
     * @return El AuthenticationManager.
     * @throws Exception Si no se puede obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Define el proveedor de autenticacin que utiliza el UserDetailsService
     * personalizado y el PasswordEncoder.
     *
     * @param userDetailsService El servicio para cargar los detalles del usuario.
     * @return El proveedor de autenticacin basado en DAO.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     * Define el codificador de contraseas utilizado en toda la aplicacin,
     * asegurando que las contraseas se almacenen como hashes BCrypt.
     *
     * @return BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*    public static void main(String[] args) {
    //Encriptador
        System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }*/

}
