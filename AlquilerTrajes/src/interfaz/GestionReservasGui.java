/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eze
 */
public class GestionReservasGui extends javax.swing.JInternalFrame {

    /**
     * Creates new form GestionReservasGui
     */
    public GestionReservasGui() {
        initComponents();
    }

    public JButton getBttnNuevaReserva() {
        return bttnNuevaReserva;
    }

    public JButton getBttnNuevoRemito() {
        return bttnNuevoRemito;
    }

    public JButton getBttnRemitoEncontrado() {
        return bttnRemitoEncontrado;
    }

    public JButton getBttnReservaEncontrada() {
        return bttnReservaEncontrada;
    }

    public JTextField getjTxtBuscarRemito() {
        return jTxtBuscarRemito;
    }

    public JTextField getjTxtBuscarReserva() {
        return jTxtBuscarReserva;
    }

    public JTable getTablaBuscarRemito() {
        return tablaBuscarRemito;
    }

    public JTable getTablaBuscarReserva() {
        return tablaBuscarReserva;
    }
    
    public void limpiarComponentes(){
        ((DefaultTableModel)this.tablaBuscarRemito.getModel()).setRowCount(0);
        ((DefaultTableModel)this.tablaBuscarReserva.getModel()).setRowCount(0);
        this.jTxtBuscarReserva.setText("");
        this.jTxtBuscarRemito.setText("");
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelContenedor = new javax.swing.JPanel();
        panelBusquedaRemito = new javax.swing.JPanel();
        jTxtBuscarRemito = new javax.swing.JTextField();
        bttnRemitoEncontrado = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaBuscarRemito = new javax.swing.JTable();
        panelBusquedaReserva = new javax.swing.JPanel();
        jTxtBuscarReserva = new javax.swing.JTextField();
        bttnReservaEncontrada = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaBuscarReserva = new javax.swing.JTable();
        panelNuevaReservaRemito = new javax.swing.JPanel();
        bttnNuevoRemito = new javax.swing.JButton();
        bttnNuevaReserva = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestión de Reservas y Facturación");
        setPreferredSize(new java.awt.Dimension(1082, 575));

        panelBusquedaRemito.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar Remito", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        jTxtBuscarRemito.setToolTipText("Buscar por Fecha, Cliente o Número de Remito.");

        bttnRemitoEncontrado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/ok.png"))); // NOI18N
        bttnRemitoEncontrado.setText("Aceptar");

        tablaBuscarRemito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Número", "Cliente", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaBuscarRemito);

        javax.swing.GroupLayout panelBusquedaRemitoLayout = new javax.swing.GroupLayout(panelBusquedaRemito);
        panelBusquedaRemito.setLayout(panelBusquedaRemitoLayout);
        panelBusquedaRemitoLayout.setHorizontalGroup(
            panelBusquedaRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaRemitoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBusquedaRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBusquedaRemitoLayout.createSequentialGroup()
                        .addComponent(jTxtBuscarRemito)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bttnRemitoEncontrado)))
                .addGap(25, 25, 25))
        );
        panelBusquedaRemitoLayout.setVerticalGroup(
            panelBusquedaRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaRemitoLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBusquedaRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttnRemitoEncontrado)
                    .addComponent(jTxtBuscarRemito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelBusquedaReserva.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar Reserva", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        jTxtBuscarReserva.setToolTipText("Buscar por Fecha de Entrega de la Reserva(AAAA-MM-DD) o Cliente (Nombre).");
        jTxtBuscarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtBuscarReservaActionPerformed(evt);
            }
        });

        bttnReservaEncontrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/ok.png"))); // NOI18N
        bttnReservaEncontrada.setText("Aceptar");

        tablaBuscarReserva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fecha", "Fecha Entrega", "Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaBuscarReserva);

        javax.swing.GroupLayout panelBusquedaReservaLayout = new javax.swing.GroupLayout(panelBusquedaReserva);
        panelBusquedaReserva.setLayout(panelBusquedaReservaLayout);
        panelBusquedaReservaLayout.setHorizontalGroup(
            panelBusquedaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaReservaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBusquedaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBusquedaReservaLayout.createSequentialGroup()
                        .addComponent(jTxtBuscarReserva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bttnReservaEncontrada))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        panelBusquedaReservaLayout.setVerticalGroup(
            panelBusquedaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaReservaLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBusquedaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBuscarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bttnReservaEncontrada))
                .addContainerGap())
        );

        panelNuevaReservaRemito.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        bttnNuevoRemito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/remito.png"))); // NOI18N
        bttnNuevoRemito.setText("Remito");
        bttnNuevoRemito.setToolTipText("Nuevo Remito");

        bttnNuevaReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/reserva.png"))); // NOI18N
        bttnNuevaReserva.setText("Reserva");

        javax.swing.GroupLayout panelNuevaReservaRemitoLayout = new javax.swing.GroupLayout(panelNuevaReservaRemito);
        panelNuevaReservaRemito.setLayout(panelNuevaReservaRemitoLayout);
        panelNuevaReservaRemitoLayout.setHorizontalGroup(
            panelNuevaReservaRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNuevaReservaRemitoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelNuevaReservaRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bttnNuevoRemito, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bttnNuevaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        panelNuevaReservaRemitoLayout.setVerticalGroup(
            panelNuevaReservaRemitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNuevaReservaRemitoLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(bttnNuevaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(bttnNuevoRemito, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBusquedaReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBusquedaRemito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelNuevaReservaRemito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelContenedorLayout.setVerticalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addComponent(panelBusquedaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBusquedaRemito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(panelNuevaReservaRemito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panelContenedor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtBuscarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtBuscarReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtBuscarReservaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnNuevaReserva;
    private javax.swing.JButton bttnNuevoRemito;
    private javax.swing.JButton bttnRemitoEncontrado;
    private javax.swing.JButton bttnReservaEncontrada;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTxtBuscarRemito;
    private javax.swing.JTextField jTxtBuscarReserva;
    private javax.swing.JPanel panelBusquedaRemito;
    private javax.swing.JPanel panelBusquedaReserva;
    private javax.swing.JPanel panelContenedor;
    private javax.swing.JPanel panelNuevaReservaRemito;
    private javax.swing.JTable tablaBuscarRemito;
    private javax.swing.JTable tablaBuscarReserva;
    // End of variables declaration//GEN-END:variables


    public void setActionListener(ActionListener lis) {
        this.bttnNuevaReserva.addActionListener(lis);
        this.bttnNuevoRemito.addActionListener(lis);
        this.bttnRemitoEncontrado.addActionListener(lis);
        this.bttnReservaEncontrada.addActionListener(lis);
    }
    
}
