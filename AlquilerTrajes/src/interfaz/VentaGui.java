/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jacinto
 */
public class VentaGui extends javax.swing.JInternalFrame {

    private DefaultTableModel tablaArticulosDefault;//tabla default de los clientes
    private DefaultTableModel tablaFacturaDefault;
    private DefaultTableModel tablaClientesDefault;

    /**
     * Creates new form VentaGui
     */
    public VentaGui() {
        initComponents();
        tablaArticulosDefault = (DefaultTableModel) tablaArticulos.getModel();//conveirto la tabla
        tablaFacturaDefault = (DefaultTableModel) tablaFactura.getModel();
        tablaClientesDefault = (DefaultTableModel) tablaClientes.getModel();
        Calendar miCalendario = Calendar.getInstance();
        java.util.Date eldia = miCalendario.getTime();
        int diaHoy = miCalendario.get(Calendar.DAY_OF_MONTH);
        int mes = miCalendario.get(Calendar.MONTH);
        int anio = miCalendario.get(Calendar.YEAR);
        calendarioFactura.setDate(Date.valueOf(anio + "-" + (mes + 1) + "-" + diaHoy));
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
        this.facturaNueva.addActionListener(lis);
        this.realizarVenta.addActionListener(lis);
        this.borrarArticulosSeleccionados.addActionListener(lis);
        this.abonaSi.addActionListener(lis);
        this.agregarInexistente.addActionListener(lis);
    }



    public JTextField getBusquedaNombre() {
        return busquedaNombre;
    }
 
     public void paraVerVenta(boolean si){
         tablaArticulos.setEnabled(!si);
         tablaFactura.setEnabled(!si);
         tablaClientes.setEnabled(!si);
         busquedaNombre.setEnabled(!si);
         busquedaCodigoArticulo.setEnabled(!si);
         calendarioFactura.setEnabled(!si);
         borrarArticulosSeleccionados.setEnabled(!si);
         abonaSi.setEnabled(!si);
         realizarVenta.setEnabled(!si);
         agregarInexistente.setEnabled(!si);
     }

    public JButton getAgregarInexistente() {
        return agregarInexistente;
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
     * Retorno la tabla Clientes con tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return DefaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaclientesDefault() {
        return tablaArticulosDefault;
    }

    /**
     * Retorno la tabla factura con tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return DefaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaFacturaDefault() {
        return tablaFacturaDefault;
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
     * Retorno la tabla clientescon tipo TableModelDefault para pdoer realizar
     * inserciones y eliminaciones de filas más facilmente
     *
     * @param
     * @return DefaultTableModel
     * @exception
     */
    public DefaultTableModel getTablaClientesDefault() {
        return tablaClientesDefault;
    }

    /**
     * Retorno boton borrarArticulosSeleccionados para borrar los articulos
     * seleccionados en la tabla factura
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getBorrarArticulosSeleccionados() {
        return borrarArticulosSeleccionados;
    }


    /**
     * Retorno el campo del cliente de la factura
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getClienteFactura() {
        return clienteFactura;
    }

    /**
     * Retorno boton facturaNueva en donde limpia los campos de la factura para
     * iniciar una nueva venta
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getFacturaNueva() {
        return facturaNueva;
    }

    /**
     * Retorno la tabla clientes con tipo JTable
     *
     * @param
     * @return JTable
     * @exception
     */
    public JTable getTablaClientes() {
        return tablaClientes;
    }

    /**
     * Retorno boton realizar venta para confirmar la venta, se debe guardar los
     * datos en la base de datos
     *
     * @param
     * @return JButton
     * @exception
     */
    public JButton getRealizarVenta() {
        return realizarVenta;
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
     * Retorno tabla factura con tipo JTable
     *
     * @param
     * @return JTable
     * @exception
     */
    public JTable getTablaFactura() {
        return tablaFactura;
    }

    /**
     * Retorno el campo totalFactura que contiene el resultado final del a
     * factura
     *
     * @param
     * @return JTextField
     * @exception
     */
    public JTextField getTotalFactura() {
        return totalFactura;
    }

    /**
     * Retorno el calendario que contiene la fecha de la factura
     *
     * @param
     * @return JDateChooser
     * @exception
     */
    public JDateChooser getCalendarioFactura() {
        return calendarioFactura;
    }

    public void limpiarVentana() {
        tablaArticulos.clearSelection();
        tablaClientes.clearSelection();
        tablaFactura.clearSelection();
        while (tablaFactura.getRowCount() > 0) {
            tablaFacturaDefault.removeRow(0);
        }
        Calendar miCalendario = Calendar.getInstance();
        java.util.Date eldia = miCalendario.getTime();
        int diaHoy = miCalendario.get(Calendar.DAY_OF_MONTH);
        int mes = miCalendario.get(Calendar.MONTH);
        int anio = miCalendario.get(Calendar.YEAR);
        calendarioFactura.setDate(Date.valueOf(anio + "-" + (mes + 1) + "-" + diaHoy));
        clienteFactura.setText("");
        totalFactura.setText("");

    }

    public JTextField getCalenFacturaText() {
        return ((JTextField) calendarioFactura.getDateEditor().getUiComponent());
    }

   
    public JCheckBox getAbonaSi() {
        return abonaSi;
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
        panelClientes = new javax.swing.JPanel();
        busquedaNombre = new javax.swing.JTextField();
        labelApellido = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        panelFactura = new javax.swing.JPanel();
        labelCliente = new javax.swing.JLabel();
        clienteFactura = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaFactura = new javax.swing.JTable();
        labelTotal = new javax.swing.JLabel();
        totalFactura = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        borrarArticulosSeleccionados = new javax.swing.JButton();
        calendarioFactura = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        abonaSi = new javax.swing.JCheckBox();
        agregarInexistente = new javax.swing.JButton();
        panelControlFactura = new javax.swing.JPanel();
        facturaNueva = new javax.swing.JButton();
        realizarVenta = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registro Venta");
        setPreferredSize(new java.awt.Dimension(838, 440));

        jPanel1.setPreferredSize(new java.awt.Dimension(825, 448));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(822, 448));

        fondoImagen.setPreferredSize(new java.awt.Dimension(820, 400));

        panelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTitulo.setPreferredSize(new java.awt.Dimension(329, 49));

        titulo.setFont(new java.awt.Font("Century Schoolbook L", 3, 24)); // NOI18N
        titulo.setText("VENTA");
        panelTitulo.add(titulo);

        panelClientesAarticulos.setBorder(null);

        panelArticulos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Artículos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        tablaArticulos.setAutoCreateRowSorter(true);
        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Codigo", "Marca", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
                .addComponent(busquedaCodigoArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelArticulosLayout.setVerticalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArticulosLayout.createSequentialGroup()
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBusquedaCodigo)
                    .addComponent(busquedaCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
        );

        panelClientes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        busquedaNombre.setToolTipText("Filtrar busqueda por nombre");

        labelApellido.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelApellido.setText("Nombre");

        tablaClientes.setAutoCreateRowSorter(true);
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tablaClientes);

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addComponent(labelApellido)
                .addGap(17, 17, 17)
                .addComponent(busquedaNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelApellido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelClientesAarticulosLayout = new javax.swing.GroupLayout(panelClientesAarticulos);
        panelClientesAarticulos.setLayout(panelClientesAarticulosLayout);
        panelClientesAarticulosLayout.setHorizontalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesAarticulosLayout.createSequentialGroup()
                .addGroup(panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        panelClientesAarticulosLayout.setVerticalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClientesAarticulosLayout.createSequentialGroup()
                .addComponent(panelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelFactura.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        labelCliente.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelCliente.setText("Cliente");

        clienteFactura.setEditable(false);

        tablaFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cantidad", "Articulo","Descripcion", "Precio","Stock" ,"Importe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.math.BigDecimal.class, java.lang.String.class, java.lang.String.class,java.math.BigDecimal.class,java.math.BigDecimal.class, java.math.BigDecimal.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tablaFactura);

        labelTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelTotal.setText("Total");

        totalFactura.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");

        borrarArticulosSeleccionados.setText("Borrar articulo");
        borrarArticulosSeleccionados.setToolTipText("Borrar articulos seleccionados en la factura");
        borrarArticulosSeleccionados.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        calendarioFactura.setDateFormatString("yyyy-MM-dd");

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel4.setText("Abona");

        abonaSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abonaSiActionPerformed(evt);
            }
        });

