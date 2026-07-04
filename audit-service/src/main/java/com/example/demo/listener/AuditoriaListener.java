package com.example.demo.listener;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.AuditoriaEvent;
import com.example.demo.entity.LogAuditoria;
import com.example.demo.repository.LogAuditoriaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditoriaListener {

    @Autowired
    private LogAuditoriaRepository repository;

    @RabbitListener(queues = RabbitMQConfig.AUDITORIA_QUEUE)
    public void recibirMensaje(AuditoriaEvent evento) {
        System.out.println("====== NUEVO EVENTO RECIBIDO EN AUDIT-SERVICE ======");
        System.out.println("Acción: " + evento.getAccion());
        System.out.println("Usuario ID: " + evento.getUsuarioId());

        try {
            LogAuditoria log = new LogAuditoria();
            log.setAccion(evento.getAccion());
            log.setUsuarioId(evento.getUsuarioId());
            log.setDetalles(evento.getDetalles());

            String entidad = switch (evento.getAccion()) {
                case "REGISTRO", "LOGIN", "LOGOUT" -> "USUARIO";
                case "CREAR_RESERVA"               -> "RESERVA";
                case "CREAR_PELICULA"              -> "PELICULA";
                case "CREAR_FUNCION"               -> "FUNCION";
                default                            -> "SISTEMA";
            };
            log.setEntidad(entidad);

            if (evento.getTimestamp() != null) {
                log.setFechaEvento(LocalDateTime.parse(evento.getTimestamp()));
            } else {
                log.setFechaEvento(LocalDateTime.now());
            }

            repository.save(log);
            System.out.println("-> Guardado en BD de auditoría con éxito.");
        } catch (Exception e) {
            System.err.println("Error procesando el log de auditoría: " + e.getMessage());
        }
    }
}