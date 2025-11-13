package com.codelink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioCreateDTO {
    @NotBlank
    private String texto;
    private Long autorId;
    private Long postId;
    // getters/setters
}