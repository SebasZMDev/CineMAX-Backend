package com.example.demo.messaging;

import com.example.demo.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuditoriaPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishEvent(String accion, Long usuarioId, String detalles) {
        Map<String, Object> mensaje = new HashMap<>();

        mensaje.put("accion", accion);
        mensaje.put("usuarioId", usuarioId);
        mensaje.put("detalles", detalles);
        mensaje.put("timestamp", LocalDateTime.now().toString());

        System.out.println("====== PUBLICANDO EVENTO A RABBITMQ ======");
        System.out.println("Exchange: " + RabbitMQConfig.AUDITORIA_EXCHANGE);
        System.out.println("Routing key: " + RabbitMQConfig.AUDITORIA_ROUTING_KEY);
        System.out.println("Mensaje: " + mensaje);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.AUDITORIA_EXCHANGE,
                RabbitMQConfig.AUDITORIA_ROUTING_KEY,
                mensaje
        );

        System.out.println("-> Evento enviado a RabbitMQ.");
    }
}