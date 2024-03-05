Carpeta con la entrega de la Sesión 02 - Ejercicio de Sockets
- La imagen CapturaCompleta.png tiene la prueba de las ejecuciones.
- En la carpeta EchoClientSocket está el fichero pom.xml con la configuración correcta desde donde poder lanzar todos los comandos necesarios:
	- mvn exec:java@server
	- mvn exec:java@cliente-pio (cliente bueno)
	- mvn exec:java@cliente-blas (cliente bueno)
	- mvn exec:java@cliente-vetado1 (cliente vetado)
	- mvn exec:java@cliente-vetado2 (cliente vetado) 