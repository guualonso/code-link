package com.codelink.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codelink.dto.RegisterDTO;
import com.codelink.dto.UsuarioDTO;
import com.codelink.exception.ResourceNotFoundException;
import com.codelink.mapper.UsuarioMapper;
import com.codelink.model.Role;
import com.codelink.model.Usuario;
import com.codelink.repository.UsuarioRepository;
import com.codelink.security.JwtUtil;

@Service
@Transactional
public class AuthService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository repo,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /** registra usuário (público) - força ROLE_USER se role for enviada */
    public UsuarioDTO register(RegisterDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario u = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .bio(null)
                .fotoPerfil(null)
                .role(Role.ROLE_USER) // força USER no cadastro público
                .build();

        Usuario salvo = repo.save(u);
        return UsuarioMapper.toDTO(salvo);
    }

    /** autentica e retorna token JWT */
    public String login(String email, String senha) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
        return jwtUtil.generateToken(email);
    }

    /** cria admin manualmente (use apenas em setup/dev) */
    public UsuarioDTO createAdminIfNotExists(String adminEmail, String adminPassword) {
        if (repo.existsByEmail(adminEmail)) {
            Usuario exist = repo.findByEmail(adminEmail).orElseThrow(() -> new ResourceNotFoundException("Admin não existe"));
            return UsuarioMapper.toDTO(exist);
        }
        Usuario u = Usuario.builder()
                .nome("Admin")
                .email(adminEmail)
                .senha(passwordEncoder.encode(adminPassword))
                .role(Role.ROLE_ADMIN)
                .build();
        Usuario salvo = repo.save(u);
        return UsuarioMapper.toDTO(salvo);
    }
}
