package com.codelink.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codelink.dto.RegisterDTO;
import com.codelink.dto.UsuarioCreateDTO;
import com.codelink.dto.UsuarioDTO;
import com.codelink.exception.ResourceNotFoundException;
import com.codelink.mapper.UsuarioMapper;
import com.codelink.model.Role;
import com.codelink.model.Usuario;
import com.codelink.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Método usado pelo controller /api/usuarios (CRUD interno) ---
    @Override
    public UsuarioDTO criar(UsuarioCreateDTO dto) {
        Usuario u = UsuarioMapper.toEntity(dto);
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            u.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        // se o DTO tiver role, mapear aqui; caso contrário, manter default da entidade
        if (u.getRole() == null) {
            u.setRole(Role.ROLE_USER);
        }
        Usuario salvo = repo.save(u);
        return UsuarioMapper.toDTO(salvo);
    }

    // --- Método usado pelo endpoint público de registro (/api/auth/register) ---
    @Override
    public UsuarioDTO criar(RegisterDTO dto) {
        // validações básicas
        if (repo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario u = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .bio(null)
                .fotoPerfil(null)
                // força ROLE_USER para registro público; se quiser permitir role via admin, faça outro método
                .role(Role.ROLE_USER)
                .build();

        Usuario salvo = repo.save(u);
        return UsuarioMapper.toDTO(salvo);
    }

    @Override
    public UsuarioDTO buscarPorId(Long id) {
        Usuario u = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        return UsuarioMapper.toDTO(u);
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return repo.findAll().stream().map(UsuarioMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO atualizar(Long id, UsuarioCreateDTO dto) {
        Usuario existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));

        existente.setNome(dto.getNome());
        existente.setBio(dto.getBio());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            existente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        Usuario atualizado = repo.save(existente);
        return UsuarioMapper.toDTO(atualizado);
    }

    @Override
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com id: " + id);
        }
        repo.deleteById(id);
    }

    
}
