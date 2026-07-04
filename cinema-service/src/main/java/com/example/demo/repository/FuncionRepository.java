package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Funcion;

import java.util.List;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Integer> {
    List<Funcion> findByPelicula_CodPel(Integer codPel);
}
