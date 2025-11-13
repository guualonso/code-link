package com.codelink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComunidadeCreateDTO {
    @NotBlank
    private String nome;
    private String descricao;
    private List<Long> membroIds;
    // getters/setters
}