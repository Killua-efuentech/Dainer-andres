package Principal;


import Controllers.IngventaJpaController;
import Controllers.UserlogJpaController;
import Entity.Ingventa;
import Entity.Userlog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VistaHotel extends javax.swing.JFrame {
    
    private final EntityManagerFactory factory;
    private final Userlog userLog;
    private final UserlogJpaController ctrlLog;
    Ingventa ingVenta = null;
    IngventaJpaController ctrlVenta = null;
    List<String> oFaltantes = null;
    List<JTextField> campoFaltante = null;

    public VistaHotel(EntityManagerFactory factory, Userlog userLog, UserlogJpaController ctrlLog) {
        initComponents();
        this.factory = factory;
        this.userLog = userLog;
        this.ctrlLog = ctrlLog;
        ctrlVenta = new IngventaJpaController(factory);
        this.setTitle("Hotel");
    }
    
    private boolean validaDatosVentaObligatorios() {
        boolean obligatorio = false;
        oFaltantes = new ArrayList<>();
        campoFaltante = new ArrayList<>();

        if (jtfHabitacion.getText().trim().isEmpty()) {
            oFaltantes.add("Habitación");
            campoFaltante.add(jtfHabitacion);
        }
        if (jtfValor.getText().trim().isEmpty()) {
            oFaltantes.add("Valor");
            campoFaltante.add(jtfValor);
        }
        if (jcbTiempo.getSelectedItem() == "Tiempo") {
            oFaltantes.add("Tiempo");
            campoFaltante.add(jtfValor);
        }
        if (jcbTipo.getSelectedItem() == "Tipo") {
            oFaltantes.add("Tipo");
            campoFaltante.add(jtfValor);
        }
        if (jdcFecha.getDate() == null) {
            oFaltantes.add("Fecha");
            campoFaltante.add(jtfValor);
        }
        if (campoFaltante.size() > 0) {
            obligatorio = false;
        } else {
            obligatorio = true;
        }
        return obligatorio;
    }
    
    private void guardaVenta() {
        if (validaDatosVentaObligatorios()) {
            
            ingVenta = new Ingventa();
            
            //ingVenta.setIdlog(userLog);
            ingVenta.setFecha(jdcFecha.getDate().toString());
            ingVenta.setHabitacion(jtfHabitacion.getText().toUpperCase());
            ingVenta.setValor(Double.parseDouble(jtfValor.getText().toUpperCase()));
            ingVenta.setTiempo(jcbTiempo.getSelectedItem().toString());
            ingVenta.setTipo(jcbTipo.getSelectedItem().toString());
            ingVenta.setEstado(1);
            ctrlVenta.create(ingVenta);
            JOptionPane.showMessageDialog(null, "Usuario creado con exito!");
            
            limpiar();
            
        } else {
            String mensaje = "";
            for (int i = 0; i < oFaltantes.size(); i++) {
                mensaje += oFaltantes.get(i) + "\n";
            }
            JOptionPane.showMessageDialog(null, "Campos requeridos para continuar:\n\n" + mensaje, "Validacion de datos", JOptionPane.INFORMATION_MESSAGE);
            if (campoFaltante.size() > 0) {
                campoFaltante.get(0).requestFocus();
            }
        }
    }
    
    private void limpiar() {
        jtfHabitacion.setText("");
        jtfValor.setText("");
        jcbTiempo.setSelectedIndex(0);
        jcbTipo.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jtfHabitacion = new javax.swing.JTextField();
        jtfValor = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtTabla = new javax.swing.JTable();
        jbNuevo = new javax.swing.JButton();
        jbAgregar = new javax.swing.JButton();
        jcbTiempo = new javax.swing.JComboBox<>();
        jcbTipo = new javax.swing.JComboBox<>();
        jdcFecha = new org.netbeans.modules.form.InvalidComponent();
        jbBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtfHabitacion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Habitación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jtfValor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Valor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jtTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Habitación", "Valor", "Tiempo", "Tipo"
            }
        ));
        jtTabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtTablaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtTabla);

        jbNuevo.setText("Nuevo");
        jbNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevoActionPerformed(evt);
            }
        });

        jbAgregar.setText("Agregar");
        jbAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbAgregarMouseClicked(evt);
            }
        });
        jbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActionPerformed(evt);
            }
        });

        jcbTiempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiempo", "Rato", "Amanecida" }));

        jcbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo", "Sencilla", "Temática" }));

        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 936, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtfHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcbTiempo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jtfValor, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfValor, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed

    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jbNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoActionPerformed
        limpiar();
    }//GEN-LAST:event_jbNuevoActionPerformed

    private void jtTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtTablaKeyPressed
       
    }//GEN-LAST:event_jtTablaKeyPressed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed

    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAgregarMouseClicked
        guardaVenta();
    }//GEN-LAST:event_jbAgregarMouseClicked



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAgregar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbNuevo;
    private javax.swing.JComboBox<String> jcbTiempo;
    private javax.swing.JComboBox<String> jcbTipo;
    private org.netbeans.modules.form.InvalidComponent jdcFecha;
    private javax.swing.JTable jtTabla;
    private javax.swing.JTextField jtfHabitacion;
    private javax.swing.JTextField jtfValor;
    // End of variables declaration//GEN-END:variables

}
