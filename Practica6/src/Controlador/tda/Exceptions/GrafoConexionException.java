/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Controlador.tda.Exceptions;

/**
 *
 * @author Thais Cartuche
 */
public class GrafoConexionException extends Exception{

    /**
     * Creates a new instance of <code>GrafoConexionException</code> without
     * detail message.
     */
    public GrafoConexionException() {
    }

    /**
     * Constructs an instance of <code>GrafoConexionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GrafoConexionException(String msg) {
        super(msg);
    }
}
