package com.codelink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.codelink.dto.RegisterDTO;
import com.codelink.requests.AuthRequest;
import com.codelink.requests.AuthResponse;
import com.codelink.service.UsuarioService;
import com.codelink.security.JwtUtil;
import com.codelink.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
                         JwtUtil jwtUtil,
                         UsuarioService usuarioService,
                         UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
        // garantir que registro público não defina role ADMIN
        dto.setRole(null);
        var userDto = usuarioService.criar(dto);
        return ResponseEntity.status(201).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        // autentica (lança AuthenticationException em caso de credenciais inválidas)
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getSenha()));

        // buscar entidade Usuario no banco para pegar id/role/nome etc
        com.codelink.model.Usuario usuario = usuarioRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String token = jwtUtil.generateTokenFromUser(usuario);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}