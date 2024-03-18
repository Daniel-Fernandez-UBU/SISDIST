package es.ubu.lsi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Cliente remoto.
 */
public class Cliente {

	/**
	 * Constructor oculto,
	 */
    private Cliente() {}


	/**
	 * Método raíz.
	 *
	 * @param args host con el registro
	 */
    public static void main(String[] args) {

		String host = (args.length < 1) ? null : args[0];
		try {
		   Registry registry = LocateRegistry.getRegistry(host);
		   // Resuelve el objeto remoto (la referencia a...)
	 	   HolaMundo stub = (HolaMundo) registry.lookup("Hola");
	 	   String respuesta = stub.decirHola();
	       System.out.println("Respuesta del servidor remoto: " + respuesta);
	       
	       Scanner sc = new Scanner(System.in);
	       
	       int eleccion;
	       
	       float numero1, numero2, resultado = 0;
	       
	       String menu = "\n------------------\n\n[-1] => Salir\n[0] => Sumar\n[1] => Restar\n[2] => Multiplicar\n[3] => Dividir\nElige: ";
	        do {
	            System.out.println(menu);

	            try {
	                eleccion = Integer.parseInt(sc.nextLine());
	            } catch (NumberFormatException e) {
	                eleccion = -1;
	            }

	            if(eleccion != -1){

	            	System.out.println("Ingresa el número 1: ");
	            	try{
	                	numero1 = Float.parseFloat(sc.nextLine());
	            	}catch(NumberFormatException e){
	            		numero1 = 0;
	            	}

	            	System.out.println("Ingresa el número 2: ");
	            	try{
	                	numero2 = Float.parseFloat(sc.nextLine());
	            	}catch(NumberFormatException e){
	            		numero2 = 0;
	            	}
	                switch (eleccion) {
		                case 0:
		                    resultado = stub.sumar(numero1, numero2);
		                    break;
		                case 1:
		                    resultado = stub.restar(numero1, numero2);
		                    break;
		                case 2:
		                    resultado = stub.multiplicar(numero1, numero2);
		                    break;
		                case 3:
		                    resultado = stub.dividir(numero1, numero2);
		                    break;
		            }

	                System.out.println("Resultado => " + String.valueOf(resultado));
	                System.out.println("Presiona ENTER para continuar");
	                sc.nextLine();
	            }
	        } while (eleccion != -1);
	        
	        sc.close(); //Cerramos la espera de consola
	       
		} 
		catch (Exception e) {
	    	System.err.println("Excepción en cliente: " + e.toString());
		} // try
		
    } // main
    
} // Cliente