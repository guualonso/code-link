package com.codelink.dto;

import com.codelink.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String bio;
    private Long fotoPerfil;
    private Role role;
    // getters/setters
}
