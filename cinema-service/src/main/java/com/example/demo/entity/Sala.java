package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_sala")
    private Integer codSala;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "tipo", length = 20)
    private String tipo;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funcion> funciones;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asiento> asientos;

    public Sala() {}

    public Integer getCodSala() { return codSala; }
    public void setCodSala(Integer codSala) { this.codSala = codSala; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public List<Funcion> getFunciones() { return funciones; }
    public void setFunciones(List<Funcion> funciones) { this.funciones = funciones; }
    public List<Asiento> getAsientos() { return asientos; }
    public void setAsientos(List<Asiento> asientos) { this.asientos = asientos; }
}
