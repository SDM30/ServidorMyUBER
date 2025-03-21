package org.sistemasdistribuidos.persistencia;

import org.sistemasdistribuidos.entidades.Usuario;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        try (InputStream inputStream = RepoUsuarios.class.getClassLoader().getResourceAsStream(nombreArchivo);
             BufferedReader br = new BufferedReader(new InputStreamReader (inputStream, StandardCharsets.UTF_8))) {

            if (inputStream == null) {
                System.err.println("No se pudo encontrar el archivo: " + nombreArchivo);
                return;
            }

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
