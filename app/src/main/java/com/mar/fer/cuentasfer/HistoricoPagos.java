package com.mar.fer.cuentasfer;

import java.util.Vector;

/**
 * Created by Fer on 26/04/2017.
 */

public class HistoricoPagos {

    public Integer idusuario;
    public String finihist;
    public String ffinhist;
    public Integer saldo;
    public Vector pagos;

    public Vector getPagos() {
        return pagos;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public String getFinihist() {
        return finihist;
    }

    public String getFfinhist() {
        return ffinhist;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public void setFinihist(String finihist) {
        this.finihist = finihist;
    }

    public void setFfinhist(String ffinhist) {
        this.ffinhist = ffinhist;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public void setvPagos(Vector vPagos) {
        this.pagos = vPagos;
    }

    public HistoricoPagos() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public HistoricoPagos(Integer idusuario, String finihist, String ffinhist, Integer saldo, Vector vPagos) {
        this.idusuario = idusuario;
        this.finihist = finihist;
        this.ffinhist = ffinhist;
        this.saldo = saldo;
        this.pagos = vPagos;
    }

}