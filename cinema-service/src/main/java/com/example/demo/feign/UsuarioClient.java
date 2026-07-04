package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", url = "${auth-service.url}")
public interface UsuarioClient {
    @GetMapping("/api/usuarios/{id}")
    UsuarioDTO findById(@PathVariable("id") Long id);
}