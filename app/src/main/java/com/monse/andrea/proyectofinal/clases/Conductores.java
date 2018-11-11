package com.monse.andrea.proyectofinal.clases;

public class Conductores
{
    private String nombre;
    private String ubicacion;
    private String foto;
    private String color;
    private String marca;
    private String placa;

    public Conductores() {
    }

    public Conductores(String nombre, String ubicacion, String foto, String color, String marca, String placa) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.foto = foto;
        this.color = color;
        this.marca = marca;
        this.placa = placa;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}

