package com.example.demo.service;

import com.example.demo.entity.Sala;
import com.example.demo.repository.SalaRepository;
import com.example.demo.utils.ModeloNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    @Autowired private SalaRepository repo;

    public List<Sala> findAll() { return repo.findAll(); }

    public Sala findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Sala no encontrada: " + id));
    }

    public Sala save(Sala sala) { return repo.save(sala); }

    public Sala update(Integer id, Sala sala) {
        repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Sala no encontrada: " + id));
        sala.setCodSala(id);
        return repo.save(sala);
    }

    public void delete(Integer id) {
        repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Sala no encontrada: " + id));
        repo.deleteById(id);
    }
}
