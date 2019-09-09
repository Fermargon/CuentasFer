package com.mar.fer.cuentasfer;
import java.util.Comparator;

public class ComparardorValores implements Comparator<Ordenar> {

    @Override
    public int compare(Ordenar o1, Ordenar o2) {
        // TODO Auto-generated method stub

        String stO1 = String.valueOf(o1.getValor());
        String stO2 = String.valueOf(o2.getValor());

        return  stO1.compareTo(stO2);
    }
}
