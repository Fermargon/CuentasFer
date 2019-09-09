package com.mar.fer.cuentasfer;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Fer on 02/06/2017.
 */

public class GlobalClass {

    private Vector vPlayerTemporadaActual = null;
    private Vector usuarios = null;
    private Integer nordenActual = null;
    private Date daFtemporada = null;
    private Integer numTemporadaActual = null;
    private static GlobalClass instance;
    private static int idusuarioConectado = 0;
    private static String  nombre = "";
    private static String iniciales = "";
    private static HashMap reports = null;
    private static String inrankings = "";

    public static String getStTipoTrofeos() {
        return stTipoTrofeos;
    }

    public static void setStTipoTrofeos(String stTipoTrofeos) {
        GlobalClass.stTipoTrofeos = stTipoTrofeos;
    }

    private static String stTipoTrofeos = "";

    public static HashMap getReports() {
        return reports;
    }

    public static void setReports(HashMap reports) {
        GlobalClass.reports = reports;
    }

    public static String getInrankings() {
        return inrankings;
    }

    public static void setInrankings(String inrankings) {
        GlobalClass.inrankings = inrankings;
    }

    public String getToken1() {
        return token1;
    }

    public void setToken1(String token1) {
        this.token1 = token1;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public String getToken3() {
        return token3;
    }

    public void setToken3(String token3) {
        this.token3 = token3;
    }

    private String token1 = "";
    private String token2 = "";
    private String token3 = "";

    public Vector getvPlayerNuevos() {
        return vPlayerNuevos;
    }

    public void setvPlayerNuevos(Vector vPlayerNuevos) {
        this.vPlayerNuevos = vPlayerNuevos;
    }

    private Vector vPlayerNuevos = null;

    public Vector getvPlayerTemporadaActual() {
        return vPlayerTemporadaActual;
    }

    public void setvPlayerTemporadaActual(Vector vPlayerTemporadaActual) {
        this.vPlayerTemporadaActual = vPlayerTemporadaActual;
    }

    public Date getDaFtemporada() {
        return daFtemporada;
    }

    public void setDaFtemporada(Date daFtemporada) {
        this.daFtemporada = daFtemporada;
    }

    public static int getIdusuarioConectado() {
        return idusuarioConectado;
    }

    public static String getIniciales(Vector vUsuario, Integer idusuarioConectado) {
        return r_obt_iniciales(vUsuario, idusuarioConectado);
    }

    public static void setIdusuarioConectado(int idusuarioConectado) {
        GlobalClass.idusuarioConectado = idusuarioConectado;
    }

    public Vector getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Vector usuarios) {
        this.usuarios = usuarios;
    }

    public Integer getHistoricoActual() {
        return nordenActual;
    }

    public void setHistoricoActual(Integer nordenActual) {
        this.nordenActual = nordenActual;
    }

    public Integer getNumTemporadaActual() {
        return numTemporadaActual;
    }

    public void setNumTemporadaActual(Integer numTemporadaActual) {
        this.numTemporadaActual = numTemporadaActual;
    }

    static {
        instance = new GlobalClass();
    }

    private static String  r_obt_iniciales(Vector vUsuarios, Integer idusuario){

        Integer idusuariorec;
        Usuarios usuarios;

        for (int i = 0; i < vUsuarios.size(); i++){

            usuarios = (Usuarios) vUsuarios.get(i);

            idusuariorec = new Integer(usuarios.getIdusuario());
            nombre = String.valueOf(usuarios.getNombre());
            iniciales = String.valueOf(usuarios.getIniciales());

            if (idusuariorec.longValue() == idusuario.longValue()){
                break;
            }
        }

        return iniciales;
    }

    public static boolean isNum(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

    private GlobalClass() {
    }

    public static GlobalClass getInstance() {
        return GlobalClass.instance;
    }
}
