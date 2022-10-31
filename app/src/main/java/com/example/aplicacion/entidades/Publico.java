package com.example.aplicacion.entidades;

import java.io.Serializable;

public class Publico implements Serializable {

    private Integer idpublico;
    private String descripcion;

    public Publico(Integer idpublico, String descripcion) {
        this.idpublico = idpublico;
        this.descripcion = descripcion;
    }

    public Publico() {
    }

    public Integer getIdpublico() {
        return idpublico;
    }

    public void setIdpublico(Integer idpublico) {
        this.idpublico = idpublico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
