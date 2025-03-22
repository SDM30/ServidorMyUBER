package org.sistemasdistribuidos;

import org.sistemasdistribuidos.entidades.TipoServicio;
import org.sistemasdistribuidos.entidades.Usuario;
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
    public String solicitarTaxi(int posXUsr, int posYUsr) throws RemoteException {
        return "TODO";
    }
}
