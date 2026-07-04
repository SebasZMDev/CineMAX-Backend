package com.example.demo.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
public class ReservaRequest {
    @NotNull
    private Integer codFun;
    @NotNull @Min(1)
    private Integer cantidadEntradas;
    private List<Integer> asientosIds;
    public Integer getCodFun() { return codFun; }
    public void setCodFun(Integer codFun) { this.codFun = codFun; }
    public Integer getCantidadEntradas() { return cantidadEntradas; }
    public void setCantidadEntradas(Integer cantidadEntradas) { this.cantidadEntradas = cantidadEntradas; }
    public List<Integer> getAsientosIds() { return asientosIds; }
    public void setAsientosIds(List<Integer> asientosIds) { this.asientosIds = asientosIds; }
}
