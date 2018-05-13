package com.example.oscar.reservalab;

/**
 * Created by cbren on 12/5/2018.
 */

public class AsignacionCarga {

    private int idAsignacionCarga;
    private String codigoAsignatura;
    private int idCiclo;

    public AsignacionCarga(){

    }

    public AsignacionCarga(int idAsignacionCarga, String codigoAsignatura, int idCiclo) {
        this.idAsignacionCarga = idAsignacionCarga;
        this.codigoAsignatura = codigoAsignatura;
        this.idCiclo = idCiclo;
    }

    public int getIdAsignacionCarga() {
        return idAsignacionCarga;
    }

    public String getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public int getIdCiclo() {
        return idCiclo;
    }

    public void setIdAsignacionCarga(int idAsignacionCarga) {
        this.idAsignacionCarga = idAsignacionCarga;
    }

    public void setCodigoAsignatura(String codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
    }

    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }
}
