package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_pel")
    private Integer codPel;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "genero", length = 50)
    private String genero;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "clasificacion", length = 10)
    private String clasificacion;

    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    @Column(name = "precio_entrada", precision = 10, scale = 2)
    private BigDecimal precioEntrada;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funcion> funciones;

    public Pelicula() {}

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
    public List<Funcion> getFunciones() { return funciones; }
    public void setFunciones(List<Funcion> funciones) { this.funciones = funciones; }
}
