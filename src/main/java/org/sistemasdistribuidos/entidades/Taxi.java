package org.sistemasdistribuidos.entidades;
/*
 * Clase taxi: conductores registrados en la aplicacion
 */
public class Taxi {
    private int coordX;
    private int coordY;
    private String placa;
    private Usuario estaOcupado;

    public Taxi(int coordX, int coordY, String placa) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.placa = placa;
        this.estaOcupado = null;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Usuario getEstaOcupado() {
        return estaOcupado;
    }

    public void setEstaOcupado(Usuario estaOcupado) {
        this.estaOcupado = estaOcupado;
    }
}
