package org.sistemasdistribuidos;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Autores: Juan Luis Ardila, Simon Diaz y Melissa F. Ruiz, 
 *
 * Interfaz remota para la aplicación my-UBER.
 * Define los métodos que pueden ser invocados remotamente por los clientes a través de RMI
 * 
 * Extiende la interfaz Remote para permitir que sus métodos sean llamados desde una máquina virtual Java diferente.
 */

public interface iMyUBER extends Remote {
    /**
     * Permite registrar un nuevo usuario en el sistema MyUBER.
     * 
     * @param nombre Nombre del usuario a registrar
     * @param telefono Número de teléfono del usuario
     * @return true si el registro fue exitoso, false si el usuario ya existe o hubo un error
     * @throws RemoteException si ocurre un error durante la invocación remota
     */
    
    public Boolean registrarUsuario(String nombre, long telefono) throws RemoteException;
    /**
     * Consulta y devuelve información sobre los tipos de servicio disponibles en el sistema my-UBER (normal, express y excursión) con sus respectivos costos y descripciones.
     * 
     * @return Un String formateado con la información de los tipos de servicio
     * @throws RemoteException si ocurre un error durante la invocación remota
     */
    
    public String consutarTiposServicio() throws RemoteException;
    /**
     * Solicita un taxi para un usuario registrado en base a su ubicación.
     * Asigna el taxi disponible más cercano a las coordenadas del usuario.
     * 
     * @param nombre Nombre del usuario que solicita el taxi
     * @param telefono Teléfono del usuario para verificar su identidad
     * @param posXUsr Coordenada X del usuario en la matriz 10x10
     * @param posYUsr Coordenada Y del usuario en la matriz 10x10
     * @return Mensaje indicando el resultado de la solicitud, incluyendo la placa del taxi asignado si es exitosa
     * @throws RemoteException si ocurre un error durante la invocación remota
     */
    
    public String solicitarTaxi(String nombre, long telefono, int posXUsr, int posYUsr) throws RemoteException;
}
