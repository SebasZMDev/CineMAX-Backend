package com.example.demo.service;

import com.example.demo.dto.response.FuncionResponse;
import com.example.demo.entity.Funcion;
import com.example.demo.entity.Pelicula;
import com.example.demo.entity.Sala;
import com.example.demo.messaging.AuditoriaPublisher;
import com.example.demo.repository.FuncionRepository;
import com.example.demo.repository.PeliculaRepository;
import com.example.demo.repository.SalaRepository;
import com.example.demo.utils.ModeloNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionService {

    @Autowired private FuncionRepository repo;
    @Autowired private PeliculaRepository peliculaRepo;
    @Autowired private SalaRepository salaRepo;
    @Autowired private AuditoriaPublisher auditoriaPublisher;

    
    private FuncionResponse toDTO(Funcion f) {
        FuncionResponse dto = new FuncionResponse();
        dto.setCodFun(f.getCodFun());
        dto.setFecha(f.getFecha());
        dto.setHora(f.getHora());
        if (f.getPelicula() != null) {
            dto.setCodPel(f.getPelicula().getCodPel());
            dto.setTituloPelicula(f.getPelicula().getTitulo());
            dto.setPrecioEntrada(f.getPelicula().getPrecioEntrada());
        }
        if (f.getSala() != null) {
            dto.setCodSala(f.getSala().getCodSala());
            dto.setNombreSala(f.getSala().getNombre());
            dto.setTipoSala(f.getSala().getTipo());
        }
        return dto;
    }

    public List<FuncionResponse> findAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FuncionResponse findById(Integer id) {
        return toDTO(repo.findById(id)
                .orElseThrow(() -> new ModeloNotFoundException("Función no encontrada: " + id)));
    }

    public List<FuncionResponse> findByPelicula(Integer codPel) {
        return repo.findByPelicula_CodPel(codPel).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FuncionResponse save(Integer codPel, Integer codSala, Funcion funcion) {
        Pelicula p = peliculaRepo.findById(codPel)
                .orElseThrow(() -> new ModeloNotFoundException("Película no encontrada: " + codPel));
        Sala s = salaRepo.findById(codSala)
                .orElseThrow(() -> new ModeloNotFoundException("Sala no encontrada: " + codSala));
        funcion.setPelicula(p);
        funcion.setSala(s);

        Funcion guardada = repo.save(funcion);

        auditoriaPublisher.publishEvent(
            "CREAR_FUNCION",
            null,
            "Función creada ID: " + guardada.getCodFun()
                + ", película: " + guardada.getPelicula().getTitulo()
                + ", fecha: " + guardada.getFecha()
        );

        return toDTO(guardada);
    }

    public void delete(Integer id) {
        repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Función no encontrada: " + id));
        repo.deleteById(id);
    }
}
