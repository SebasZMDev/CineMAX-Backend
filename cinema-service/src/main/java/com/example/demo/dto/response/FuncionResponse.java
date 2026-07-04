package com.example.demo.dto.response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
public class FuncionResponse {
    private Integer codFun;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer codPel;
    private String tituloPelicula;
    private BigDecimal precioEntrada;
    private Integer codSala;
    private String nombreSala;
    private String tipoSala;
    public Integer getCodFun() { return codFun; }
    public void setCodFun(Integer codFun) { this.codFun = codFun; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public Integer getCodPel() { return codPel; }
    public void setCodPel(Integer codPel) { this.codPel = codPel; }
    public String getTituloPelicula() { return tituloPelicula; }
    public void setTituloPelicula(String tituloPelicula) { this.tituloPelicula = tituloPelicula; }
    public BigDecimal getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(BigDecimal precioEntrada) { this.precioEntrada = precioEntrada; }
    public Integer getCodSala() { return codSala; }
    public void setCodSala(Integer codSala) { this.codSala = codSala; }
    public String getNombreSala() { return nombreSala; }
    public void setNombreSala(String nombreSala) { this.nombreSala = nombreSala; }
    public String getTipoSala() { return tipoSala; }
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }
}
