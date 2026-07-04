package com.example.demo.controller;

import com.example.demo.dto.request.ReservaRequest;
import com.example.demo.dto.response.ReservaResponse;
import com.example.demo.service.ReservaService;
import com.example.demo.utils.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservaResponse>> crear(
            @Valid @RequestBody ReservaRequest request,
            @AuthenticationPrincipal Long usuarioId) { 
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Reserva creada", service.crear(request, usuarioId)));
    }

    @GetMapping("/mis-reservas")
    public ResponseEntity<ApiResponse<List<ReservaResponse>>> misReservas(
            @AuthenticationPrincipal Long usuarioId) {
        return ResponseEntity.ok(ApiResponse.ok("Mis reservas", service.findMisReservas(usuarioId)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ReservaResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Todas las reservas", service.findAll()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Reserva eliminada", null));
    }
}