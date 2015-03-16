/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eze
 */
public class ReservaGui extends javax.swing.JInternalFrame {
    
    /**
     * Creates new form Reserva
     */
    public ReservaGui() {
        initComponents();
    }

    public JTextField getBusquedaCliente() {
        return busquedaCliente;
    }

    public JTextField getBusquedaCodigoArticulo() {
        return busquedaCodigoArticulo;
    }

    public String getFechaEntregaReserva() {
        if( fechaEntregaReserva.getCalendar() != null){
            int año = fechaEntregaReserva.getCalendar().get(Calendar.YEAR);
            int mes = fechaEntregaReserva.getCalendar().get(Calendar.MONTH) + 1;
            int dia = fechaEntregaReserva.getCalendar().get(Calendar.DAY_OF_MONTH);
            return año+"-"+mes+"-"+dia;
        }else{
            return null;
        }
    }

    public Date getFechaEntregaReservaDate(){
        return this.fechaEntregaReserva.getDate();
    }
    
    public String getFechaReserva() {
        if( fechaReserva.getCalendar() !=null){
            int año = fechaReserva.getCalendar().get(Calendar.YEAR);
            int mes = fechaReserva.getCalendar().get(Calendar.MONTH) + 1;
            int dia = fechaReserva.getCalendar().get(Calendar.DAY_OF_MONTH);
            return año+"-"+mes+"-"+dia;
        }else{
            return null;
        }
    }

    public JTable getTablaClienteReserva() {
        return tablaClienteReserva;
    }

    public JTable getTablaArticulosReserva() {
        return tablaArticulosReserva;
    }

    public JTable getTablaBusquedaArticulosReserva() {
        return tablaBusquedaArticulosReserva;
    }
    

    public JButton getConfirmarReserva() {
        return confirmarReserva;
    }

    public JButton getBttnCancelar() {
        return bttnCancelar;
    }

    public JButton getBttnCrearRemito() {
        return bttnCrearRemito;
    }

    public JButton getBttnEliminar() {
        return bttnEliminar;
    }
    
    public void setFechaEntregaReserva(Date date) {
        this.fechaEntregaReserva.setDate(date);
    }

    public void setFechaReserva(Date date) {
        this.fechaReserva.setDate(date);
    }

    public void setBusquedaCliente(String busquedaCliente) {
        this.busquedaCliente.setText( busquedaCliente);
    }
    
    public void limpiarComponentes(){
        this.busquedaCliente.setText("");
        this.busquedaCodigoArticulo.setText("");
        this.fechaEntregaReserva.setCalendar(null);
        this.fechaReserva.setCalendar(null);
        ((DefaultTableModel)this.tablaClienteReserva.getModel()).setRowCount(0);
        ((DefaultTableModel)this.tablaArticulosReserva.getModel()).setRowCount(0);
        ((DefaultTableModel)this.tablaBusquedaArticulosReserva.getModel()).setRowCount(0);
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
        panelBusquedaArticulosReserva = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaBusquedaArticulosReserva = new javax.swing.JTable();
        labelBusquedaCodigo = new javax.swing.JLabel();
        busquedaCodigoArticulo = new javax.swing.JTextField();
        panelBusquedaClienteReserva = new javax.swing.JPanel();
        busquedaCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaClienteReserva = new javax.swing.JTable();
        panelDatosYConfirmacionReserva = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fechaEntregaReserva = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        fechaReserva = new com.toedter.calendar.JDateChooser();
        confirmarReserva = new javax.swing.JButton();
        bttnCrearRemito = new javax.swing.JButton();
        bttnEliminar = new javax.swing.JButton();
        bttnCancelar = new javax.swing.JButton();
        panelArticulosReserva = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaArticulosReserva = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Reserva");
        setPreferredSize(new java.awt.Dimension(1082, 575));

        panelBusquedaArticulosReserva.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda de Artículos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        tablaBusquedaArticulosReserva.setAutoCreateRowSorter(true);
        tablaBusquedaArticulosReserva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Modelo", "Marca", "Tipo", "Talle", "Descripcion", "Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tablaBusquedaArticulosReserva);

        labelBusquedaCodigo.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelBusquedaCodigo.setText("Código");

        busquedaCodigoArticulo.setToolTipText("Buscar por Modelo, Marca,Tipo,Talle o Nombre de Ambo.");

