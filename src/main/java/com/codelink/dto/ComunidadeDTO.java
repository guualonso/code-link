package com.codelink.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComunidadeDTO {
    private Long id;
    private String nome;
    private String descricao;
    private List<UsuarioDTO> membros;
    // getters/setters
}