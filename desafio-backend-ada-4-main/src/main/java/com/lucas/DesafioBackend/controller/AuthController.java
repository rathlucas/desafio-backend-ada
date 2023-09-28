package com.lucas.DesafioBackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Tag(name = "Login", description = "Autenticação de usuários internos.")
@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    AuthenticationConfiguration configuration;

    @Autowired
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request, @RequestParam("username") String username,
                                        @RequestParam("password") String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication authentication = configuration
                    .getAuthenticationManager().authenticate(authenticationToken);

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);
        } catch (Exception e) {
            Logger.getLogger("error").warning(e.getMessage());
            return new ResponseEntity<>("Ocorreu um erro inesperado!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Usuário " + username + "autenticado com sucesso!", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> performLogout(Authentication authentication,
                                                HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return new ResponseEntity<>("Usuário deslogado com sucesso!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam("username") String username,
                                           @RequestParam("password") String password) {

        String encodedPassword = passwordEncoder.encode(password);
        UserDetails user = User
                .withUsername(username)
                .password(encodedPassword)
                .roles("USER")
                .build();

        inMemoryUserDetailsManager.createUser(user);
        return new ResponseEntity<>("Usuário " + username + " criado com sucesso!", HttpStatus.OK);
    }
}
