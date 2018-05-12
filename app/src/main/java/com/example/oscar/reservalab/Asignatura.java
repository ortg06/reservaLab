package com.example.oscar.reservalab;


public class Asignatura {

    private String codigoAsignatura;
    private String nombreAsignatura;
    private String numCiclo;

    public Asignatura(String codigoAsignatura, String nombreAsignatura, String numCiclo) {
        this.codigoAsignatura = codigoAsignatura;
        this.nombreAsignatura=nombreAsignatura;
        this.numCiclo=numCiclo;
    }

    public Asignatura() {
    }

    public String getNumCiclo() {
        return numCiclo;
    }

    public void setNumCiclo(String numCiclo) {
        this.numCiclo = numCiclo;
    }

    public String getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public void setCodigoAsignatura(String codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }
}

