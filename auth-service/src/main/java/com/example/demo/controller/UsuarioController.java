package com.example.demo.controller;

import com.example.demo.dto.response.UsuarioResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.utils.ApiResponse;
import com.example.demo.utils.UsuarioMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UsuarioResponse>> me(Authentication authentication) {
    	
        if (authentication == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("No autenticado"));
        }
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(ApiResponse.ok("Usuario actual", UsuarioMapper.toResponse(usuario)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> findById(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(ApiResponse.ok("Usuario", UsuarioMapper.toResponse(usuario)));
    }
}