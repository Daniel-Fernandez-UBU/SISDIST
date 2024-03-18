package es.ubu.lsi;
	
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Servidor remoto.
 *
 */	
public class Servidor implements HolaMundo {
	
	/**
	 * {@inheritDoc}.
	 *
	 * @return {@inheritDoc}
	 */
    public String decirHola() {
		return "Hola mundo!";
    }
	
	
	@Override
	public float sumar(float numero1, float numero2) throws RemoteException {
		return numero1 + numero2;
	}

	@Override
	public float restar(float numero1, float numero2) throws RemoteException {
		return numero1 - numero2;
	}

	@Override
	public float multiplicar(float numero1, float numero2) throws RemoteException {
		return numero1 * numero2;
	}

	@Override
	public float dividir(float numero1, float numero2) throws RemoteException {
		return numero1 / numero2;
	}
	
	/**
	 * Método raíz.
	 *
	 * @param args argumentos
	 */
    public static void main(String args[]) throws RemoteException, AlreadyBoundException {
	
		try {
		    Servidor obj = new Servidor();
		    
		    // si no hereda de UnicastRemoteObject es necesario exportar
	    	HolaMundo stub = (HolaMundo) UnicastRemoteObject.exportObject(obj, 0);

		    // Liga el resguardo de objeto remoto en el registro
	    	Registry registro = LocateRegistry.getRegistry();
	    	registro.bind("Hola", stub);
	
	    	System.out.println("Servidor preparado");
		}
		catch (Exception e) {
		    System.err.println("Excepción de servidor: " + e.toString());
		}

    } // main
    
} // Servidor