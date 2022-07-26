/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.tda.Exceptions;

/**
 *
 * @author Thais Cartuche
 */
public class TopeException extends Exception {

    /**
     * Creates a new instance of <code>TopeException</code> without detail
     * message.
     */
    public TopeException() {
    }

    /**
     * Constructs an instance of <code>TopeException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public TopeException(String msg) {
        super(msg);
    }
}
