package Vista.ModeloTabla;

import Controlador.tda.Exceptions.VerticeException;
import Controlador.tda.grafos.GrafoEND;
import Modelo.Farmacia;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Thais Cartuche
 */
public class ModeloTablaFarmacias extends AbstractTableModel {

    private GrafoEND<Farmacia> grafoEND;
    private String[] columnas;

    public GrafoEND getGrafoEND() {
        return grafoEND;
    }

    public void setGrafoEND(GrafoEND grafoEND) {
        this.grafoEND = grafoEND;
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    @Override
    public int getRowCount() {
        return grafoEND.numVertices();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nro";
            case 1:
                return "Nombres";
            case 2:
                return "Ubicacion";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        try {
            Farmacia lugar = grafoEND.obtenerEtiqueta(arg0+1);
            switch (arg1) {
                case 0:
                    return (arg0+1);
                case 1:
                    return lugar.getNombre();
                case 2:
                    return (lugar.getUbicacion() == null) ? "NO TIENE" : lugar.getUbicacion().toString();
                default:
                    return null;
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
            return null;
        }
    }

}
