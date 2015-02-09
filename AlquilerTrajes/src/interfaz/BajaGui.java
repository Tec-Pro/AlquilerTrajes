/*
 * To change this template, choose Tools | Templates
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
 * @author jacinto
 */
public class BajaGui extends javax.swing.JInternalFrame {

    private DefaultTableModel tablaArticulosDefault;//tabla default de los clientes


    /**
     * Creates new form RegistroAmbo
     */
    public BajaGui() {
        initComponents();
        tablaArticulosDefault = (DefaultTableModel) tablaArticulos.getModel();//conveirto la tabla
    }

    /**
     * Seteo el actionListener para los botones articulosALafactura,
     * clienteALafactura, facturaNueva, imprimir realizarVenta,
     * borrarArticulosSeleccionados, modificar
     *
     * @param
     * @return
     * @exception
     */
    public void setActionListener(ActionListener lis) {
        this.darBaja.addActionListener(lis);
    }

 
     public void paraVerVenta(boolean si){
         tablaArticulos.setEnabled(!si);
         busquedaCodigoArticulo.setEnabled(!si);
     }

     
    
    /**
     *
     * Retorno la tabla Articulos con tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return defaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaArticulosDefault() {
        return tablaArticulosDefault;
    }


    /**
     * Retorno el campo busquedaCodigoArticulo para poder filtrar las busquedas
     * dado el codigo de un articulo
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getBusquedaCodigoArticulo() {
        return busquedaCodigoArticulo;
    }

  
    /**
     * Retorno el campo del cliente de la factura
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getDescripcion() {
        return descripcion;
    }

    /**
     * Retorno boton facturaNueva en donde limpia los campos de la factura para
     * iniciar una nueva venta
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getDarBaja() {
        return darBaja;
    }

    /**
     * Retorno tabla articulos con tipo JTable
     *
     * @param
     * @return JTable
     * @exception
     */
    public JTable getTablaArticulos() {
        return tablaArticulos;
    }

    /**
     * Retorno el campo totalFactura que contiene el resultado final del a
     * factura
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getCobro() {
        return cobro;
    }


    public void limpiarVentana() {
        tablaArticulos.clearSelection();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        fondoImagen = new javax.swing.JPanel();
        panelTitulo = new org.edisoncor.gui.panel.PanelImage();
        titulo = new javax.swing.JLabel();
        panelClientesAarticulos = new javax.swing.JPanel();
        panelArticulos = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        labelBusquedaCodigo = new javax.swing.JLabel();
        busquedaCodigoArticulo = new javax.swing.JTextField();
        panelFactura = new javax.swing.JPanel();
        labelCliente = new javax.swing.JLabel();
        descripcion = new javax.swing.JTextField();
        Monto = new javax.swing.JLabel();
        cobro = new javax.swing.JTextField();
        panelControlFactura = new javax.swing.JPanel();
        darBaja = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Baja Articulo");
        setPreferredSize(new java.awt.Dimension(838, 440));

        jPanel1.setPreferredSize(new java.awt.Dimension(825, 448));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(822, 448));

        fondoImagen.setPreferredSize(new java.awt.Dimension(820, 400));

        panelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTitulo.setPreferredSize(new java.awt.Dimension(329, 49));

        titulo.setFont(new java.awt.Font("Century Schoolbook L", 3, 24)); // NOI18N
        titulo.setText("BAJA");
        panelTitulo.add(titulo);

        panelClientesAarticulos.setBorder(null);

        panelArticulos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Artículos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        tablaArticulos.setAutoCreateRowSorter(true);
        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Modelo", "Marca", "Tipo", "Talle", "Precio Compra"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
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

        javax.swing.GroupLayout panelArticulosLayout = new javax.swing.GroupLayout(panelArticulos);
        panelArticulos.setLayout(panelArticulosLayout);
        panelArticulosLayout.setHorizontalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelArticulosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelBusquedaCodigo)
                .addGap(54, 54, 54)
                .addComponent(busquedaCodigoArticulo))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
        );
        panelArticulosLayout.setVerticalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArticulosLayout.createSequentialGroup()
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBusquedaCodigo)
                    .addComponent(busquedaCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelClientesAarticulosLayout = new javax.swing.GroupLayout(panelClientesAarticulos);
        panelClientesAarticulos.setLayout(panelClientesAarticulosLayout);
        panelClientesAarticulosLayout.setHorizontalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesAarticulosLayout.createSequentialGroup()
                .addComponent(panelArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelClientesAarticulosLayout.setVerticalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelArticulos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelFactura.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        labelCliente.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelCliente.setText("Descripcion");

        descripcion.setEditable(false);

        Monto.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        Monto.setText("Cobro");

        javax.swing.GroupLayout panelFacturaLayout = new javax.swing.GroupLayout(panelFactura);
        panelFactura.setLayout(panelFacturaLayout);
        panelFacturaLayout.setHorizontalGroup(
            panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCliente)
                    .addComponent(Monto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFacturaLayout.createSequentialGroup()
                        .addComponent(descripcion)
                        .addContainerGap())
                    .addGroup(panelFacturaLayout.createSequentialGroup()
                        .addComponent(cobro, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                        .addGap(70, 70, 70))))
        );
        panelFacturaLayout.setVerticalGroup(
            panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturaLayout.createSequentialGroup()
                .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCliente)
                    .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Monto)
                    .addComponent(cobro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );

        panelControlFactura.setLayout(new java.awt.GridLayout(1, 0));

        darBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/borrar.png"))); // NOI18N
        darBaja.setToolTipText("Dar de baja");
        panelControlFactura.add(darBaja);

        javax.swing.GroupLayout fondoImagenLayout = new javax.swing.GroupLayout(fondoImagen);
        fondoImagen.setLayout(fondoImagenLayout);
        fondoImagenLayout.setHorizontalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addComponent(panelClientesAarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelControlFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(fondoImagenLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        fondoImagenLayout.setVerticalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addComponent(panelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoImagenLayout.createSequentialGroup()
                        .addComponent(panelFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(panelControlFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelClientesAarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jScrollPane1.setViewportView(fondoImagen);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 827, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Monto;
    private javax.swing.JTextField busquedaCodigoArticulo;
    private javax.swing.JTextField cobro;
    private javax.swing.JButton darBaja;
    private javax.swing.JTextField descripcion;
    private javax.swing.JPanel fondoImagen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelBusquedaCodigo;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelClientesAarticulos;
    private javax.swing.JPanel panelControlFactura;
    private javax.swing.JPanel panelFactura;
    private org.edisoncor.gui.panel.PanelImage panelTitulo;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}