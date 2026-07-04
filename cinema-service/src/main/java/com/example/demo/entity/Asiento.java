package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_asiento")
public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_asiento")
    private Integer codAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_sala", nullable = false)
    private Sala sala;

    @Column(name = "fila", nullable = false, length = 1)
    private String fila;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    public Asiento() {}

    public Integer getCodAsiento() { return codAsiento; }
    public void setCodAsiento(Integer codAsiento) { this.codAsiento = codAsiento; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
}
