package com.example.demo.service;

import com.example.demo.entity.Asiento;
import com.example.demo.entity.Funcion;
import com.example.demo.entity.Reserva;
import com.example.demo.repository.AsientoRepository;
import com.example.demo.repository.FuncionRepository;
import com.example.demo.repository.ReservaRepository;
import com.example.demo.dto.request.ReservaRequest;
import com.example.demo.dto.response.ReservaResponse;
import com.example.demo.feign.UsuarioClient;
import com.example.demo.feign.UsuarioDTO;
import com.example.demo.utils.BusinessException;
import com.example.demo.utils.ModeloNotFoundException;
import com.example.demo.messaging.AuditoriaPublisher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired private ReservaRepository repo;
    @Autowired private FuncionRepository funcionRepo;
    @Autowired private AsientoRepository asientoRepo;
    @Autowired private UsuarioClient usuarioClient;
    @Autowired private AuditoriaPublisher auditoriaPublisher;
    
    private ReservaResponse toDTO(Reserva r) {
        ReservaResponse dto = new ReservaResponse();
        dto.setCodRes(r.getCodRes());
        dto.setCantidadEntradas(r.getCantidadEntradas());
        dto.setFechaReserva(r.getFechaReserva());

        UsuarioDTO usuario = usuarioClient.findById(r.getUsuarioId());
        if (usuario != null) dto.setNombreCliente(usuario.getNombre());

        if (r.getFuncion() != null) {
            dto.setCodFun(r.getFuncion().getCodFun());
            dto.setFechaFuncion(r.getFuncion().getFecha());
            if (r.getFuncion().getPelicula() != null) {
                dto.setTituloPelicula(r.getFuncion().getPelicula().getTitulo());
                BigDecimal precio = r.getFuncion().getPelicula().getPrecioEntrada();
                if (precio != null)
                    dto.setTotalPagar(precio.multiply(BigDecimal.valueOf(r.getCantidadEntradas())));
            }
        }
        if (r.getAsientos() != null) {
            dto.setAsientos(r.getAsientos().stream()
                    .map(a -> a.getFila() + a.getNumero())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    @Transactional
    public ReservaResponse crear(ReservaRequest request, Long usuarioId) {
        Funcion funcion = funcionRepo.findById(request.getCodFun())
                .orElseThrow(() -> new ModeloNotFoundException(
                        "Función no encontrada: " + request.getCodFun()
                ));

        Reserva reserva = new Reserva();
        reserva.setUsuarioId(usuarioId);
        reserva.setFuncion(funcion);
        reserva.setCantidadEntradas(request.getCantidadEntradas());
        reserva.setFechaReserva(LocalDate.now());

        if (request.getAsientosIds() != null && !request.getAsientosIds().isEmpty()) {
            if (request.getAsientosIds().size() != request.getCantidadEntradas()) {
                throw new BusinessException(
                        "La cantidad de asientos debe coincidir con la cantidad de entradas"
                );
            }

            Set<Asiento> asientos = new HashSet<>();

            for (Integer id : request.getAsientosIds()) {
                Asiento asiento = asientoRepo.findById(id)
                        .orElseThrow(() -> new ModeloNotFoundException(
                                "Asiento no encontrado: " + id
                        ));

                asientos.add(asiento);
            }

            reserva.setAsientos(asientos);
        }

        Reserva reservaGuardada = repo.save(reserva);

        auditoriaPublisher.publishEvent(
                "CREAR_RESERVA",
                usuarioId,
                "Reserva creada. Código: " + reservaGuardada.getCodRes()
                        + ", función: " + funcion.getCodFun()
                        + ", entradas: " + reservaGuardada.getCantidadEntradas()
        );

        return toDTO(reservaGuardada);
    }

    public List<ReservaResponse> findMisReservas(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReservaResponse> findAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void delete(Integer id) {
        repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Reserva no encontrada: " + id));
        repo.deleteById(id);
    }
}