package com.example.oscar.reservalab;


public class Asignatura {

    private String codigoAsignatura;
    private String nombreAsignatura;

    public Asignatura(String codigoAsignatura, String nombreAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
        this.nombreAsignatura=nombreAsignatura;
    }

    public Asignatura() {
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

