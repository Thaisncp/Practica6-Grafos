package Controlador.tda.grafos;

import Controlador.tda.Exceptions.PosicionException;
import Controlador.tda.Exceptions.VerticeException;
import Controlador.tda.lista.ListaEnlazada;

/**
 *
 * @author Thais Cartuche
 */
public class GrafoD extends Grafo {

    protected Integer numV;
    protected Integer numA;
    protected ListaEnlazada<Adyacencia> listaAdyacente[];

    public GrafoD(Integer numV) {
        this.numV = numV;
        this.numA = 0;
        listaAdyacente = new ListaEnlazada[numV + 1];
        for (int i = 0; i <= this.numV; i++) {
            listaAdyacente[i] = new ListaEnlazada<>();
        }
    }

    @Override
    public Integer numVertices() {
        return this.numV;
    }

    @Override
    public Integer numAristas() {
        return this.numA;
    }

    /**
     * Permite verificar si existe una conexion entre aristas
     *
     * @param i vertice inicial
     * @param f vertice final
     * @return arreglo de objetos: en la posicion 0 regresa un boolean y en la 1
     * el peso
     * @throws VerticeException
     */
    @Override
    public Object[] existeArista(Integer i, Integer f) throws VerticeException {
        Object[] resultado = {Boolean.FALSE, Double.NaN};
        if (i > 0 && f > 0 && i <= numV && f <= numV) {
            ListaEnlazada<Adyacencia> lista = listaAdyacente[i];
            for (int j = 0; j < lista.getSize(); j++) {
                try {
                    Adyacencia aux = lista.obtenerDato(j);
                    if (aux.getDestino().intValue() == f.intValue()) {
                        resultado[0] = Boolean.TRUE;
                        resultado[1] = aux.getPeso();
                        break;
                    }
                } catch (PosicionException ex) {

                }
            }
        } else {
            throw new VerticeException("Algun vertice ingresado no existe");
        }
        return resultado;
    }

    @Override
    public Double pesoArista(Integer i, Integer f) throws VerticeException {
        Double peso = Double.NaN;
        Object[] existe = existeArista(i, f);
        if (((Boolean) existe[0])) {
            peso = (Double) existe[1];
        }
        return peso;
    }

    @Override
    public void insertarArista(Integer i, Integer j) throws VerticeException {
        insertarArista(i, j, Double.NaN);
    }

    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeException {
        if (i > 0 && j > 0 && i <= numV && j <= numV) {
            Object[] existe = existeArista(i, j);
            if (!((Boolean) existe[0])) {
                numA++;
                listaAdyacente[i].insertarCabecera(new Adyacencia(j, peso));
            }
        } else {
            throw new VerticeException("Algun vertice ingresado no existe");
        }

    }

    @Override
    public ListaEnlazada<Adyacencia> adyacente(Integer i) throws VerticeException {
        return listaAdyacente[i];
    }

    public void generarAumento(Integer numV, Integer numA, ListaEnlazada<Adyacencia>[] listaAdyacente) {
        this.numV = numV;
        this.numA = numA;
        this.listaAdyacente = listaAdyacente;
    }

    public void nuevoGrafo() {
        GrafoD copia = new GrafoD(numVertices() + 1);
        try {
            for (int i = 0; i < numVertices(); i++) {
                ListaEnlazada<Adyacencia> lista = adyacente(i);
                for (int j = 0; j < lista.getSize(); j++) {
                    Adyacencia aux = lista.obtenerDato(j);
                    copia.insertarArista(i, aux.getDestino(), aux.getPeso());
                }
            }
            generarAumento(copia.numVertices(), copia.numAristas(), copia.listaAdyacente);
        } catch (Exception e) {
            System.out.println("ERROR COPIA: " + e);
        }
    }

    public ListaEnlazada<Integer> caminosFloyd(Integer origen, Integer destino) throws VerticeException {
        ListaEnlazada<Integer> camino = new ListaEnlazada<>();
        Double[][] recorrido = new Double[numV + 1][numV + 1];
        Integer[][] traza = new Integer[numV + 1][numV + 1];
        Integer[][] traza2 = new Integer[numV + 1][numV + 1];

        for (int i = 1; i < numV; i++) {
            for (int j = 1; j < numV; j++) {
                traza2[i][j] = j;
                traza[i][j] = i;
                if (i == j) {
                    recorrido[i][j] = 0.0;
                    traza2[i][j] = 0;
                    traza[i][j] = 0;
                }else{
                    if (!(Boolean)existeArista(i, j)[0]) {
                        recorrido[i][j]=Double.POSITIVE_INFINITY;
                    } else {
                        recorrido[i][j] = pesoArista(i, j);
                    }
                }

            }
        }
        
        for (int k= 1; k < numV; k++) {
            for (int i = 1; i < numV; i++) {
                for (int j = 1; j < numV; j++) {
                    if (recorrido[i][k]+recorrido[k][j] < recorrido[i][j]) {
                        recorrido[i][j] = recorrido[i][k]+recorrido[k][j];
                        traza2[i][j]=traza[k][j];
                    }
                }
            }
        }
        
        Integer i = origen;
        Integer j = destino;
        
        while(i != j){
            camino.insertarCabecera(i);
            i = traza2[i][j];
        }
        
        camino.insertarCabecera(i);
        return camino;
        
    }
    
    public Integer[] busquedaAnchura(Integer origen) throws PosicionException {
        Integer[] visitados = new Integer[numV];
        visitados[0] = origen;
        Integer contador = 1;
        int i=0 ;
        boolean band = false;
        while (contador < numV) {
            i++;
            for (int j = 0; j < listaAdyacente[origen].getSize(); j++) {
                for (int k = 0; k < visitados.length; k++) {
                    if (visitados[k] == listaAdyacente[origen].obtenerDato(j).getDestino()) {
                        band = true;
                        break;
                    } else {
                        band = false;
                    }
                }
                if (!band) {
                    visitados[contador] = listaAdyacente[origen].obtenerDato(j).getDestino();
                    contador++;
                }
            }

            if (origen == numV) {
                origen = 0;
            }
            if (contador == numV) {
                break;
            }
            origen++;
            if (i > numV) {
                break;
            }
        }
        return visitados;
    }
    
    public Integer[] busquedaProfundidad(Integer origen) throws PosicionException {
        Integer[] visitados = new Integer[numV];
        visitados[0] = origen;
        Integer contador = 1;
        int i=0 ;
        boolean band = false;
        while (contador < numV) {
            i++;
            for (int j = 0; j < listaAdyacente[origen].getSize(); j++) {
                for (int k = 0; k < visitados.length; k++) {
                    if (visitados[k] == listaAdyacente[origen].obtenerDato(j).getDestino()) {
                        band = true;
                        break;
                    } else {
                        band = false;
                    }
                }
                if (!band) {
                    visitados[contador] = listaAdyacente[origen].obtenerDato(j).getDestino();
                    contador++;
                }
            }

            if (origen == numV) {
                origen = 0;
            }
            if (contador == numV) {
                break;
            }
            origen++;
            if (i > numV) {
                break;
            }
        }
        return visitados;
    }
}
