package com.mar.fer.cuentasfer;

public class Ordenar {

    private String nombre;
    private double valor;

    public Ordenar() {
    }

    public Ordenar(String nombre, double valor) {
        super();
        this.nombre = nombre;
        this.valor  = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Ordenar{" +
                "nombre='" + nombre + '\'' +
                ", valor ='" + valor + '\'' +
                '}';
    }
}