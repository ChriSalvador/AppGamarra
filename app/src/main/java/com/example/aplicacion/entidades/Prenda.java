package com.example.aplicacion.entidades;

import java.io.Serializable;

public class Prenda implements Serializable {

    private Integer idprenda;
    private String nombreprenda;

    public Prenda(Integer idprenda, String nombreprenda) {
        this.idprenda = idprenda;
        this.nombreprenda = nombreprenda;
    }

    public Prenda() {
    }

    public Integer getIdprenda() {
        return idprenda;
    }

    public void setIdprenda(Integer idprenda) {
        this.idprenda = idprenda;
    }

    public String getNombreprenda() {
        return nombreprenda;
    }

    public void setNombreprenda(String nombreprenda) {
        this.nombreprenda = nombreprenda;
    }
}
