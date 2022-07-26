package Controlador.tda.Exceptions;

/**
 *
 * @author Thais Cartuche
 */
public class VerticeException extends Exception{

    /**
     * Creates a new instance of <code>VerticeException</code> without detail
     * message.
     */
    public VerticeException() {
    }

    /**
     * Constructs an instance of <code>VerticeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VerticeException(String msg) {
        super(msg);
    }
}
