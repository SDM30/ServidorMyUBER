package org.sistemasdistribuidos.entidades;
/*
* Clase usuario: representa a los usuarios registrados en la app
 */
public class Usuario {
    private String nombre;
    private long telefono;

    public Usuario(String nombre, long telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }
}
