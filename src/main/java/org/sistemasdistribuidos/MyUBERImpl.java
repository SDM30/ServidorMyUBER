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
        System.out.println ("Cliente: Registro usuario");
        List<Usuario> usuarios = RepoUsuarios.obtenerUsuarios();
        if (RepoUsuarios.agregarUsuario (nombre, telefono)) {
            return true;
        } else {
            System.err.println ("El usuario a registrar ya existe");
        }
        return false;
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
