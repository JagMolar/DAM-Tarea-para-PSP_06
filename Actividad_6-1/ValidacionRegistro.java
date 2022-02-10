/*
 * TAREA PSP06. EJERCICIO 1.
 * Crea una aplicación que realice los siguientes pasos:
 * 1- Solicita el nombre del usuario que va a utilizar la aplicación. 
 * El login tiene una longitud de 8 caracteres y está compuesto únicamente 
 * por letras minúsculas.
 * 2- Solicita al usuario el nombre de un fichero que quiere mostrar. 
 * El nombre del fichero es como máximo de 8 caracteres y tiene una 
 * extensión de 3 caracteres.
 * 3- Visualiza en pantalla el contenido del fichero.
 * 
 * Es importante tener en cuenta que se tiene que realizar una validación de  
 * los datos de entrada y llevar un registro de la actividad del programa.
 * 
 * RECORDAR  COMENTAR EL PACKAGE SI SE QUIERE COMPILAR FUERA DE NETBEANS.
 */
//package validacionregistro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author juang <juangmuelas@gmail.com>
 * @since 17/03/2021
 * @version 1
 */
public class ValidacionRegistro {

    /**
     * Seguimos el esquema propuesto en el Ejemplo para validar
     * entradas en el apartado 2.4 del temario.
     * @param cliente String de recogida de datos
     * @param fichero String de recogida de datos
     * @param patCliente objeto de la clase pattern para recogida
     * de las expresiones regulares.
     * @param matCliente objeto de la clase pattern para evaluar
     * las expresiones regulares.
     * @param patArchivo objeto de la clase pattern para recogida
     * de las expresiones regulares.
     * @param matArchivo objeto de la clase pattern para evaluar
     * las expresiones regulares.
     */
    
    public ValidacionRegistro(){
        String cliente = new String();
        String fichero = new String();
        Pattern patCliente = null;
        Matcher matCliente = null;
        Pattern patArchivo = null;
        Matcher matArchivo = null;
        
        /**
         * Seguimos el esquema propuesto en el Ejemplo para los 
         * ficheros de registro en el apartado 2.6 del temario.
         * Aprovechamos por tanto parte de su código.
         * @param logger objeto de la clase Logger para controlar 
         * los eventos.
         * @param fh para el manejador que asocia al fichero.
         */
               
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        
        try {
          // Configuro el logger y establezco el formato
          fh = new FileHandler("MyLogFile.log", true);
          logger.addHandler(fh);
          logger.setLevel(Level.ALL); //ALL: Para registrar todos los eventos
          SimpleFormatter formatter = new SimpleFormatter();
          fh.setFormatter(formatter);
          /**
           * Tras revisar la documentación y comprobar varios ejemplos como
           * el encontrado en el artículo de Medium:logs-en-java-con-java-util
           * decido apartar los mensajes de registro de la vista
           */
          logger.setUseParentHandlers(false);  
        } catch (SecurityException e) {
          e.printStackTrace() ;
        } catch (IOException e) {
          e.printStackTrace() ;
        } 
        
        /**
         * Tras el apartado de registros, seguimos aprovechando el 
         * ejemplo del apartado 2.4
         * @param texto String que guarda los mensajes a mostrar.
         */
        String texto ="";

        // para leer del teclado
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));

        try{
            /**
             * Solicitamos el login y cotejamos con un patrón.
             */
            System.out.println("*****************************************");
            System.out.println("(solo con formato de 8 letras minusculas)");
            System.out.println("LOGIN: ");
            cliente=reader.readLine();
            patCliente=Pattern.compile("^[a-z]{8}$");
            matCliente=patCliente.matcher(cliente);

            /**
             * Comprobamos patrón correcto, se registra y sigue el proceso
             */

            if(matCliente.find()){
                //Si cumple con el patrón, informamos, registramos y continuamos
                logger.log(Level.CONFIG,"LOGIN: " + cliente + ": FORMATO CORRECTO.");
                System.out.println("Bienvenido,  "+cliente +" el LOGIN es correcto.");

                //Pedimos el nombre de archivo y comprobamos el patrón
                System.out.println("*****************************************");
                System.out.println("(Formato de 8 letras+extension de 3 (Ejemplo: Ficheros.dox)");
                System.out.println("INDIQUE LE ARCHIVO A OBTENER: ");

                fichero=reader.readLine();
                patArchivo=Pattern.compile("[a-zA-Z0-9]{1,8}.[a-zA-Z]{3}");
                matArchivo=patArchivo.matcher(fichero); 
                if(matArchivo.find()){
                    //Si cumple con el patrón, informamos, registramos y continuamos
                    logger.log(Level.CONFIG,"FILE: " + fichero + ": FORMATO CORRECTO.");
                    System.out.println("Obteniendo  "+ fichero +".");
                 }else{
                    //Si no cumple con el patrón                                             
                    logger.log(Level.CONFIG,"FILE " + fichero + ": RECHAZADO CON FORMATO NO VALIDO.");
                    System.out.println("Atención:  "+ fichero +" no tiene un formato valido.");

                 }


                File file= new File(fichero);
                /**
                 * Si el archivo existe,registramos los eventos.
                 */
                if(file.exists()){                  
                    logger.log(Level.INFO,"Fichero: "+ fichero + " solicitado existe.");
                    //Se abre el archivo
                    FileReader fr = new FileReader(fichero);
                    BufferedReader br = new BufferedReader(fr);
                    //mientras se recogan eventos, se imprimen.
                    while((texto = br.readLine())!=null) {
                        System.out.println(texto);                        
                    } 
                    //Cerramos archivo
                    br.close();
                    fr.close();

                    /**
                     * Se registra el evento que nos muestra el fichero
                     */
                    logger.log(Level.INFO,"Fichero: " + fichero + " mostrado correctamente.");
                }else{  
                    //En caso se que el archivo no exista
                    System.out.println("Fichero:  " + fichero + " no existe!.");
                    logger.log(Level.INFO,"Fichero: "+ fichero + " solicitado no existe.");
                }                   

            }else{
                /**
                 * Registrar e informar en caso de no cumplir con el patrón
                 */
                logger.log(Level.CONFIG,"LOGIN " + cliente + ": RECHAZADO CON FORMATO NO VALIDO.");
                System.out.println("Atención:  "+ cliente +" el LOGIN no tiene un formato valido.");
            }
               
          } catch( Exception e ) {
               System.out.println( e.getMessage() );
          }
    } //Fin constructor ValidacionRegistro
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        new ValidacionRegistro();
    } //Fin de main    
}//Fin clase ValidacionRegistro
