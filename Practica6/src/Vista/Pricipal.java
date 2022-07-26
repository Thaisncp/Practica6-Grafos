package Vista;

import Controlador.tda.grafosController.FarmaciaDatos;
import Controlador.tda.grafosController.UbicacionDatos;
import Controlador.tda.grafosController.FarmaciaGrafoController;
import Controlador.tda.lista.ListaEnlazada;
import Vista.ModeloTabla.ModeloTablaFarmacias;
import javax.swing.JOptionPane;



/**
 *
 * @author Thais Cartuche
 */
public class Pricipal extends javax.swing.JFrame {
   
    FarmaciaGrafoController grafo_farmacia = new FarmaciaGrafoController();
    private ModeloTablaFarmacias modeloTabla = new ModeloTablaFarmacias();
    FarmaciaDatos datosF = new FarmaciaDatos();
    UbicacionDatos ubicacionF = new UbicacionDatos();

    public Pricipal() {
        initComponents();
        btnModificar.setEnabled(false);
    }

    private void agregar() {
        if (txtNombre.getText().trim().length() == 0 || txtLongitud.getText().trim().length() == 0
                || txtLatitud.getText().trim().length() == 0 || txtLatitud.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Llene todos los datos", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            grafo_farmacia.getSitio().setNombre(txtNombre.getText());
            grafo_farmacia.getSitio().getUbicacion().setLatitud(Double.parseDouble(txtLatitud.getText()));
            grafo_farmacia.getSitio().getUbicacion().setLongitud(Double.parseDouble(txtLongitud.getText()));

            cargarTabla();

            ubicacionF = new UbicacionDatos(grafo_farmacia.getSitio().getUbicacion().getLongitud(), grafo_farmacia.getSitio().getUbicacion().getLatitud());
            datosF = new FarmaciaDatos(grafo_farmacia.getSitio().getNombre(), ubicacionF);
            grafo_farmacia.agregarGrafo(datosF);
            cargarCombos();
            limpiar();
            String[] aux = {""};
            jList.setListData(aux);
        }
    }
   private void busquedaAnchura() {
        Integer origen = (cbxOrigen.getSelectedIndex() + 1);
        try {
            Integer[] lista = grafo_farmacia.getGrafoUbi().busquedaAnchura(origen);
            String[] aux = new String[lista.length];
            for (int i = 0; i < lista.length; i++) {
                aux[i] = grafo_farmacia.getGrafoUbi().obtenerEtiqueta(lista[i]).toString();
            }
            jListAncho.setListData(aux);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cargarCombos() {
        cbxOrigen.removeAllItems();
        cbxDestino.removeAllItems();
        try {
            for (int i = 1; i <= grafo_farmacia.getGrafoUbi().numVertices(); i++) {
                cbxOrigen.addItem(grafo_farmacia.getGrafoUbi().obtenerEtiqueta(i).toString());
                cbxDestino.addItem(grafo_farmacia.getGrafoUbi().obtenerEtiqueta(i).toString());
            }
        } catch (Exception e) {
            System.out.println("ERROR EN CARGAR COMBO");
        }
    }

    private void limpiar() {
        txtLatitud.setText("");
        txtLongitud.setText("");
        txtNombre.setText("");
        txtLatitud.setText("");

    }

    private void adyacencia() {
        Integer origen = (cbxOrigen.getSelectedIndex() + 1);
        Integer destino = (cbxDestino.getSelectedIndex() + 1);

        if (origen == destino) {
            JOptionPane.showMessageDialog(null, "ESCOJA LUGARES DIFERENTES", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Double distancia = grafo_farmacia.calcularDistancia(grafo_farmacia.getGrafoUbi().obtenerEtiqueta(origen), grafo_farmacia.getGrafoUbi().obtenerEtiqueta(destino));
                grafo_farmacia.getGrafoUbi().insertarAristaE(grafo_farmacia.getGrafoUbi().obtenerEtiqueta(origen), grafo_farmacia.getGrafoUbi().obtenerEtiqueta(destino), distancia);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarTabla() {
        modeloTabla.setGrafoEND(grafo_farmacia.getGrafoUbi());
        modeloTabla.fireTableStructureChanged();
        modeloTabla.fireTableDataChanged();
        Jtable1.setModel(modeloTabla);
        Jtable1.updateUI();
        System.out.println(grafo_farmacia.getGrafoUbi().toString());
    }

    private void modificar() {
        if (txtNombre.getText().trim().length() == 0 || txtLongitud.getText().trim().length() == 0 || txtLatitud.getText().trim().length() == 0 || txtLatitud.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Datos incompletos para modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Integer pos = Jtable1.getSelectedRow();
                grafo_farmacia.getSitio().setNombre(txtNombre.getText());
                grafo_farmacia.getSitio().getUbicacion().setLatitud(Double.parseDouble(txtLatitud.getText()));
                grafo_farmacia.getSitio().getUbicacion().setLongitud(Double.parseDouble(txtLongitud.getText()));
                if (grafo_farmacia.getGrafoUbi().modificar(grafo_farmacia.getGrafoUbi().obtenerEtiqueta(pos), grafo_farmacia.getSitio())) {
                    cargarCombos();
                    
                    limpiar();
                    btnModificar.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Modificado con éxito", "OK", JOptionPane.INFORMATION_MESSAGE);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE PUDO MODIFICAR", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarVista() {
        Integer fila = -1;
        fila = Jtable1.getSelectedRow();
        if (fila >= 0) {
            try {
                grafo_farmacia.setSitio(grafo_farmacia.getGrafoUbi().obtenerEtiqueta(fila + 1));
                txtNombre.setText(grafo_farmacia.getSitio().getNombre());
                txtLatitud.setText(String.valueOf(grafo_farmacia.getSitio().getUbicacion().getLatitud()));
                txtLongitud.setText(String.valueOf(grafo_farmacia.getSitio().getUbicacion().getLongitud()));
                btnModificar.setEnabled(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escoja una fila de la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void caminoMinimos() {
        Integer origen = (cbxOrigen.getSelectedIndex() + 1);
        Integer destino = (cbxDestino.getSelectedIndex() + 1);
        if (origen == destino) {
            JOptionPane.showMessageDialog(null, "Elija lugares diferentes", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ListaEnlazada<Integer> lista = grafo_farmacia.getGrafoUbi().caminosFloyd(origen, destino);
                String[] aux = new String[lista.getSize()];
                for (int i = 0; i < lista.getSize(); i++) {
                    aux[i] = grafo_farmacia.getGrafoUbi().obtenerEtiqueta(lista.obtenerDato(i)).toString();
                }
                jList.setListData(aux);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbxOrigen = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxDestino = new javax.swing.JComboBox<>();
        btnVincular = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList<>();
        btnCaminos = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        txtLatitud = new javax.swing.JTextField();
        txtLongitud = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Jtable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListAncho = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Vincular Farmacias"));

        cbxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("ORIGEN");

        jLabel5.setText("DESTINO");

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnVincular.setText("VINCULAR");
        btnVincular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVincularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(btnVincular, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(cbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVincular))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Caminos"));

        jScrollPane2.setViewportView(jList);

        btnCaminos.setText("CAMINOS");
        btnCaminos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaminosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(btnCaminos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnCaminos)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Generar grafo"));

        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jLabel3.setText("Latitud:");

        jLabel2.setText("Longitud:");

        jLabel1.setText("Nombre: ");

        Jtable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Jtable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Jtable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Jtable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Jtable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnModificar)
                        .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtLongitud, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addComponent(txtNombre)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrar)
                            .addComponent(btnModificar))))
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jLabel6.setText("CAMINOS MINIMOS ALGORITMO DE FLOYD ");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Busquedas"));

        jScrollPane3.setViewportView(jListAncho);

        jButton1.setText("BUSQUEDA ANCHURA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(33, 33, 33))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        agregar();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCaminosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaminosActionPerformed
        caminoMinimos();
    }//GEN-LAST:event_btnCaminosActionPerformed

    private void btnVincularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVincularActionPerformed
        adyacencia();
    }//GEN-LAST:event_btnVincularActionPerformed

    private void Jtable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Jtable1MouseClicked
        cargarVista();
    }//GEN-LAST:event_Jtable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        busquedaAnchura();
    }//GEN-LAST:event_jButton1ActionPerformed
   
    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pricipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pricipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pricipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pricipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pricipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Jtable1;
    private javax.swing.JButton btnCaminos;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVincular;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jList;
    private javax.swing.JList<String> jListAncho;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField txtLatitud;
    private javax.swing.JTextField txtLongitud;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
