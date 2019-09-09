package com.mar.fer.cuentasfer;

import java.io.Serializable;

public class Datosplayers implements Serializable {

    public String codusuario;
    public String nombre;
    public double trofeos;
    public double winwars;
    public double cartas;
    public double donaciones;

    public String getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(String codusuario) {
        this.codusuario = codusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTrofeos() {
        return trofeos;
    }

    public void setTrofeos(double trofeos) {
        this.trofeos = trofeos;
    }

    public double getWinwars() {
        return winwars;
    }

    public void setWinwars(double winwars) {
        this.winwars = winwars;
    }

    public double getCartas() {
        return cartas;
    }

    public void setCartas(double cartas) {
        this.cartas = cartas;
    }

    public double getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(double donaciones) {
        this.donaciones = donaciones;
    }

    public Datosplayers() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

}