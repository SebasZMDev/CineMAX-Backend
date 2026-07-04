package com.example.demo.dto;

import java.io.Serializable;

public class AuditoriaEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accion;
    private Long usuarioId;
    private String detalles;
    private String timestamp;

    public AuditoriaEvent() {}

    public AuditoriaEvent(String accion, Long usuarioId, String detalles, String timestamp) {
        this.accion = accion;
        this.usuarioId = usuarioId;
        this.detalles = detalles;
        this.timestamp = timestamp;
    }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}