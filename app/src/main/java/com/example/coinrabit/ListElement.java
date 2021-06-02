package com.example.coinrabit;

public class ListElement {
    public String tipo;
    public String concepto;
    public String fecha;
    public String monto;
    public String colorMonto;
    public String colorIcono;
    public int icono;
    public String idMovimiento;


    public ListElement(String tipo, String concepto, String fecha, String monto, String colorMonto, String colorIcono, int icono, String idMovimiento) {
        this.tipo = tipo;
        this.concepto = concepto;
        this.fecha = fecha;
        this.monto = monto;
        this.colorMonto = colorMonto;
        this.colorIcono = colorIcono;
        this.icono = icono;
        this.idMovimiento = idMovimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getColorMonto() {
        return colorMonto;
    }

    public void setColorMonto(String colorMonto) {
        this.colorMonto = colorMonto;
    }

    public String getColorIcono() {
        return colorIcono;
    }

    public void setColorIcono(String colorIcono) {
        this.colorIcono = colorIcono;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }
}
