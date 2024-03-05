package es.ubu.lsi.echoserver;

/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.util.*;
import java.io.*;

public class EchoServer {
	
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println("Usage: java EchoServer <port number ini> <port number fin>");
            System.exit(1);
        }
        
        // Controlamos que el puerto inicial sea menor
        if (Integer.parseInt(args[0]) >= Integer.parseInt(args[1])) {
            System.err.println("El puerto inicial debe ser menor que el puerto final");
            System.err.println("Usage: java EchoServer <port number ini> <port number fin>");
            System.exit(1);
        }
        int portNumberIni = Integer.parseInt(args[0]);
        int portNumberFin = Integer.parseInt(args[1]);
        
        List<Integer> portList = listadoPuertos(portNumberIni, portNumberFin);
		
		//Puertos en la lista negra
		List<Integer> blacklist = Arrays.asList(20020, 20025);
	
		System.out.println("Listado de puertos vetados" + blacklist);
        System.out.println("Escuchando en los intervalos de puertos: " + portNumberIni + " - " + portNumberFin);
        
        for (Integer port: portList) {
        	Thread thread = new Thread(() -> {
		        try  (
		            	ServerSocket serverSocket = new ServerSocket(port);
		   		)
		        {
		            while (true){
		                Socket clientSocket = serverSocket.accept();
		                Integer puertoLocal = clientSocket.getLocalPort();
		                if (blacklist.contains(puertoLocal)) {
		                    clientSocket.close(); // Cerrar la conexión con el cliente
		                    throw new IOException("El puerto " + puertoLocal + " está en la lista negra");
		                } else {
			                System.out.println("Nuevo Cliente: " + clientSocket.getInetAddress() + "/" + clientSocket.getPort());
			            	Thread hilonuevocliente = new ThreadServerHandler(clientSocket);
			            	hilonuevocliente.start();
		                }
		            }
		        	
		        } catch (IOException e) {
		            System.out.println("Exception caught when trying to listen on port: " + port + " or listening for a connection");
		            System.out.println(e.getMessage());
		        }
        	});
        	thread.start();
        }
    }
    /**
     * Método listadoPuertos
     * 
     * Dado un puerto inicio y un puerto fin, devuelve el listado de puertos.
     * 
     * @param puertoIni
     * @param puertoFin
     * @return listado
     */
    private static List<Integer> listadoPuertos(Integer puertoIni, Integer puertoFin) {
    	
    	List<Integer> listado = new ArrayList<Integer>();
    	
    	for (Integer i = puertoIni; i<= puertoFin; i++) {
    		listado.add(i);
    	}
    	
    	return listado;
    }
}
    
class ThreadServerHandler extends Thread {
	
	private Socket clientSocket;
	
	public ThreadServerHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;
            
			while ((inputLine = in.readLine()) != null) {
            	System.out.println(clientSocket.getPort() + ":" + inputLine);
                out.println(inputLine);
            }
        }
        catch (IOException e) {
            System.out.println("Exception caught on thread");
            System.out.println(e.getMessage());
        }
      }
}