package com.example.reciclerviewactividad.Modelo;

public class Categoria {
    private String id;
    private String descripcion;

    public Categoria(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}