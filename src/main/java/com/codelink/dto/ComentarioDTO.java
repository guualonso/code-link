package com.codelink.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioDTO {
    private Long id;
    private String texto;
    private LocalDateTime dataComentario;
    private UsuarioDTO autor;
    // getters/setters
}