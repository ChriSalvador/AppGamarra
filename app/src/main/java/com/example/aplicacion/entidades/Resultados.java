package com.example.aplicacion.entidades;

public class Resultados {

    private int id;
    private String razonsoc;
    private String direccion;
    private String referencia;
    private String prenda;
    private String publico;
    private String zona;

    public Resultados() {
    }

    public Resultados(int id, String razonsoc, String direccion, String referencia, String prenda, String publico, String zona) {
        this.id = id;
        this.razonsoc = razonsoc;
        this.direccion = direccion;
        this.referencia = referencia;
        this.prenda = prenda;
        this.publico = publico;
        this.zona = zona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazonSoc() {
        return razonsoc;
    }

    public void setRazonSoc(String razonsoc) {
        this.razonsoc = razonsoc;
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

    public String getPrenda() {
        return prenda;
    }

    public void setPrenda(String prenda) {
        this.prenda = prenda;
    }

    public String getPublico() {
        return publico;
    }

    public void setPublico(String publico) {
        this.publico = publico;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
