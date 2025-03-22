package org.sistemasdistribuidos.persistencia;

import org.sistemasdistribuidos.entidades.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepoUsuarios {
    // Lista en memoria de los usuarios
    private static List<Usuario> usuarios = null;

    // Constantes para las rutas de archivos
    private static final String NOMBRE_ARCHIVO = "Usuarios.txt";
    private static final String DIRECTORIO_APP = System.getProperty("user.home") +
            File.separator + "Taller1SD";

    /**
     * Obtiene la lista de usuarios, inicializándola si es necesario.
     * Retorna la lista de usuarios cargada desde el archivo externo o, si está vacío, desde los recursos.
     */
    public static List<Usuario> obtenerUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            inicializarDirectorios();
            cargarUsuariosDesdeArchivo();
        }
        return usuarios;
    }

    /**
     * Crea el directorio de la aplicación si no existe.
     */
    private static void inicializarDirectorios() {
        File directorio = new File(DIRECTORIO_APP);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado: " + DIRECTORIO_APP);
            } else {
                System.err.println("No se pudo crear el directorio: " + DIRECTORIO_APP);
            }
        }
    }

    /**
     * Obtiene la ruta completa del archivo de usuarios.
     * Retorna la ruta completa del archivo.
     */
    private static String obtenerRutaCompleta() {
        return DIRECTORIO_APP + File.separator + NOMBRE_ARCHIVO;
    }

    /**
     * Carga los usuarios desde el archivo externo.
     * Si el archivo no existe, se crea uno vacío.
     * Si el archivo está vacío, carga los usuarios desde el recurso en src/main/resources.
     */
    private static void cargarUsuariosDesdeArchivo() {
        String rutaCompleta = obtenerRutaCompleta();
        File archivo = new File(rutaCompleta);

        // Si el archivo no existe, creamos uno vacío
        if (!archivo.exists()) {
            try {
                if (archivo.createNewFile()) {
                    System.out.println("Archivo creado: " + rutaCompleta);
                }
            } catch (IOException e) {
                System.err.println("Error al crear el archivo: " + e.getMessage());
                return;
            }
        }

        // Si el archivo está vacío, cargar desde recursos
        if (archivo.length() == 0) {
            System.out.println("El archivo externo está vacío. Cargando usuarios desde recursos.");
            cargarUsuariosDesdeRecurso();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCompleta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(", ");
                if (partes.length == 2) {
                    try {
                        String nombre = partes[0];
                        long telefono = Long.parseLong(partes[1]);
                        usuarios.add(new Usuario(nombre, telefono));
                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear teléfono en línea: " + linea);
                    }
                }
            }
            System.out.println("Usuarios cargados desde: " + rutaCompleta);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Carga los usuarios desde el recurso ubicado en src/main/resources/Usuarios.txt.
     */
    /**
     * Carga los usuarios desde el recurso ubicado en src/main/resources/Usuarios.txt.
     * Luego, los escribe en el archivo externo.
     */
    private static void cargarUsuariosDesdeRecurso() {
        try (InputStream is = RepoUsuarios.class.getResourceAsStream("/Usuarios.txt")) {
            if (is == null) {
                System.err.println("El recurso Usuarios.txt no se encontró en el classpath.");
                return;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(", ");
                    if (partes.length == 2) {
                        try {
                            String nombre = partes[0];
                            long telefono = Long.parseLong(partes[1]);
                            Usuario usuario = new Usuario(nombre, telefono);
                            usuarios.add(usuario);

                            // Escribir el usuario en el archivo externo
                            escribirUsuarioEnArchivo(usuario);
                        } catch (NumberFormatException e) {
                            System.err.println("Error al parsear teléfono en línea: " + linea);
                        }
                    }
                }
                System.out.println("Usuarios cargados desde el recurso Usuarios.txt y guardados en el archivo.");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el recurso Usuarios.txt: " + e.getMessage());
        }
    }


    /**
     * Verifica si un usuario ya existe en la lista.
     * @param nombre Nombre del usuario.
     * @param telefono Teléfono del usuario.
     * @return true si el usuario existe, false en caso contrario.
     */
    public static Boolean verificarUsuarioExistente(String nombre, long telefono) {
        // Aseguramos que la lista esté inicializada
        obtenerUsuarios();

        for (Usuario user : usuarios) {
            if (user.getNombre().equals(nombre) && user.getTelefono() == telefono) {
                return true;
            }
        }
        return false;
    }

    /**
     * Agrega un nuevo usuario si no existe.
     * @param nombre Nombre del usuario.
     * @param telefono Teléfono del usuario.
     * @return true si se agregó correctamente, false si ya existía.
     */
    public static boolean agregarUsuario(String nombre, long telefono) {
        // Validaciones de entrada
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre no puede estar vacío");
            return false;
        }

        if (telefono <= 0) {
            System.err.println("El teléfono debe ser un número positivo");
            return false;
        }

        // Verificar si el usuario ya existe
        if (verificarUsuarioExistente(nombre, telefono)) {
            System.err.println("El usuario ya existe en el sistema");
            return false;
        }

        // Crear y agregar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(nombre, telefono);
        usuarios.add(nuevoUsuario);

        // Guardar en el archivo externo
        return escribirUsuarioEnArchivo(nuevoUsuario);
    }

    /**
     * Escribe un usuario en el archivo externo.
     * @param usuario Usuario a escribir.
     * @return true si se escribió correctamente, false en caso contrario.
     */
    private static boolean escribirUsuarioEnArchivo(Usuario usuario) {
        inicializarDirectorios();
        String rutaCompleta = obtenerRutaCompleta();

        try (FileWriter fw = new FileWriter(rutaCompleta, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(usuario.getNombre() + ", " + usuario.getTelefono());
            System.out.println("Usuario agregado al archivo: " + usuario.getNombre());
            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
