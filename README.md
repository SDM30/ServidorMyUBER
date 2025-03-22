
---

# Introducción a los sistemas distribuidos: Taller 1 - Servidor  

**Autores:** Juan Luis Ardila, Simón Díaz y Melissa Ruíz  

## Autores  

- [@Juan Luis Ardila](https://github.com/jardila20)  
- [@Simon Diaz](https://github.com/SDM30)  
- [@Melissa Ruiz](https://github.com/mfruiz1025)  

## Cómo ejecutar el programa  

### Ejecutar código desde IDE  

Descargar el código fuente del servidor y ejecutarlo en el IDE de preferencia.  

### Ejecutar como JAR  

1. Descarga el código fuente y dirígete al directorio `ServidorMyUBER/target`.  
2. Ejecuta el jar con el siguiente comando:  

   ```
   java -jar ServidorMyUBER-1.0-SNAPSHOT-jar-with-dependencies.jar 
   ```  

### Ejecutar como proceso  

No modificar el código fuente y ejecutarlo directamente. En otra ventana, ejecuta el [cliente](https://github.com/SDM30/ClienteMyUBER).  

### Ejecutar en varias máquinas  

1. En el código fuente, entra a la clase `MainServidor`.  
2. Quita el comentario a la línea:  

   ```
   System.setProperty("java.rmi.server.hostname", "IP PC servidor");
   ```  

3. Ejecuta el código en el IDE o vuelve a crear el JAR con el comando:  

   ```
   mvn clean package 
   ```  

## Almacenamiento de usuarios  

Los usuarios creados se guardan en la siguiente ruta:  

**`C:\Usuarios\TU_USUARIO\Taller1SD`** en Windows  
**`/home/TU_USUARIO/Taller1SD`** en Linux y Mac  
