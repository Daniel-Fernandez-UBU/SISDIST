package es.ubu.lsi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface remota.
 */
public interface HolaMundo extends Remote {
	
	/**
	 * Devuelve un texto con un saludo.
	 *
	 * @return texto de saludo
	 * @throws RemoteException problema en acceso remoto
	 */
    String decirHola() throws RemoteException;
    
    /**
     * Añadimos cuatro métodos más para simular una calculadora sencilla.
     * 
     * @param numero1
     * @param numero2
     * @return
     * @throws RemoteException
     */
    float sumar(float numero1, float numero2) throws RemoteException;
    float restar(float numero1, float numero2) throws RemoteException;
    float multiplicar(float numero1, float numero2) throws RemoteException;
    float dividir(float numero1, float numero2) throws RemoteException;
    
} // HolaMundo