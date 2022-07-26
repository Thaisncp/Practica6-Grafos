package Modelo;

/**
 *
 * @author Thais Cartuche
 */
public class Ubicacion {
    
    private Double longitud;
    private Double latitud;

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Ubicacion() {
    }

    public Ubicacion(Double longitud, Double latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }
    
    @Override
    public String toString() {
        return "Longitud=" + longitud + ", Latitud=" + latitud;
    }
  
}
