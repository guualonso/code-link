package com.codelink.mapper;

import com.codelink.dto.UsuarioCreateDTO;
import com.codelink.dto.UsuarioDTO;
import com.codelink.model.Usuario;

public class UsuarioMapper {
    public static Usuario toEntity(UsuarioCreateDTO dto){
        if(dto == null) return null;
        Usuario u = new Usuario();
        u.setNome(dto.getNome());
        u.setEmail(dto.getEmail());
        u.setSenha(dto.getSenha());
        u.setBio(dto.getBio());
        u.setFotoPerfil(dto.getFotoPerfil());
        return u;
    }

    public static UsuarioDTO toDTO(Usuario u){
        if(u == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNome(u.getNome());
        dto.setEmail(u.getEmail());
        dto.setBio(u.getBio());
        dto.setFotoPerfil(u.getFotoPerfil());
        return dto;
    }
}