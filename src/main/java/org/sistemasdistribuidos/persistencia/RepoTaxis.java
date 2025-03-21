package org.sistemasdistribuidos.persistencia;

import org.sistemasdistribuidos.entidades.Taxi;

import java.util.List;

public class RepoTaxis {
    private static List<Taxi> taxis;
    public static List<Taxi> obtenerTaxis () {
        if (taxis.isEmpty()) {
            taxis.add(new Taxi(1, 1, "XXC23"));
            taxis.add(new Taxi(5, 4, "XCV33"));
            taxis.add(new Taxi(6, 9, "GHJ45"));
            taxis.add(new Taxi(3, 8, "RR167"));
            taxis.add(new Taxi(4, 4, "GGT55"));
            taxis.add(new Taxi(2, 3, "HHW33"));
        }
        return taxis;
    }
}
