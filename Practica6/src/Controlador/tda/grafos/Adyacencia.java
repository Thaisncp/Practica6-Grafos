package Controlador.tda.grafos;

/**
 *
 * @author Thais Cartuche
 */
public class Adyacencia {
    
    private Integer destino;
    private Double peso;

    public Adyacencia(Integer destino, Double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Integer getDestino() {
        return destino;
    }

    public void setDestino(Integer destino) {
        this.destino = destino;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "destino=" + destino + ", peso=" + peso;
    }
    
    
    
}
