package Controlador.tda.grafos;

import Controlador.tda.Exceptions.VerticeException;

/**
 *
 * @author Thais Cartuche
 */
public class GrafoND extends GrafoD{

    public GrafoND(Integer numV) {
        super(numV);
    }
    
    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeException {
        if (i > 0 && j > 0 &&i <= numV && j <= numV) {
            Object[] existe = existeArista(i, j);
            if (!((Boolean) existe[0])) {
                numA++;
                listaAdyacente[i].insertarCabecera(new Adyacencia(j, peso));
                listaAdyacente[j].insertarCabecera(new Adyacencia(i, peso));
            }
        } else {
            throw new VerticeException("Algun vertice ingresado no existe");
        }
    }

}
