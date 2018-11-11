package com.monse.andrea.proyectofinal.clases;

public class Cliente
{
    private String nombre;
    private String ubicacion;
    private String foto;

    public Cliente() {
    }

    public Cliente(String nombre, String ubicacion, String foto) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
