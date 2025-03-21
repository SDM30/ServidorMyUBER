package org.sistemasdistribuidos.entidades;
/*
 * Enum Tipo Servicio: Contiene los servicios ofrecidos por la app myUBER
 */
public enum TipoServicio {
    NORMAL (50000, "Taxis amarillos."),
    EXPRESS (70000, "Taxis más amplios y cómodos."),
    EXCURSION (120000, "Te llevan a cualquier lugar y te esperan.");

    private final int costoHora;
    private final String descripcion;

    TipoServicio(int costoHora, String descripcion) {
        this.costoHora = costoHora;
        this.descripcion = descripcion;
    }

    public int getCostoHora() {
        return costoHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-10d | %s", name(), costoHora, descripcion);
    }
}
