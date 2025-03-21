package org.sistemasdistribuidos;

import java.rmi.RemoteException;

public class MyUBERImpl implements iMyUBER{
    //TODO #1
    @Override
    public Boolean registrarUsuario(String nombre, long telefono) throws RemoteException {
        return null;
    }

    //TODO #2
    @Override
    public String consutarTiposServicio() throws RemoteException {
        return "TODO";
    }

    //TODO #3
    @Override
    public String solicitarTaxi(int posXUsr, int posYUsr) throws RemoteException {
        return "TODO";
    }
}
