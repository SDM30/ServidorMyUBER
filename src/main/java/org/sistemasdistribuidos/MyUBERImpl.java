package org.sistemasdistribuidos;

import org.sistemasdistribuidos.entidades.Taxi;
import org.sistemasdistribuidos.entidades.TipoServicio;
import org.sistemasdistribuidos.entidades.Usuario;
import org.sistemasdistribuidos.persistencia.RepoTaxis;
import org.sistemasdistribuidos.persistencia.RepoUsuarios;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

// UnicastRemoteObject genera un stub que permite exportar el objeto remoto
public class MyUBERImpl extends UnicastRemoteObject implements iMyUBER{

    //Constructor sin parametros, inicializa el stub
    protected MyUBERImpl() throws RemoteException {
        super();
    }

    //TODO #1
    @Override
    public Boolean registrarUsuario(String nombre, long telefono) throws RemoteException {
        System.out.println("Cliente: Solicitud de registro de usuario: " + nombre);

        // Validaciones de entrada
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre no puede estar vacío");
            return false;
        }

        if (telefono <= 0) {
            System.err.println("Error: El teléfono debe ser un número positivo");
            return false;
        }

        // Verificamos si ya existe el usuario antes de intentar agregarlo
        if (RepoUsuarios.verificarUsuarioExistente(nombre, telefono)) {
            System.err.println("Error: El usuario " + nombre + " ya existe en el sistema");
            return false;
        }

        // Si llegamos aquí, intentamos agregar el usuario
        boolean resultado = RepoUsuarios.agregarUsuario(nombre, telefono);

        if (resultado) {
            System.out.println("Usuario " + nombre + " registrado exitosamente");
        } else {
            System.err.println("Error al registrar el usuario " + nombre);
        }

        return resultado;
    }

    @Override
    public String consutarTiposServicio() throws RemoteException{
        System.out.println ("Cliente: Consulta tipos de servicio");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-10s | %s%n", "Servicio", "Costo/Hora", "Descripción"));
        sb.append("-----------------------------------------------------------\n");
        for (TipoServicio tipo : TipoServicio.values()) {
            sb.append(tipo.toString()).append("\n");
        }
        return sb.toString();
    }


    //TODO #3
    @Override
    public String solicitarTaxi(String nombre, long telefono, int posXUsr, int posYUsr) throws RemoteException {
        System.out.println("Cliente: Solicitud de taxi para " + nombre + " en (" + posXUsr + ", " + posYUsr + ")");

        // Busca al usuario en la lista de usuarios registrados
        List<Usuario> usuarios = RepoUsuarios.obtenerUsuarios();
        Usuario usuario = null;

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

        // Asignar las coordenadas ingresadas al usuario
        usuario.setCoordX(posXUsr);
        usuario.setCoordY(posYUsr);

        // Obtener la lista de taxis disponibles
        List<Taxi> taxis = RepoTaxis.obtenerTaxis();
        if (taxis.isEmpty()) {
            return "No hay taxis disponibles en este momento.";
        }

        // Busca el primer taxi disponible para inicializar la distancia mínima
        Taxi taxiCercano = null;
        double menorDistancia = -1;

        for (Taxi taxi : taxis) {
            if (taxi.getEstaOcupado() == null) { // Solo considerar taxis libres
                double distancia = Math.sqrt(Math.pow(taxi.getCoordX() - posXUsr, 2) +
                        Math.pow(taxi.getCoordY() - posYUsr, 2));

                // Si es el primer taxi o es más cercano, actualizar valores
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

        // Asignar el taxi al usuario
        taxiCercano.setEstaOcupado(usuario);

        System.out.println("Taxi asignado a " + nombre + ": " + taxiCercano.getPlaca());
        return "Taxi asignado: " + taxiCercano.getPlaca();
    }

}