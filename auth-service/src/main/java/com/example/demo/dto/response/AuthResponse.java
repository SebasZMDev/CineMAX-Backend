package com.example.demo.dto.response;
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String nombre;
    private String rol;
    public AuthResponse() {}
    public AuthResponse(String accessToken, String refreshToken, String email, String nombre, String rol) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
    }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
