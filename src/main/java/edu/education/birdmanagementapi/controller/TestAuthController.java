package edu.education.birdmanagementapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de prueba para verificar la configuracin de Spring Security.
 */
@RestController
@RequestMapping("/auth")
public class TestAuthController {


    /**
     * Endpoint pblico. Permite verificar la accesibilidad de la aplicacin sin autenticacin.
     *
     * @return Mensaje de bienvenida.
     */
    @GetMapping("/test")
    public String hello() {
        return "Hello world";
    }

    /**
     * Endpoint asegurado. Requiere autenticacin y el permiso 'READ' (o rol asociado).
     *
     * @return Mensaje de bienvenida asegurado.
     */
    @GetMapping("/test-secured")
    public String helloSecured() {
        return "Hello world secured";
    }
}
