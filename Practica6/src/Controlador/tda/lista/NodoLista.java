
package Controlador.tda.lista;

/**
 *
 * @author sebastian
 */

public class NodoLista <E> {
    
    private E dato;
    
    private NodoLista<E> siguiente;

    public NodoLista(E dato, NodoLista<E> siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }    
    
    public NodoLista() {
        this.dato = null;
        siguiente = null;
    }
    
    public E getDato() {
        return dato;
    }

    public void setDato(E dato) {
        this.dato = dato;
    }

    public NodoLista<E> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoLista<E> siguiente) {
        this.siguiente = siguiente;
    }
    
}
