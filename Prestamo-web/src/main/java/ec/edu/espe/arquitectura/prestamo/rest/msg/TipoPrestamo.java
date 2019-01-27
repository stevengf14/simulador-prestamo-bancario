/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.prestamo.rest.msg;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Steven
 */
public class TipoPrestamo {
    private BigDecimal id;
    private String descripcion;
    private String estado;
    private BigDecimal interes;
    private String tipo_cliente;
    private BigDecimal montoMin;
    private BigDecimal montoMax;
    private BigInteger plazoMin;
    private BigInteger plazoMax;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public String getTipo_cliente() {
        return tipo_cliente;
    }

    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public BigDecimal getMontoMin() {
        return montoMin;
    }

    public void setMontoMin(BigDecimal montoMin) {
        this.montoMin = montoMin;
    }

    public BigDecimal getMontoMax() {
        return montoMax;
    }

    public void setMontoMax(BigDecimal montoMax) {
        this.montoMax = montoMax;
    }

    public BigInteger getPlazoMin() {
        return plazoMin;
    }

    public void setPlazoMin(BigInteger plazoMin) {
        this.plazoMin = plazoMin;
    }

    public BigInteger getPlazoMax() {
        return plazoMax;
    }

    public void setPlazoMax(BigInteger plazoMax) {
        this.plazoMax = plazoMax;
    }
    
    
}