        agregarInexistente.setText("Agregar manualmente");

        javax.swing.GroupLayout panelFacturaLayout = new javax.swing.GroupLayout(panelFactura);
        panelFactura.setLayout(panelFacturaLayout);
        panelFacturaLayout.setHorizontalGroup(
            panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelFacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clienteFactura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(calendarioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFacturaLayout.createSequentialGroup()
                .addComponent(borrarArticulosSeleccionados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agregarInexistente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abonaSi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTotal)
                .addGap(0, 0, 0)
                .addComponent(totalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelFacturaLayout.setVerticalGroup(
            panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturaLayout.createSequentialGroup()
                .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCliente)
                        .addComponent(clienteFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(calendarioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(borrarArticulosSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(agregarInexistente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFacturaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(abonaSi))
                            .addGroup(panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(totalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelTotal))))))
        );

        panelControlFactura.setLayout(new java.awt.GridLayout(1, 0));

        facturaNueva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/agregar.png"))); // NOI18N
        facturaNueva.setToolTipText("Realizar una nueva venta");
        panelControlFactura.add(facturaNueva);

        realizarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/vender.png"))); // NOI18N
        realizarVenta.setToolTipText("Solo registrar la venta");
        panelControlFactura.add(realizarVenta);

        javax.swing.GroupLayout fondoImagenLayout = new javax.swing.GroupLayout(fondoImagen);
        fondoImagen.setLayout(fondoImagenLayout);
        fondoImagenLayout.setHorizontalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addComponent(panelClientesAarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelControlFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void abonaSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abonaSiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_abonaSiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox abonaSi;
    private javax.swing.JButton agregarInexistente;
    private javax.swing.JButton borrarArticulosSeleccionados;
    private javax.swing.JTextField busquedaCodigoArticulo;
    private javax.swing.JTextField busquedaNombre;
    private com.toedter.calendar.JDateChooser calendarioFactura;
    private javax.swing.JTextField clienteFactura;
    private javax.swing.JButton facturaNueva;
    private javax.swing.JPanel fondoImagen;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel labelApellido;
    private javax.swing.JLabel labelBusquedaCodigo;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelClientesAarticulos;
    private javax.swing.JPanel panelControlFactura;
    private javax.swing.JPanel panelFactura;
    private org.edisoncor.gui.panel.PanelImage panelTitulo;
    private javax.swing.JButton realizarVenta;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaFactura;
    private javax.swing.JLabel titulo;
    private javax.swing.JTextField totalFactura;
    // End of variables declaration//GEN-END:variables
}
