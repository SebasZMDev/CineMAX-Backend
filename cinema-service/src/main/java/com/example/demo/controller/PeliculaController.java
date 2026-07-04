package com.example.demo.controller;

import com.example.demo.dto.response.PeliculaResponse;
import com.example.demo.service.PeliculaService;
import com.example.demo.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "*")
public class PeliculaController {

    @Autowired
    private PeliculaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PeliculaResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Películas obtenidas", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PeliculaResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Película encontrada", service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PeliculaResponse>> save(@RequestBody PeliculaResponse dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Película registrada", service.save(dto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PeliculaResponse>> update(@PathVariable Integer id,
                                                                 @RequestBody PeliculaResponse dto) {
        return ResponseEntity.ok(ApiResponse.ok("Película actualizada", service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Película eliminada", null));
    }
}
