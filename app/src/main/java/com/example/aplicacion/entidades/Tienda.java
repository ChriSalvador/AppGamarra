package com.example.aplicacion.entidades;

import java.io.Serializable;

public class Tienda implements Serializable {
    private Integer idtienda;
    private Integer idprenda;
    private Integer idpublico;
    private Integer idzona;
    private String ruc;
    private String nombre;
    private String apellido;
    private String direccion;
    private String referencia;

    public Tienda(Integer idtienda, Integer idprenda, Integer idpublico, Integer idzona, String ruc, String nombre, String apellido, String direccion, String referencia) {
        this.idtienda = idtienda;
        this.idprenda = idprenda;
        this.idpublico = idpublico;
        this.idzona = idzona;
        this.ruc = ruc;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.referencia = referencia;
    }

    public Tienda() {
    }

    public Integer getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(Integer idtienda) {
        this.idtienda = idtienda;
    }

    public Integer getIdprenda() {
        return idprenda;
    }

    public void setIdprenda(Integer idprenda) {
        this.idprenda = idprenda;
    }

    public Integer getIdpublico() {
        return idpublico;
    }

    public void setIdpublico(Integer idpublico) {
        this.idpublico = idpublico;
    }

    public Integer getIdzona() {
        return idzona;
    }

    public void setIdzona(Integer idzona) {
        this.idzona = idzona;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
