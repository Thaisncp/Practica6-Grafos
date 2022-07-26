package Modelo;

/**
 *
 * @author Thais Cartuche
 */
public class Farmacia {
    private String nombre;
    private Ubicacion ubicacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ubicacion getUbicacion() {
         if (ubicacion == null) {
            ubicacion = new Ubicacion();
        }
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Farmacia(String nombre, Ubicacion ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public Farmacia() {
    }

    @Override
    public String toString() {
        return nombre;
    }
   
}
