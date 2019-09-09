package com.mar.fer.cuentasfer;

import java.util.Date;
import java.util.Vector;

public class Tempplayers{

    Vector vDatosplayers = null;

    public Vector getvDatosplayers() {
        return vDatosplayers;
    }

    public void setvDatosplayers(Vector vDatosplayers) {
        this.vDatosplayers = vDatosplayers;
    }

    public Tempplayers() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}