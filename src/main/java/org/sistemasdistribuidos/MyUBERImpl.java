package org.sistemasdistribuidos;

import org.sistemasdistribuidos.entidades.Taxi;
import org.sistemasdistribuidos.entidades.TipoServicio;
import org.sistemasdistribuidos.entidades.Usuario;
import org.sistemasdistribuidos.persistencia.RepoTaxis;
import org.sistemasdistribuidos.persistencia.RepoUsuarios;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Implementación de la interfaz iMyUBER que proporciona los servicios remotos de
 * la aplicación my-UBER. Esta clase extiende UnicastRemoteObject para permitir
 * que sus instancias sean exportadas de manera automática como objetos remotos.
 * 
 * Los servicios implementados incluyen:
 * Registro de usuarios, consulta de tipos de servicio y solicitud de taxis.
 *
 * Autores: Melissa F. Ruiz, Juan Luis Ardila y Simon Diaz
 */

// UnicastRemoteObject genera un stub que permite exportar el objeto remoto
public class MyUBERImpl extends UnicastRemoteObject implements iMyUBER{

    /**
     * Constructor por defecto que inicializa el objeto remoto.
     * La superclase UnicastRemoteObject se encarga de exportar el objeto remoto para hacer que esté disponible para llamadas RMI.
     * 
     * @throws RemoteException si ocurre un error durante la exportación del objeto remoto
     */
    
    protected MyUBERImpl() throws RemoteException {
        super();
    }

    /**
     * Registra un nuevo usuario en el sistema MyUBER.
     * Realiza validaciones de los datos de entrada y verifica que el usuario no exista previamente en el sistema antes de registrarlo.
     *
     * @param nombre El nombre del usuario a registrar
     * @param telefono El número de teléfono del usuario
     * @return true si el registro fue exitoso, false en caso contrario
     * @throws RemoteException si ocurre un error durante la invocación remota
     */

    //TODO #1
    @Override
    public Boolean registrarUsuario(String nombre, long telefono) throws RemoteException {
        System.out.println("Cliente: Solicitud de registro de usuario: " + nombre);

        // Validaciones de entrada para asegurar datos correctos
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre no puede estar vacío");
            return false;
        }

        // validación de valor de teléfono que no puede ser un número negativo
        if (telefono <= 0) {
            System.err.println("Error: El teléfono debe ser un número positivo");
            return false;
        }

        // Verificamos si ya existe el usuario antes de intentar agregarlo para evitar duplicados en el sistema
        if (RepoUsuarios.verificarUsuarioExistente(nombre, telefono)) {
            System.err.println("Error: El usuario " + nombre + " ya existe en el sistema");
            return false;
        }

        // Si todas las validaciones anteriores son existosas, intentamos agregar el usuario al repositorio de usuarios
        boolean resultado = RepoUsuarios.agregarUsuario(nombre, telefono);

        // Registramos el resultado de la operación en la consola del servidor bien sea exitoso o error
        if (resultado) {
            System.out.println("Usuario " + nombre + " registrado exitosamente");
        } else {
            System.err.println("Error al registrar el usuario " + nombre);
        }

        return resultado;
    }

    /**
     * Consulta y devuelve información sobre los tipos de servicio disponibles
     * en el sistema my-UBER (normal, express, excursión).
     *
     * @return Un String formateado con la información de los tipos de servicio
     * @throws RemoteException si ocurre un error durante la invocación remota
     */
    
    @Override
    public String consutarTiposServicio() throws RemoteException{
        System.out.println ("Cliente: Consulta tipos de servicio");
        // Construimos una tabla formateada con la información
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-10s | %s%n", "Servicio", "Costo/Hora", "Descripción"));
        sb.append("-----------------------------------------------------------\n");
         // Iteramos sobre todos los valores de TipoServicio y agregamos cada uno al resultado
        for (TipoServicio tipo : TipoServicio.values()) {
            sb.append(tipo.toString()).append("\n");
        }
        return sb.toString();
    }


    //TODO #3
    
    /**
     * Solicita un taxi para un usuario registrado en base a su ubicación.
     * El método busca el taxi disponible más cercano a las coordenadas del usuario y lo asigna, marcándolo como ocupado.
     *
     * @param nombre Nombre del usuario que solicita el taxi
     * @param telefono Teléfono del usuario para verificar su identidad
     * @param posXUsr Coordenada en X del usuario en la matriz 10x10
     * @param posYUsr Coordenada en Y del usuario en la matriz 10x10
     * @return Mensaje indicando el resultado de la solicitud, incluyendo la placa del taxi asignado
     * @throws RemoteException si ocurre un error durante la invocación remota
     */
    
    @Override
    public String solicitarTaxi(String nombre, long telefono, int posXUsr, int posYUsr) throws RemoteException {
        System.out.println("Cliente: Solicitud de taxi para " + nombre + " en (" + posXUsr + ", " + posYUsr + ")");

        // Busca al usuario en la lista de usuarios registrados
        List<Usuario> usuarios = RepoUsuarios.obtenerUsuarios();
        Usuario usuario = null;

        // Buscamos el usuario que coincida con nombre y teléfono
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre) && u.getTelefono() == telefono) {
                usuario = u;
                break;
            }
        }

        // Si el usuario no existe, devuelve un mensaje de error
        if (usuario == null) {
            return "Error: Usuario no encontrado en el sistema.";
        }

        // Asignar las coordenadas ingresadas al usuario para el registre de su ubicación
        usuario.setCoordX(posXUsr);
        usuario.setCoordY(posYUsr);

        // Obtener la lista de taxis disponibles
        List<Taxi> taxis = RepoTaxis.obtenerTaxis();
        if (taxis.isEmpty()) {
            return "No hay taxis disponibles en este momento.";
        }

        // Busca  el taxi más cercano al usuario basado en la distancia euclidiana entre las coordenadas
        Taxi taxiCercano = null;
        double menorDistancia = -1;

        for (Taxi taxi : taxis) {
            // Solo son considerados taxis que no estén ocupados
            if (taxi.getEstaOcupado() == null) { // Solo considerar taxis libres
                // Cálculo de la distancia euclidiana entre el taxi y el usuario
                double distancia = Math.sqrt(Math.pow(taxi.getCoordX() - posXUsr, 2) +
                        Math.pow(taxi.getCoordY() - posYUsr, 2));

                // // Si es el primer taxi disponible o es más cercano que el actual, actualizamos el taxi más cercano
                if (taxiCercano == null || distancia < menorDistancia) {
                    menorDistancia = distancia;
                    taxiCercano = taxi;
                }
            }
        }

        // Si no hay taxis disponibles después de la búsqueda
        if (taxiCercano == null) {
            return "No hay taxis disponibles en este momento.";
        }

        // Asignar el taxi al usuario marcando este como ocupado
        taxiCercano.setEstaOcupado(usuario);

        //devuelve a la consola la info de la pedida de taxi con usuario que realizó solicitud y la placa del taxi asignado
        System.out.println("Taxi asignado a " + nombre + ": " + taxiCercano.getPlaca());
        return "Taxi asignado: " + taxiCercano.getPlaca();
    }

}
