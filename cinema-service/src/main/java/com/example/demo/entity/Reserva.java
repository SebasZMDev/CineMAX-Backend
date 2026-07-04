package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_res")
    private Integer codRes;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;          // ya no @ManyToOne Usuario

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_fun", nullable = false)
    private Funcion funcion;

    @Column(name = "cantidad_entradas", nullable = false)
    private Integer cantidadEntradas;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tb_reserva_asiento",
        joinColumns = @JoinColumn(name = "cod_res"),
        inverseJoinColumns = @JoinColumn(name = "cod_asiento")
    )
    private Set<Asiento> asientos = new HashSet<>();

    public Reserva() {}
    public Integer getCodRes() { return codRes; }
    public void setCodRes(Integer codRes) { this.codRes = codRes; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Funcion getFuncion() { return funcion; }
    public void setFuncion(Funcion funcion) { this.funcion = funcion; }
    public Integer getCantidadEntradas() { return cantidadEntradas; }
    public void setCantidadEntradas(Integer cantidadEntradas) { this.cantidadEntradas = cantidadEntradas; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }
    public Set<Asiento> getAsientos() { return asientos; }
    public void setAsientos(Set<Asiento> asientos) { this.asientos = asientos; }
}