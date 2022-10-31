package com.example.aplicacion.entidades;

import java.io.Serializable;

public class Zona implements Serializable {

    private Integer idzona;
    private String nombrezona;

    public Zona(Integer idzona, String nombrezona) {
        this.idzona = idzona;
        this.nombrezona = nombrezona;
    }

    public Zona() {
    }

    public Integer getIdzona() {
        return idzona;
    }

    public void setIdzona(Integer idzona) {
        this.idzona = idzona;
    }

    public String getNombrezona() {
        return nombrezona;
    }

    public void setNombrezona(String nombrezona) {
        this.nombrezona = nombrezona;
    }
}
