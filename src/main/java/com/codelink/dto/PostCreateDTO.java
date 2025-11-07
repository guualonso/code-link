package com.codelink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateDTO {
    @NotBlank
    private String conteudo;
    private String imagem;
    private Long autorId;
    private Long comunidadeId; // opcional

    // getters/setters
}