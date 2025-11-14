package com.codelink.mapper;

import com.codelink.dto.ComentarioDTO;
import com.codelink.model.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface ComentarioMapper {

    @Mapping(target = "autor", source = "autor")
    ComentarioDTO toDTO(Comentario comentario);
}
