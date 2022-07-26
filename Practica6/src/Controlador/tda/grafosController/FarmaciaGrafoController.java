package Controlador.tda.grafosController;

import Controlador.tda.grafos.GrafoEND;
import Modelo.Farmacia;
import Modelo.Ubicacion;

/**
 *
 * @author Thais Cartuche
 */
public class FarmaciaGrafoController {

    private GrafoEND<Farmacia> grafoUbi;
    private Farmacia sitio;
    int i = 1;

    public FarmaciaGrafoController() {
    }

    public Farmacia guardarDatos(String nombre, String tipo, Double latitud, Double longitud) {
        Farmacia ubi = new Farmacia();
        ubi.setNombre(nombre);
        Ubicacion u = new Ubicacion();
        u.setLatitud(latitud);
        u.setLongitud(longitud);
        ubi.setUbicacion(u);

        return ubi;
    }

    public void agregarGrafo(Farmacia lugar) {
        grafoUbi.ActualizarGrafo();
        grafoUbi.etiquetarVertice(i, lugar);
        i++;
    }

    public GrafoEND<Farmacia> getGrafoUbi() {
        if (grafoUbi == null) {
            grafoUbi = new GrafoEND<>(0, Farmacia.class);
        }
        return grafoUbi;
    }

    public void setGrafoUbi(GrafoEND<Farmacia> grafoUbi) {
        this.grafoUbi = grafoUbi;
    }

    public Farmacia getSitio() {
        if (sitio == null) {
            sitio = new Farmacia();
        }
        return sitio;
    }

    public void setSitio(Farmacia sitio) {
        this.sitio = sitio;
    }


    public Double calcularDistancia(Farmacia puntoOrigen, Farmacia puntoDestino) {
        Double distancia = 0.0;
        Double x = puntoOrigen.getUbicacion().getLongitud() - puntoDestino.getUbicacion().getLongitud();
        Double y = puntoOrigen.getUbicacion().getLatitud() - puntoDestino.getUbicacion().getLatitud();
        distancia = Math.sqrt((x * x) + (y * y));
        return Math.round(distancia)*100.0/100.0;
    }

}
