package com.desastres.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.desastres.model.Usuario;
import com.desastres.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
        Usuario createdUser = userService.createUser(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone());
        return ResponseEntity.ok(createdUser);
    }
}

@RestController
@RequestMapping("/api/auth")
class AuthController {
    
    @Autowired
    private UserService userService;
    
    private static final String SECRET_KEY = "chave_secreta";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario loginRequest) {
        Usuario usuario = userService.authenticate(loginRequest.getEmail(), loginRequest.getSenha());
        if (usuario != null) {
        	String token = Jwts.builder()
        	        .setSubject(usuario.getEmail())
        	        .setIssuedAt(new Date())
        	        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        	        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        	        .compact();
        	return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
