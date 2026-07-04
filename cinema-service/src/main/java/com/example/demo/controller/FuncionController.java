package com.example.demo.controller;

import com.example.demo.dto.response.FuncionResponse;
import com.example.demo.entity.Funcion;
import com.example.demo.service.FuncionService;
import com.example.demo.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funciones")
@CrossOrigin(origins = "*")
public class FuncionController {

    @Autowired
    private FuncionService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FuncionResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Funciones obtenidas", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FuncionResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Función encontrada", service.findById(id)));
    }

    @GetMapping("/pelicula/{codPel}")
    public ResponseEntity<ApiResponse<List<FuncionResponse>>> findByPelicula(@PathVariable Integer codPel) {
        return ResponseEntity.ok(ApiResponse.ok("Funciones por película", service.findByPelicula(codPel)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FuncionResponse>> save(
            @RequestParam Integer codPel,
            @RequestParam Integer codSala,
            @RequestBody Funcion funcion) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Función registrada", service.save(codPel, codSala, funcion)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Función eliminada", null));
    }
}
