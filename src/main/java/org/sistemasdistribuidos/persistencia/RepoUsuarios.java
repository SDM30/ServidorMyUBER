package org.sistemasdistribuidos.persistencia;

import org.sistemasdistribuidos.entidades.Usuario;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RepoUsuarios {
    private static List<Usuario> usuarios = null;
    private static final String NOMBRE_ARCHIVO = "Usuarios.txt";

    public static List<Usuario> obtenerUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            cargarUsuariosDesdeArchivo(NOMBRE_ARCHIVO);
        }
        return usuarios;
    }

    private static void cargarUsuariosDesdeArchivo(String nombreArchivo) {
        try (InputStream inputStream = RepoUsuarios.class.getClassLoader().getResourceAsStream(nombreArchivo);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

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

    public static Boolean verificarUsuarioExistente(String nombre, long telefono) {
        for (Usuario user : usuarios) {
            if (user.getNombre().equals(nombre) && user.getTelefono() == telefono) {
                return true;
            }
        }
        return false;
    }

    public static boolean agregarUsuario(String nombre, long telefono) {
        if (verificarUsuarioExistente(nombre, telefono)) {
            return false;
        }

        Usuario nuevoUsuario = new Usuario(nombre, telefono);
        usuarios.add(nuevoUsuario);

        escribirUsuarioEnArchivo(NOMBRE_ARCHIVO, nuevoUsuario);

        return true;
    }

    // Función que escribe (agrega) un usuario al archivo
    private static void escribirUsuarioEnArchivo(String nombreArchivo, Usuario usuario) {

        try (FileWriter fw = new FileWriter(nombreArchivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {


            out.println(usuario.getNombre() + ", " + usuario.getTelefono());
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
