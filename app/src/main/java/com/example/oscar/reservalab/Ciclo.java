package com.example.oscar.reservalab;

/**
 * Created by Melissa on 12/05/2018.
 */

public class Ciclo {
    private Integer idCiclo;
    private Integer numCiclo;
    private Integer anio;

    public Ciclo() {
    }

    public Ciclo(Integer idCiclo, Integer numCiclo, Integer anio) {
        this.idCiclo = idCiclo;
        this.numCiclo = numCiclo;
        this.anio = anio;
    }

    public Integer getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(Integer idCiclo) {
        this.idCiclo = idCiclo;
    }

    public Integer getNumCiclo() {
        return numCiclo;
    }

    public void setNumCiclo(Integer numCiclo) {
        this.numCiclo = numCiclo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
}