package org.sistemasdistribuidos;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
* Autores: Melissa F. Ruiz, Juan Luis Ardila y Simon Diaz
 * Clase principal del servidor para la aplicación myUBER
 * Esta clase es responsable de iniciar el servidor RMI y registrar el objeto remoto
 */
public class MainServidor {
    public static void main(String[] args) {
        try {
            //Direccion estatica para el servidor (cambiar a la del pc donde se va a ejecutar)
            //Si se ejecutan como procesos locales dejar comentado esta propiedad
            //System.setProperty ("java.rmi.server.hostname", "192.168.20.205");

            //Crear RMI registry en el puerto 1099
            Registry registry = LocateRegistry.createRegistry (1099);
            System.out.println("RMI Registry creado en el puerto 1099");

            //el servidor MyUBER se registra en el regitry
            // Vincula el stub en el registro con el nombre "ObjetoRemotoMyUBER"
            registry.rebind ("ObjetoRemotoMyUBER", new MyUBERImpl());
            System.out.println("Objeto Remoto Creado");
        } catch (RemoteException re) {
            // Captura excepciones relacionadas con la comunicación remota
            System.err.println ("Error: " + re);
        }
    }
}
