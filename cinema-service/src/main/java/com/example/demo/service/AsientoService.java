package com.example.demo.service;

import com.example.demo.dto.response.AsientoResponse;
import com.example.demo.entity.Asiento;
import com.example.demo.repository.AsientoRepository;
import com.example.demo.utils.ModeloNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsientoService {
    @Autowired private AsientoRepository repo;

    private AsientoResponse toDTO(Asiento a, boolean ocupado) {
        AsientoResponse dto = new AsientoResponse();
        dto.setCodAsiento(a.getCodAsiento());
        dto.setFila(a.getFila());
        dto.setNumero(a.getNumero());
        if (a.getSala() != null) dto.setCodSala(a.getSala().getCodSala());
        dto.setOcupado(ocupado);
        return dto;
    }

    public List<AsientoResponse> findBySala(Integer codSala) {
        return repo.findBySala_CodSala(codSala).stream()
                .map(a -> toDTO(a, false))
                .collect(Collectors.toList());
    }

    public List<AsientoResponse> findBySalaAndFuncion(Integer codSala, Integer codFun) {
        List<Integer> ocupados = repo.findCodAsientosOcupadosByFuncion(codFun);
        return repo.findBySala_CodSala(codSala).stream()
                .map(a -> toDTO(a, ocupados.contains(a.getCodAsiento())))
                .collect(Collectors.toList());
    }

    public AsientoResponse findById(Integer id) {
        return toDTO(repo.findById(id)
                .orElseThrow(() -> new ModeloNotFoundException("Asiento no encontrado: " + id)), false);
    }
}