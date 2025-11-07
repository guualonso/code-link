package com.codelink.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {
    @NotBlank
    private String nome;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(min = 6)
    private String senha;

    private String bio;
    private Long fotoPerfil;

    // getters e setters
}