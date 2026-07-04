package com.example.demo.controller;

import com.example.demo.entity.Sala;
import com.example.demo.service.SalaService;
import com.example.demo.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class SalaController {

    @Autowired
    private SalaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Sala>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Salas obtenidas", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sala>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Sala encontrada", service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Sala>> save(@RequestBody Sala sala) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Sala registrada", service.save(sala)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Sala>> update(@PathVariable Integer id, @RequestBody Sala sala) {
        return ResponseEntity.ok(ApiResponse.ok("Sala actualizada", service.update(id, sala)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Sala eliminada", null));
    }
}
