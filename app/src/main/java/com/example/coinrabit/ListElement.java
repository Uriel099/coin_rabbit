package com.example.coinrabit;

public class ListElement {
    public String tipo;
    public String concepto;
    public String fecha;
    public String monto;
    public String color;

    public ListElement(String tipo, String concepto, String fecha, String monto, String color) {
        this.tipo = tipo;
        this.concepto = concepto;
        this.fecha = fecha;
        this.monto = monto;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
