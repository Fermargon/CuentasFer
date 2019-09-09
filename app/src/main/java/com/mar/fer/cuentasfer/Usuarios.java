package com.mar.fer.cuentasfer;

/**
 * Created by Fer on 26/04/2017.
 */

public class Usuarios {

    public String nombre;
    public String email;
    public String usuario;
    public String pass;
    public String iniciales;
    public Integer idusuario;


    public Usuarios() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Usuarios(String nombre, String email, String usuario, String pass, Integer idusuario, String iniciales) {
        this.nombre = nombre;
        this.email = email;
        this.usuario = usuario;
        this.pass = pass;
        this.idusuario = idusuario;
        this.iniciales = iniciales;
    }

    public String getNombre(){
        return nombre;
    }

    public String getEmail(){
        return email;
    }
    public String getUsuario(){
        return usuario;
    }
    public String getPass(){
        return pass;
    }
    public Integer getIdusuario(){
        return idusuario;
    }
    public String getIniciales(){
        return iniciales;
    }

}