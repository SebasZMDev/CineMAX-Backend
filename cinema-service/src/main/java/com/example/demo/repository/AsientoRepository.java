package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Asiento;

import java.util.List;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Integer> {
    List<Asiento> findBySala_CodSala(Integer codSala);

    @Query(value = "SELECT ra.cod_asiento FROM tb_reserva_asiento ra " +
                    "INNER JOIN tb_reserva r ON r.cod_res = ra.cod_res " +
                    "WHERE r.cod_fun = :codFun", nativeQuery = true)
    List<Integer> findCodAsientosOcupadosByFuncion(@Param("codFun") Integer codFun);
}