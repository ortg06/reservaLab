package com.example.oscar.reservalab;

/**
 * Created by cbren on 11/5/2018.
 */

public class Profesor {

    private String idProfesor;
    private String nombreProfesor;
    private String idUsuario;
    private int idAsignacionCarga;

    public Profesor(){


    }

    public Profesor(String idProfesor, String nombreProfesor, String idUsuario, int idAsignacionCarga) {
        this.idProfesor = idProfesor;
        this.nombreProfesor = nombreProfesor;
        this.idUsuario = idUsuario;
        this.idAsignacionCarga = idAsignacionCarga;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public int getIdAsignacionCarga() {
        return idAsignacionCarga;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdAsignacionCarga(int idAsignacionCarga) {
        this.idAsignacionCarga = idAsignacionCarga;
    }
}

