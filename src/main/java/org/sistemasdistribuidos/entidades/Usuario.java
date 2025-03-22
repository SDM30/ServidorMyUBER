package org.sistemasdistribuidos.entidades;

/*
 * Clase Usuario: representa a los usuarios registrados en la app
 */
public class Usuario {
    private String nombre;
    private long telefono;
    private int coordX; // Coordenada X del usuario
    private int coordY; // Coordenada Y del usuario

    public Usuario(String nombre, long telefono){
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Usuario(String nombre, long telefono, int coordX, int coordY) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.coordX = coordX;
        this.coordY = coordY;
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

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
}
