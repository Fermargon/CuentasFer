package com.mar.fer.cuentasfer;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Fer on 26/04/2017.
 */

public class Pagos implements Serializable{

    public String tipo1 = null;
    public String tipo2 = null;
    public String descrip = null;
    public String fecha = null;;
    public Date fcompra = null;
    public int importe = -1;
    public int idusuario = -1;
    public String iniciales = null;
    public String idpago = null ;
    public String fecharegistro = null;

    public Pagos() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Pagos(String tipo1, String tipo2, String descrip, String fecha, int importe, int idusuario, String idpago, String fecharegistro) {
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.descrip = descrip;
        this.fecha = fecha;
        this.importe = importe;
        this.idusuario = idusuario;
        this.idpago = idpago;
        this.fecharegistro = fecharegistro;
    }

    public Pagos(String tipo1, String tipo2, String descrip, String fecha, int importe, int idusuario, String iniciales, String idpago, String fecharegistro) {
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.descrip = descrip;
        this.fecha = fecha;
        this.importe = importe;
        this.idusuario = idusuario;
        this.idusuario = idusuario;
        this.iniciales = iniciales;
        this.fecharegistro = fecharegistro;
    }

    public Pagos(String tipo1, String tipo2, String descrip, String fecha, Date fcompra, int importe, int idusuario, String idpago, String fecharegistro) {
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.descrip = descrip;
        this.fecha = fecha;
        this.fcompra = fcompra;
        this.importe = importe;
        this.idusuario = idusuario;
        this.idpago = idpago;
        this.fecharegistro = fecharegistro;
    }

    public String getTipo1(){
        return tipo1;
    }
    public String getTipo2(){
        return tipo2;
    }
    public String getDescrip(){
        return descrip;
    }
    public String getFecha(){
        return fecha;
    }
    public Date getFcompra(){
        return fcompra;
    }
    public int getImporte(){return importe;}
    public int getIdusuario(){ return idusuario;}
    public String getIniciales(){ return iniciales;}
    public String getIdpago(){ return idpago; }
    public String getFecharegistro(){
        return fecharegistro;
    }

    public void setTipo1 (String tipo1){
        this.tipo1 = tipo1;
    }
    public void setTipo2 (String tipo2){
        this.tipo2 = tipo2;
    }
    public void setDescrip (String descrip){
        this.descrip = descrip;
    }
    public void setFecha (String fecha){
        this.fecha = fecha;
    }
    public void setFcompra (Date fcompra){
        this.fcompra = fcompra;
    }
    public void setImporte (int importe){
        this.importe = importe;
    }
    public void setIdusuario (int idusuario){
        this.idusuario = idusuario;
    }
    public void setIniciales (String iniciales){
        this.iniciales = iniciales;
    }
    public void setIdpago (String idpago){
        this.idpago = idpago;
    }
    public void setFecharegistro (String fecharegistro){
        this.fecharegistro = fecharegistro;
    }
}