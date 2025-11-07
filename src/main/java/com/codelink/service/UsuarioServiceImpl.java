package com.codelink.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.relation.RelationNotFoundException;

import org.springframework.stereotype.Service;

import com.codelink.dto.UsuarioCreateDTO;
import com.codelink.dto.UsuarioDTO;
import com.codelink.mapper.UsuarioMapper;
import com.codelink.model.Usuario;
import com.codelink.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repo;
    private UsuarioCreateDTO dto;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UsuarioDTO criar(UsuarioCreateDTO dto) {
        // aqui você pode validar email duplicado, hashear senha, etc.
        Usuario u = UsuarioMapper.toEntity(dto);
        Usuario salvo = repo.save(u);
        return UsuarioMapper.toDTO(salvo);
    }

    @Override
    public UsuarioDTO buscarPorId(Long id) {
        Usuario u = repo.findById(id).orElseThrow(() -> new RelationNotFoundException("Usuario não encontrado"));
        return UsuarioMapper.toDTO(u);
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return repo.findAll().stream().map(UsuarioMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO atualizar(Long id, UsuarioCreateDTO dto) {
        Usuario existente = repo.findById(id).orElseThrow(() -> new RelationNotFoundException("Usuario não encontrado"));
        existente.setNome(dto.getNome());
        existente.setBio(dto.getBio());
        // não sobrescreva a senha/email se não quiser: exemplo simples
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            existente.setSenha(dto.getSenha());
        }
        Usuario atualizado = repo.save(existente);
        return UsuarioMapper.toDTO(atualizado);
    }

    @Override
    public void deletar(Long id) {
        if(!repo.existsById(id)) new RelationNotFoundException("Usuario não encontrado");
        repo.deleteById(id);
    
        this.dto = dto;
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'criar'");
    }
    
}