        javax.swing.GroupLayout panelBusquedaArticulosReservaLayout = new javax.swing.GroupLayout(panelBusquedaArticulosReserva);
        panelBusquedaArticulosReserva.setLayout(panelBusquedaArticulosReservaLayout);
        panelBusquedaArticulosReservaLayout.setHorizontalGroup(
            panelBusquedaArticulosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaArticulosReservaLayout.createSequentialGroup()
                .addGroup(panelBusquedaArticulosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBusquedaArticulosReservaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelBusquedaCodigo)
                        .addGap(54, 54, 54)
                        .addComponent(busquedaCodigoArticulo))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelBusquedaArticulosReservaLayout.setVerticalGroup(
            panelBusquedaArticulosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaArticulosReservaLayout.createSequentialGroup()
                .addGroup(panelBusquedaArticulosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBusquedaCodigo)
                    .addComponent(busquedaCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBusquedaClienteReserva.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda de Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        busquedaCliente.setToolTipText("Buscar clientes por ID, Nombre o DNI");

        tablaClienteReserva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "DNI"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaClienteReserva);
        if (tablaClienteReserva.getColumnModel().getColumnCount() > 0) {
            tablaClienteReserva.getColumnModel().getColumn(0).setResizable(false);
            tablaClienteReserva.getColumnModel().getColumn(0).setPreferredWidth(8);
            tablaClienteReserva.getColumnModel().getColumn(1).setResizable(false);
            tablaClienteReserva.getColumnModel().getColumn(2).setResizable(false);
            tablaClienteReserva.getColumnModel().getColumn(2).setPreferredWidth(15);
        }

        javax.swing.GroupLayout panelBusquedaClienteReservaLayout = new javax.swing.GroupLayout(panelBusquedaClienteReserva);
        panelBusquedaClienteReserva.setLayout(panelBusquedaClienteReservaLayout);
        panelBusquedaClienteReservaLayout.setHorizontalGroup(
            panelBusquedaClienteReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaClienteReservaLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelBusquedaClienteReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addComponent(busquedaCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBusquedaClienteReservaLayout.setVerticalGroup(
            panelBusquedaClienteReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaClienteReservaLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(busquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDatosYConfirmacionReserva.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 3, 18)); // NOI18N
        jLabel3.setText("Fecha de Retiro");

        fechaEntregaReserva.setToolTipText("Fecha en que se entrega la reserva al Cliente");

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 3, 18)); // NOI18N
        jLabel2.setText("Fecha");

        fechaReserva.setToolTipText("Fecha en que se realiza la Reserva");

        confirmarReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/guardar.png"))); // NOI18N
        confirmarReserva.setText("Guardar");
        confirmarReserva.setToolTipText("Crear Reserva en Base de Datos");

        bttnCrearRemito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/enviar.png"))); // NOI18N
        bttnCrearRemito.setText("Crear Remito");
        bttnCrearRemito.setToolTipText("Crear un Remito con los datos de esta Reserva");

        bttnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/borrar.png"))); // NOI18N
        bttnEliminar.setText("Eliminar");
        bttnEliminar.setToolTipText("Borrar Reserva creada en Base de Datos");

        bttnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/cancelar.png"))); // NOI18N
        bttnCancelar.setText("Cancelar");
        bttnCancelar.setToolTipText("Descartar Reserva todavia no realizada");

        javax.swing.GroupLayout panelDatosYConfirmacionReservaLayout = new javax.swing.GroupLayout(panelDatosYConfirmacionReserva);
        panelDatosYConfirmacionReserva.setLayout(panelDatosYConfirmacionReservaLayout);
        panelDatosYConfirmacionReservaLayout.setHorizontalGroup(
            panelDatosYConfirmacionReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosYConfirmacionReservaLayout.createSequentialGroup()
                .addGroup(panelDatosYConfirmacionReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosYConfirmacionReservaLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(panelDatosYConfirmacionReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosYConfirmacionReservaLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(52, 52, 52)
                                .addComponent(fechaEntregaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosYConfirmacionReservaLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(119, 119, 119)
                                .addComponent(fechaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosYConfirmacionReservaLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(confirmarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bttnCrearRemito, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelDatosYConfirmacionReservaLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(bttnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bttnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        panelDatosYConfirmacionReservaLayout.setVerticalGroup(
            panelDatosYConfirmacionReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosYConfirmacionReservaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosYConfirmacionReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(20, 20, 20)
                .addGroup(panelDatosYConfirmacionReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(fechaEntregaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosYConfirmacionReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttnEliminar)
                    .addComponent(bttnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDatosYConfirmacionReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttnCrearRemito)
                    .addComponent(confirmarReserva))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelArticulosReserva.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Articulos de la Reserva", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        tablaArticulosReserva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Modelo", "Marca", "Tipo", "Talle", "Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
        jScrollPane3.setViewportView(tablaArticulosReserva);

        javax.swing.GroupLayout panelArticulosReservaLayout = new javax.swing.GroupLayout(panelArticulosReserva);
        panelArticulosReserva.setLayout(panelArticulosReservaLayout);
        panelArticulosReservaLayout.setHorizontalGroup(
            panelArticulosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelArticulosReservaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        panelArticulosReservaLayout.setVerticalGroup(
            panelArticulosReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelArticulosReservaLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBusquedaArticulosReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelArticulosReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBusquedaClienteReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDatosYConfirmacionReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelContenedorLayout.setVerticalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContenedorLayout.createSequentialGroup()
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBusquedaClienteReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBusquedaArticulosReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDatosYConfirmacionReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelArticulosReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(panelContenedor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnCancelar;
    private javax.swing.JButton bttnCrearRemito;
    private javax.swing.JButton bttnEliminar;
    private javax.swing.JTextField busquedaCliente;
    private javax.swing.JTextField busquedaCodigoArticulo;
    private javax.swing.JButton confirmarReserva;
    private com.toedter.calendar.JDateChooser fechaEntregaReserva;
    private com.toedter.calendar.JDateChooser fechaReserva;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelBusquedaCodigo;
    private javax.swing.JPanel panelArticulosReserva;
    private javax.swing.JPanel panelBusquedaArticulosReserva;
    private javax.swing.JPanel panelBusquedaClienteReserva;
    private javax.swing.JPanel panelContenedor;
    private javax.swing.JPanel panelDatosYConfirmacionReserva;
    private javax.swing.JTable tablaArticulosReserva;
    private javax.swing.JTable tablaBusquedaArticulosReserva;
    private javax.swing.JTable tablaClienteReserva;
    // End of variables declaration//GEN-END:variables

    public void setActionListener(ActionListener lis) {
        this.confirmarReserva.addActionListener(lis);
        this.bttnCrearRemito.addActionListener(lis);
        this.bttnEliminar.addActionListener(lis);
        this.bttnCancelar.addActionListener(lis);
    }
}