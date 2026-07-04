package com.example.demo.service;

import com.example.demo.utils.BusinessException;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.Rol;
import com.example.demo.entity.Usuario;
import com.example.demo.messaging.AuditoriaPublisher;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private RolRepository rolRepo;
    @Autowired private RefreshTokenRepository refreshRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private AuthenticationManager authManager;
    @Autowired private UsuarioDetailsService userDetailsService;
    @Autowired private AuditoriaPublisher auditoriaPublisher;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepo.existsByEmail(request.getEmail())) {
            throw new BusinessException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        Rol rol = rolRepo.findByNombre("CLIENTE")
                .orElseThrow(() -> new BusinessException("Rol CLIENTE no encontrado. Ejecuta el SQL de la BD."));
        usuario.getRoles().add(rol);
        usuarioRepo.save(usuario);

        auditoriaPublisher.publishEvent(
                "REGISTRO",
                usuario.getId(),
                "Usuario registrado: " + usuario.getEmail()
            );
        
        return generateAuthResponse(usuario);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Usuario usuario = usuarioRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
        
        auditoriaPublisher.publishEvent(
                "LOGIN",
                usuario.getId(),
                "Login exitoso: " + usuario.getEmail()
            );
        return generateAuthResponse(usuario);
    }

    @Transactional
    public AuthResponse refreshToken(String token) {
        RefreshToken rt = refreshRepo.findByToken(token)
                .orElseThrow(() -> new BusinessException("Refresh token inválido"));
        if (rt.getRevocado() || rt.getExpiracion().isBefore(Instant.now())) {
            throw new BusinessException("Refresh token expirado o revocado");
        }
        rt.setRevocado(true);
        refreshRepo.save(rt);
        return generateAuthResponse(rt.getUsuario());
    }

    @Transactional
    public void logout(Long usuarioId) {
        auditoriaPublisher.publishEvent(
            "LOGOUT",
            usuarioId,
            "Logout usuario ID: " + usuarioId
        );
        refreshRepo.deleteByUsuario_Id(usuarioId);
    }

    private AuthResponse generateAuthResponse(Usuario usuario) {
        String accessToken = jwtUtils.generateToken(usuario);
        String refreshToken = createRefreshToken(usuario);
        String rol = usuario.getRoles().stream().findFirst().map(Rol::getNombre).orElse("CLIENTE");
        return new AuthResponse(accessToken, refreshToken, usuario.getEmail(), usuario.getNombre(), rol);
    }

    private String createRefreshToken(Usuario usuario) {
        refreshRepo.deleteByUsuario_Id(usuario.getId());
        RefreshToken rt = new RefreshToken();
        rt.setUsuario(usuario);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiracion(Instant.now().plusMillis(refreshExpiration));
        rt.setRevocado(false);
        return refreshRepo.save(rt).getToken();
    }
}
