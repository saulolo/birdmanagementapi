package edu.education.birdmanagementapi.controller;

import edu.education.birdmanagementapi.domain.dto.request.AuthCreateUserDTO;
import edu.education.birdmanagementapi.domain.dto.request.AuthLoginRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.AuthResponseDTO;
import edu.education.birdmanagementapi.service.impl.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Registra un nuevo usuario y retorna AuthResponseDTO (incluye JWT).
     *
     * @param authCreateUserDTO DTO con datos del nuevo usuario.
     * @return ResponseEntity con AuthResponseDTO y status 201.
     */
    @PostMapping("register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid AuthCreateUserDTO authCreateUserDTO) {
        return new ResponseEntity<>(userDetailsService.createUser(authCreateUserDTO), HttpStatus.CREATED);
    }

    /**
     * Autentica credenciales y retorna AuthResponseDTO con el token.
     *
     * @param userRequest DTO con username y password.
     * @return ResponseEntity con AuthResponseDTO y status 200.
     */
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest) {
        return new ResponseEntity<>(userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }

}
