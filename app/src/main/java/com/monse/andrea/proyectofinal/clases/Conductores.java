package com.monse.andrea.proyectofinal.clases;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Conductores
{
    private String nombre;
    private String ubicacion;
    private String destino;
    private String foto;
    private String color;
    private String marca;
    private String placa;
    private String estado;

    public Conductores() {
    }

    public Conductores(String nombre, String ubicacion, String destino, String foto, String color, String marca, String placa, String estado) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.destino = destino;
        this.foto = foto;
        this.color = color;
        this.marca = marca;
        this.placa = placa;
        this.estado = estado;
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

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

