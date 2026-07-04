package com.example.demo.dto.response;
import java.math.BigDecimal;
import java.time.LocalDate;
public class PeliculaResponse {
    private Integer codPel;
    private String titulo;
    private String genero;
    private Integer duracion;
    private String clasificacion;
    private LocalDate fechaEstreno;
    private BigDecimal precioEntrada;
    
    public Integer getCodPel() { return codPel; }
    public void setCodPel(Integer codPel) { this.codPel = codPel; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }
    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
    public LocalDate getFechaEstreno() { return fechaEstreno; }
    public void setFechaEstreno(LocalDate fechaEstreno) { this.fechaEstreno = fechaEstreno; }
    public BigDecimal getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(BigDecimal precioEntrada) { this.precioEntrada = precioEntrada; }
}
