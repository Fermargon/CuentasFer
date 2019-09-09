package com.mar.fer.cuentasfer;

import java.util.Date;

/**
 * Created by Fer on 26/04/2017.
 */

public class HistoricoActual {

    public Integer norden;
    public Date ftemporada = null;
    public Integer numtemporada;

    public HistoricoActual() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public void setNorden(Integer norden) {
        this.norden = norden;
    }

    public Integer getNorden(){
        return norden;
    }

    public Integer getNumtemporada() {
        return numtemporada;
    }

    public void setNumtemporada(Integer numtemporada) {
        this.numtemporada = numtemporada;
    }

    public Date getFtemporada() {
        return ftemporada;
    }

    public void setFtemporada(Date ftemporada) {
        this.ftemporada = ftemporada;
    }

}