package com.example.demo.service;

import com.example.demo.dto.response.PeliculaResponse;
import com.example.demo.entity.Pelicula;
import com.example.demo.messaging.AuditoriaPublisher;
import com.example.demo.repository.PeliculaRepository;
import com.example.demo.utils.ModeloNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeliculaService {

    @Autowired private PeliculaRepository repo;
    @Autowired private ModelMapper mapper;
    @Autowired private AuditoriaPublisher auditoriaPublisher;

    
    public List<PeliculaResponse> findAll() {
        return repo.findAll().stream()
                .map(e -> mapper.map(e, PeliculaResponse.class))
                .collect(Collectors.toList());
    }

    public PeliculaResponse findById(Integer id) {
        return mapper.map(repo.findById(id)
                .orElseThrow(() -> new ModeloNotFoundException("Película no encontrada: " + id)),
                PeliculaResponse.class);
    }

    public PeliculaResponse save(PeliculaResponse dto) {
        Pelicula guardada = repo.save(mapper.map(dto, Pelicula.class));

        auditoriaPublisher.publishEvent(
            "CREAR_PELICULA",
            null,
            "Película creada: " + guardada.getTitulo() + " (ID: " + guardada.getCodPel() + ")"
        );

        return mapper.map(guardada, PeliculaResponse.class);
    }

    public PeliculaResponse update(Integer id, PeliculaResponse dto) {
        repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Película no encontrada: " + id));
        Pelicula e = mapper.map(dto, Pelicula.class);
        e.setCodPel(id);
        return mapper.map(repo.save(e), PeliculaResponse.class);
    }

    public void delete(Integer id) {
        repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Película no encontrada: " + id));
        repo.deleteById(id);
    }
}
