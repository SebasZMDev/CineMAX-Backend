package com.example.demo.dto.response;

public class AsientoResponse {
    private Integer codAsiento;
    private String fila;
    private Integer numero;
    private Integer codSala;
    private boolean ocupado;

    public Integer getCodAsiento() { return codAsiento; }
    public void setCodAsiento(Integer codAsiento) { this.codAsiento = codAsiento; }
    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getCodSala() { return codSala; }
    public void setCodSala(Integer codSala) { this.codSala = codSala; }
    public boolean isOcupado() { return ocupado; }
    public void setOcupado(boolean ocupado) { this.ocupado = ocupado; }
}