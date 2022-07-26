package Controlador.tda.grafos;

import Controlador.tda.Exceptions.VerticeException;
/**
 *
 * @author Thais Cartuche
 */
public class GrafoEND<E> extends GrafoDE<E> {

    public GrafoEND(Integer numV, Class clazz) {
        super(numV, clazz);
    }

    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeException {
        if (i > 0 && j > 0 && i <= numV && j <= numV) {
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

    @Override
    public void insertarAristaE(E i, E j, Double peso) throws Exception {
        insertarArista(obtenerCodigo(i), obtenerCodigo(j), peso);
        insertarArista(obtenerCodigo(j), obtenerCodigo(i), peso);
    }

    public void generarGrafoN() {
        super.ActualizarGrafo();
    }

}
