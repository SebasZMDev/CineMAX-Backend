package com.example.demo.controller;

import com.example.demo.entity.LogAuditoria;
import com.example.demo.repository.LogAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*")
public class AuditoriaController {

    @Autowired
    private LogAuditoriaRepository repository;

    @GetMapping
    public ResponseEntity<List<LogAuditoria>> obtenerTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<LogAuditoria>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(repository.findByUsuarioId(usuarioId));
    }
}