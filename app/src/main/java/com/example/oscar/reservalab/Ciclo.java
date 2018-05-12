package com.example.oscar.reservalab;

/**
 * Created by Melissa on 12/05/2018.
 */

public class Ciclo {

    private String numCiclo;
    private String anio;

    public Ciclo() {
    }

    public Ciclo(String numCiclo, String anio) {
        this.numCiclo = numCiclo;
        this.anio = anio;
    }

    public String getNumCiclo() {
        return numCiclo;
    }

    public void setNumCiclo(String numCiclo) {
        this.numCiclo = numCiclo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
}