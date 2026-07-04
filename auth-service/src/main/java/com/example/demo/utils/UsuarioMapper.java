package com.example.demo.utils;

import com.example.demo.dto.response.UsuarioResponse;
import com.example.demo.entity.Usuario;

public class UsuarioMapper {
    public static UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRoles().stream()
                .findFirst()
                .map(r -> r.getNombre())
                .orElse(null));
        return dto;
    }
}