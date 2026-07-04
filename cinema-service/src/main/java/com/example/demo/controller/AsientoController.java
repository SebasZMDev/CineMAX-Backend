package com.example.demo.controller;

import com.example.demo.dto.response.AsientoResponse;
import com.example.demo.service.AsientoService;
import com.example.demo.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
@CrossOrigin(origins = "*")
public class AsientoController {

    @Autowired
    private AsientoService service;

    @GetMapping("/sala/{codSala}")
    public ResponseEntity<ApiResponse<List<AsientoResponse>>> findBySala(@PathVariable Integer codSala) {
        return ResponseEntity.ok(ApiResponse.ok("Asientos de la sala", service.findBySala(codSala)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AsientoResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Asiento encontrado", service.findById(id)));
    }
    
    @GetMapping("/sala/{codSala}/funcion/{codFun}")
    public ResponseEntity<ApiResponse<List<AsientoResponse>>> findBySalaAndFuncion(
            @PathVariable Integer codSala,
            @PathVariable Integer codFun) {
        return ResponseEntity.ok(ApiResponse.ok("Asientos con disponibilidad", service.findBySalaAndFuncion(codSala, codFun)));
    }
}

