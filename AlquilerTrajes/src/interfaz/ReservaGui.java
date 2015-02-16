/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eze
 */
public class ReservaGui extends javax.swing.JInternalFrame {

    private final DefaultTableModel tablaArticulosDefault;
    
    /**
     * Creates new form Reserva
     */
    public ReservaGui() {
        initComponents();
        this.tablaArticulosDefault = (DefaultTableModel) tablaArticulos.getModel();//conveirto la tabla
        
    }

    public String getBusquedaCliente() {
        return busquedaCliente.getText();
    }

    public String getBusquedaCodigoArticulo() {
        return busquedaCodigoArticulo.getText();
    }

    public String getFechaEntregaReserva() {
        int año = fechaEntregaReserva.getCalendar().get(Calendar.YEAR);
        int mes = fechaEntregaReserva.getCalendar().get(Calendar.MONTH) + 1;
        int dia = fechaEntregaReserva.getCalendar().get(Calendar.DAY_OF_MONTH);
        return año+"-"+mes+"-"+dia;
    }

    public String getFechaReserva() {
        int año = fechaReserva.getCalendar().get(Calendar.YEAR);
        int mes = fechaReserva.getCalendar().get(Calendar.MONTH) + 1;
        int dia = fechaReserva.getCalendar().get(Calendar.DAY_OF_MONTH);
        return año+"-"+mes+"-"+dia;
    }

    public DefaultTableModel getTablaArticulosDefault() {
        return tablaArticulosDefault;
    }

    public JButton getConfirmarReserva() {
        return confirmarReserva;
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
        jPanel1 = new javax.swing.JPanel();
        panelTitulo = new org.edisoncor.gui.panel.PanelImage();
        titulo = new javax.swing.JLabel();
        panelReservaArticulos = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        labelBusquedaCodigo = new javax.swing.JLabel();
        busquedaCodigoArticulo = new javax.swing.JTextField();
        panelDatosReserva = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        busquedaCliente = new javax.swing.JTextField();
        fechaReserva = new com.toedter.calendar.JDateChooser();
        fechaEntregaReserva = new com.toedter.calendar.JDateChooser();
        panelConfirmarCancelarReserva = new javax.swing.JPanel();
        confirmarReserva = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1082, 575));

        panelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTitulo.setPreferredSize(new java.awt.Dimension(329, 49));

        titulo.setFont(new java.awt.Font("Century Schoolbook L", 3, 24)); // NOI18N
        titulo.setText("RESERVA");
        panelTitulo.add(titulo);

        panelReservaArticulos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Artículos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        tablaArticulos.setAutoCreateRowSorter(true);
        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Modelo", "Marca", "Tipo", "Talle", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tablaArticulos);

        labelBusquedaCodigo.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelBusquedaCodigo.setText("Código");

        busquedaCodigoArticulo.setToolTipText("Filtrar por código");

        javax.swing.GroupLayout panelReservaArticulosLayout = new javax.swing.GroupLayout(panelReservaArticulos);
        panelReservaArticulos.setLayout(panelReservaArticulosLayout);
        panelReservaArticulosLayout.setHorizontalGroup(
            panelReservaArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReservaArticulosLayout.createSequentialGroup()
                .addGroup(panelReservaArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReservaArticulosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelBusquedaCodigo)
                        .addGap(54, 54, 54)
                        .addComponent(busquedaCodigoArticulo))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelReservaArticulosLayout.setVerticalGroup(
            panelReservaArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReservaArticulosLayout.createSequentialGroup()
                .addGroup(panelReservaArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBusquedaCodigo)
                    .addComponent(busquedaCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setText("Cliente");

        jLabel2.setText("Fecha");

        jLabel3.setText("Fecha de Retiro");

        javax.swing.GroupLayout panelDatosReservaLayout = new javax.swing.GroupLayout(panelDatosReserva);
        panelDatosReserva.setLayout(panelDatosReservaLayout);
        panelDatosReservaLayout.setHorizontalGroup(
            panelDatosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosReservaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosReservaLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(fechaEntregaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 105, Short.MAX_VALUE))
                    .addGroup(panelDatosReservaLayout.createSequentialGroup()
                        .addGroup(panelDatosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDatosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosReservaLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(fechaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(busquedaCliente))))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        panelDatosReservaLayout.setVerticalGroup(
            panelDatosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosReservaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelDatosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(busquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(fechaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(panelDatosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(fechaEntregaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(196, Short.MAX_VALUE))
        );

        confirmarReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/agregar.png"))); // NOI18N
        confirmarReserva.setToolTipText("Realizar una nueva venta");

        javax.swing.GroupLayout panelConfirmarCancelarReservaLayout = new javax.swing.GroupLayout(panelConfirmarCancelarReserva);
        panelConfirmarCancelarReserva.setLayout(panelConfirmarCancelarReservaLayout);
        panelConfirmarCancelarReservaLayout.setHorizontalGroup(
            panelConfirmarCancelarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panelConfirmarCancelarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfirmarCancelarReservaLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(confirmarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelConfirmarCancelarReservaLayout.setVerticalGroup(
            panelConfirmarCancelarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(panelConfirmarCancelarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfirmarCancelarReservaLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(confirmarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelReservaArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDatosReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConfirmarCancelarReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelReservaArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelDatosReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelConfirmarCancelarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField busquedaCliente;
    private javax.swing.JTextField busquedaCodigoArticulo;
    private javax.swing.JButton confirmarReserva;
    private com.toedter.calendar.JDateChooser fechaEntregaReserva;
    private com.toedter.calendar.JDateChooser fechaReserva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelBusquedaCodigo;
    private javax.swing.JPanel panelConfirmarCancelarReserva;
    private javax.swing.JPanel panelDatosReserva;
    private javax.swing.JPanel panelReservaArticulos;
    private org.edisoncor.gui.panel.PanelImage panelTitulo;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables

    public void setActionListener(ActionListener lis) {
        this.confirmarReserva.addActionListener(lis);
    }
}