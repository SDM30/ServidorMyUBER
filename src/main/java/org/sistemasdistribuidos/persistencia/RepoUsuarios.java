package org.sistemasdistribuidos.persistencia;

import org.sistemasdistribuidos.entidades.Usuario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepoUsuarios {
    private static List<Usuario> usuarios = null;

    public static List<Usuario> obtenerUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            cargarUsuariosDesdeArchivo("Usuarios.txt");
        }
        return usuarios;
    }

    private static void cargarUsuariosDesdeArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(", ");
                if (partes.length == 2) {
                    String nombre = partes[0];
                    long telefono = Long.parseLong(partes[1]);
                    usuarios.add(new Usuario(nombre, telefono));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
