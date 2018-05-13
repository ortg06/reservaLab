package com.example.oscar.reservalab;


public class Asignatura {

    private String codigoAsignatura;
    private String nombreAsignatura;
    private Integer idCiclo;

    public Asignatura() {
    }

    public Asignatura(String codigoAsignatura, String nombreAsignatura, Integer idCiclo) {
        this.codigoAsignatura = codigoAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.idCiclo = idCiclo;
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

    public Integer getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(Integer idCiclo) {
        this.idCiclo = idCiclo;
    }
}