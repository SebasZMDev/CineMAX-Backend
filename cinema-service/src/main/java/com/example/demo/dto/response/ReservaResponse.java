package com.example.demo.dto.response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public class ReservaResponse {
    private Integer codRes;
    private String nombreCliente;
    private Integer codFun;
    private String tituloPelicula;
    private LocalDate fechaFuncion;
    private Integer cantidadEntradas;
    private LocalDate fechaReserva;
    private BigDecimal totalPagar;
    private List<String> asientos;
    public Integer getCodRes() { return codRes; }
    public void setCodRes(Integer codRes) { this.codRes = codRes; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public Integer getCodFun() { return codFun; }
    public void setCodFun(Integer codFun) { this.codFun = codFun; }
    public String getTituloPelicula() { return tituloPelicula; }
    public void setTituloPelicula(String tituloPelicula) { this.tituloPelicula = tituloPelicula; }
    public LocalDate getFechaFuncion() { return fechaFuncion; }
    public void setFechaFuncion(LocalDate fechaFuncion) { this.fechaFuncion = fechaFuncion; }
    public Integer getCantidadEntradas() { return cantidadEntradas; }
    public void setCantidadEntradas(Integer cantidadEntradas) { this.cantidadEntradas = cantidadEntradas; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }
    public BigDecimal getTotalPagar() { return totalPagar; }
    public void setTotalPagar(BigDecimal totalPagar) { this.totalPagar = totalPagar; }
    public List<String> getAsientos() { return asientos; }
    public void setAsientos(List<String> asientos) { this.asientos = asientos; }
}
