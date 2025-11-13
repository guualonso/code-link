package com.codelink.service;

import java.util.List;

import com.codelink.dto.RegisterDTO;
import com.codelink.dto.UsuarioCreateDTO;
import com.codelink.dto.UsuarioDTO;

public interface UsuarioService {
    // usado pelo endpoint /api/usuarios (CRUD interno)
    UsuarioDTO criar(UsuarioCreateDTO dto);

    UsuarioDTO buscarPorId(Long id);
    List<UsuarioDTO> listarTodos();
    UsuarioDTO atualizar(Long id, UsuarioCreateDTO dto);
    void deletar(Long id);

    // usado pelo endpoint público /api/auth/register
    UsuarioDTO criar(RegisterDTO dto);
}
