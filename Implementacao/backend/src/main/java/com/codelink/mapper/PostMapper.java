package com.codelink.mapper;

import com.codelink.dto.*;
import com.codelink.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, ComentarioMapper.class})
public interface PostMapper {

    @Mapping(target = "comunidadeId", source = "comunidade.id")
    PostDTO toDTO(Post post);

    @Mapping(target = "comunidade", ignore = true) 
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "dataPostagem", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    Post toEntity(PostCreateDTO dto);
}
