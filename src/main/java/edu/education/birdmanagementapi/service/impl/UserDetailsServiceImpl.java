package edu.education.birdmanagementapi.service.impl;

import edu.education.birdmanagementapi.domain.entity.User;
import edu.education.birdmanagementapi.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementacin personalizada de Spring Security UserDetailsService.
 * Responsable de cargar los detalles del usuario, incluyendo roles y permisos,
 * desde la base de datos para el proceso de autenticacin.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
