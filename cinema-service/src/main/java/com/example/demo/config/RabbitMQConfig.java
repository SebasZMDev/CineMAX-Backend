package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String AUDITORIA_QUEUE = "auditoria.queue";
    public static final String AUDITORIA_EXCHANGE = "auditoria.exchange";
    public static final String AUDITORIA_ROUTING_KEY = "auditoria.routingKey";

    @Bean
    public Queue auditoriaQueue() {
        return new Queue(AUDITORIA_QUEUE, true);
    }

    @Bean
    public DirectExchange auditoriaExchange() {
        return new DirectExchange(AUDITORIA_EXCHANGE);
    }

    @Bean
    public Binding auditoriaBinding(Queue auditoriaQueue, DirectExchange auditoriaExchange) {
        return BindingBuilder.bind(auditoriaQueue).to(auditoriaExchange).with(AUDITORIA_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}