package com.codelink.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String conteudo;
    private String imagem;
    private LocalDateTime dataPostagem;
    private int likes;
    private UsuarioDTO autor;
    private Long comunidadeId;
    private List<ComentarioDTO> comentarios;
    // getters/setters
}