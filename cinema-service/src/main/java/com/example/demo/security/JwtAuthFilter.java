package com.example.demo.security;

import com.example.demo.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("DEBUG -> No hay header Authorization o no empieza con Bearer");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        boolean valido = jwtUtils.isTokenValid(token);
        Long usuarioId = jwtUtils.extractUserId(token);
        String rol = jwtUtils.extractRol(token);

        System.out.println("DEBUG -> tokenValido=" + valido + " usuarioId=" + usuarioId + " rol=" + rol);

        if (valido && SecurityContextHolder.getContext().getAuthentication() == null) {
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(usuarioId, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("DEBUG -> Authentication seteado con principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        filterChain.doFilter(request, response);
    }
}