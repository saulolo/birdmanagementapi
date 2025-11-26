package edu.education.birdmanagementapi.service.impl;

import edu.education.birdmanagementapi.domain.dto.request.AuthCreateUserDTO;
import edu.education.birdmanagementapi.domain.dto.request.AuthLoginRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.AuthResponseDTO;
import edu.education.birdmanagementapi.domain.entity.Role;
import edu.education.birdmanagementapi.domain.entity.User;
import edu.education.birdmanagementapi.repository.RoleRepository;
import edu.education.birdmanagementapi.repository.UserRepository;
import edu.education.birdmanagementapi.util.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementacin personalizada de Spring Security UserDetailsService.
 * Responsable de cargar los detalles del usuario, incluyendo roles y permisos,
 * desde la base de datos para el proceso de autenticacin.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * Carga el usuario por su nombre de usuario.
     * Mapea la entidad User a un objeto UserDetails de Spring Security,
     * incluyendo sus Roles (con prefijo ROLE_) y Permisos.
     *
     * @param username El nombre de usuario que intenta autenticarse.
     * @return Los detalles del usuario (UserDetails).
     * @throws UsernameNotFoundException Si el usuario no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("EL usuario: " + username + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        user.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNoExpired(),
                user.isCredentialNoExpired(),
                user.isAccountNoLocked(),
                authorityList);
    }

    /**
     * Autentica al usuario con username/password y devuelve AuthResponseDTO con JWT.
     *
     * @param authLoginRequest DTO con credenciales.
     * @return AuthResponseDTO con token y mensaje.
     */
    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponseDTO(username, "Usuario logeado exitosamente.", accessToken, true);
    }

    /**
     * Valida credenciales y retorna una Authentication con UserDetails como principal.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña en claro.
     * @return Authentication válida.
     * @throws BadCredentialsException si credenciales inválidas.
     */
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Usuario o password inválidos.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password inválido.");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * Crea un nuevo usuario, lo persiste y devuelve AuthResponseDTO con JWT.
     *
     * @param authCreateUserDTO DTO con datos del usuario a crear.
     * @return AuthResponseDTO con token y mensaje.
     * @throws IllegalArgumentException si los roles no existen.
     */
    public AuthResponseDTO createUser (AuthCreateUserDTO authCreateUserDTO) {
        String username = authCreateUserDTO.username();
        String password = authCreateUserDTO.password();
        List<String> roleRequest = authCreateUserDTO.roleRequestDTO().roleListName();

        Set<Role> roleSet = roleRepository.findByRoleEnumIn(roleRequest)
                .stream()
                .collect(Collectors.toSet());

        if (roleSet.isEmpty()) {
            throw new IllegalArgumentException("Los roles especificados no existen.");
        }

        User user = User.builder()
                .name(authCreateUserDTO.name())
                .username(username)
                .email(authCreateUserDTO.email())
                .password(passwordEncoder.encode(password))
                .roles(roleSet)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        User userCreated = userRepository.save(user);

        UserDetails userDetails = loadUserByUsername(userCreated.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponseDTO(userCreated.getUsername(), "Usuario creado correctamente.", accessToken, true);
    }

}
