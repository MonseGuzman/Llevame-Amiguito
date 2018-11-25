package com.monse.andrea.proyectofinal.clases;

public class Cliente
{
    private String nombre;
    private String ubicacion;
    private String destino;
    private String foto;

    public Cliente() {
    }

    public Cliente(String nombre, String ubicacion, String destino, String foto) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.destino = destino;
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

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
