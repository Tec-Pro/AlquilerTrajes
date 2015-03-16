/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import abm.ABMReserva;
import busqueda.Busqueda;
import busqueda.BusquedaArticulo;
import interfaz.RemitoGui;
import interfaz.ReservaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Ambo;
import modelos.Articulo;
import modelos.Cliente;
import modelos.Remito;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class ControladorReserva implements ActionListener {

    private final ABMReserva abmReserva; //abm de una reserva
    private final ReservaGui reservaGui; // gui de una reserva
    private Reserva reserva; // modelo de reserva
    private final Busqueda busqueda; //busquedas de clientes
    private final boolean isNuevaReserva; //si la reserva es nueva, o es una que se modificara
    private String fechaReserva; //fecha en que se realiza la reserva
    private String fechaEntregaReserva; //fecha en que se debe entregar la reserva
    private Integer idCliente; //ID del cliente que realizo la reserva
    private final BusquedaArticulo busquedaArticulo;//busqueda de articulos
    private List<Ambo> listaAmbos; //lista de Ambos de una Reserva a modificar
    private List<Articulo> listaArticulos; //lista de Articulos de una Reserva a modificar
    private RemitoGui remitoGui;
    private ControladorRemito controladorRemito;

    public ControladorReserva(ReservaGui reservaGui, RemitoGui remGui, Reserva r) throws SQLException {
        this.reservaGui = reservaGui;
        this.remitoGui = remGui;
        this.busqueda = new Busqueda();
        this.busquedaArticulo = new BusquedaArticulo();
        this.reservaGui.setActionListener(this);
        this.listaAmbos = null;
        this.listaArticulos = null;
        // si r es distinto de null, tenemos una reserva a modificar
        if (r != null) {
            this.isNuevaReserva = false; //la reserva no es nueva
            this.reserva = r; // le asigno a la reserva, que pasada por parametro para modificar
            this.abmReserva = new ABMReserva();
            this.idCliente = (Integer) reserva.get("cliente_id");
            cargarReserva(this.reserva); //cargo la reserva en la gui
            // sino creamos una nueva reserva
        } else {
            this.isNuevaReserva = true; // la reserva es nueva
            this.fechaEntregaReserva = null;
            this.fechaReserva = null;
            this.idCliente = null;
            this.reserva = new Reserva(); // creo un modelo nuevo de reserva
            this.abmReserva = new ABMReserva();
        }
        //escucho en el JText lo que se va ingresando para buscar un cliente
        this.reservaGui.getBusquedaCliente().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                List<Cliente> listaClientes;
                try {
                    listaClientes = busquedaClientes(evt); //busco los clientes
                    if (listaClientes != null) { //si hay clientes los cargo en la gui
                        actualizarTablaClientes(listaClientes);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        //escucho en el JText lo que se va ingresando para buscar un articulo
        this.reservaGui.getBusquedaCodigoArticulo().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    List<Articulo> listaArticulos;
                    List<Ambo> listaAmbos;
                    listaArticulos = busquedaArticulos(evt);
                    listaAmbos = busquedaAmbos(evt);
                    if (listaArticulos != null || listaAmbos != null) { //si hay articulos o ambos los cargo en la gui
                        actualizarTablaArticulos(listaArticulos, listaAmbos);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

        //reviso si se clickea alguna fila de la tabla clientes
        this.reservaGui.getTablaClienteReserva().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClickedClientes(evt); //si se clickea alguna fila, saco el id del cliente seleccionado
            }
        });
        //reviso si se clickea alguna fila de la tabla de busqueda de articulos
        this.reservaGui.getTablaBusquedaArticulosReserva().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClickedBusquedaArticulos(evt); //si se clickea alguna fila, saco el id del cliente seleccionado
            }
        });
        //reviso si se clickea alguna fila de la tabla de articulos para la reserva (Doble click sobre el articulo lo elimina de la tabla)
        this.reservaGui.getTablaArticulosReserva().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    eliminarFilaArticulosReserva();
                }
            }
        });
    }

    /*Saca el id del cliente seleccionado en la tabla de busqueda de clientes
     * y setea el JText de busqueda con el cliente seleccionado
     */
    private void tablaMouseClickedClientes(MouseEvent evt) {
        int selectedRow = reservaGui.getTablaClienteReserva().getSelectedRow();
        DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaClienteReserva().getModel());
        reservaGui.getBusquedaCliente().setText(modelo.getValueAt(selectedRow, 0) + " - "
                + modelo.getValueAt(selectedRow, 1) + "  " + modelo.getValueAt(selectedRow, 2));
        idCliente = (Integer) modelo.getValueAt(selectedRow, 0);
    }

    /* Inserto las filas seleccionadas en la tabla Busqueda de Articulos en la 
     * tabla Articulos reservas.
     */
    private void tablaMouseClickedBusquedaArticulos(MouseEvent evt) {
        /*Por defecto se utiliza la seleccion de filas individuales en la tabla*/
        int selectedRow = reservaGui.getTablaBusquedaArticulosReserva().getSelectedRow();
        DefaultTableModel modeloBusqueda = ((DefaultTableModel) reservaGui.getTablaBusquedaArticulosReserva().getModel());
        Object[] row;
        DefaultTableModel modeloReserva = ((DefaultTableModel) reservaGui.getTablaArticulosReserva().getModel());
        row = new Object[6];
        row[0] = (modeloBusqueda.getValueAt(selectedRow, 0));
        row[1] = (modeloBusqueda.getValueAt(selectedRow, 1));
        row[2] = (modeloBusqueda.getValueAt(selectedRow, 2));
        row[3] = (modeloBusqueda.getValueAt(selectedRow, 3));
        row[4] = (modeloBusqueda.getValueAt(selectedRow, 4));
        row[5] = (modeloBusqueda.getValueAt(selectedRow, 6));
        modeloReserva.addRow(row);
    }

    //Busco los clientes a partir de los datos ingresados en el JText de busqueda
    private List<Cliente> busquedaClientes(KeyEvent evt) throws SQLException {
        String textBusquedaCliente = reservaGui.getBusquedaCliente().getText();
        List<Cliente> listClientes;
        //Si la busqueda empieza con un numero, busco al/los clientes por el id y dni simultaneamente
        if (textBusquedaCliente.startsWith("0") || textBusquedaCliente.startsWith("1") || textBusquedaCliente.startsWith("2") || textBusquedaCliente.startsWith("3") || textBusquedaCliente.startsWith("4")
                || textBusquedaCliente.startsWith("5") || textBusquedaCliente.startsWith("6") || textBusquedaCliente.startsWith("7") || textBusquedaCliente.startsWith("8")
                || textBusquedaCliente.startsWith("9")) {
            listClientes = busqueda.buscarClientesPorIDyDni(Integer.parseInt(textBusquedaCliente));

            //Sino, busco los clientes por su nombre
        } else {
            listClientes = busqueda.buscarCliente(textBusquedaCliente);
        }
        return listClientes;
    }

    //Carga la lista de clientes encontrados en la tabla de busqueda de clientes
    private void actualizarTablaClientes(List<Cliente> lista) throws SQLException {
        DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaClienteReserva().getModel());
        modelo.setRowCount(0);
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Iterator<Cliente> itr = lista.iterator();
        Cliente c;
        Object[] o = new Object[3];
        while (itr.hasNext()) {
            c = itr.next();
            o[0] = (c.getId());
            o[1] = (c.getString("nombre"));
            o[2] = (c.getString("dni"));
            modelo.addRow(o);

        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    //Busco los articulos a partir de los datos ingresados en el JText de busqueda
    private List<Articulo> busquedaArticulos(KeyEvent evt) throws SQLException {
        String textBusquedaArticulo = reservaGui.getBusquedaCodigoArticulo().getText();
        List<Articulo> listArticulo = busquedaArticulo.buscarArticulos(textBusquedaArticulo);
        return listArticulo;
    }

    //Busco los ambos a partir de los datos ingresados en el JText de busqueda
    private List<Ambo> busquedaAmbos(KeyEvent evt) throws SQLException {
        String textBusquedaAmbo = reservaGui.getBusquedaCodigoArticulo().getText();
        List<Ambo> listAmbo = busquedaArticulo.buscarAmbos(textBusquedaAmbo);
        return listAmbo;
    }

    //Carga la lista de articulos y ambos encontrados en la tabla busqueda de articulos
    private void actualizarTablaArticulos(List<Articulo> listaArticulos, List<Ambo> listaAmbos) throws SQLException {
        DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaBusquedaArticulosReserva().getModel());
        modelo.setRowCount(0);
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Iterator<Articulo> itrArticulo = listaArticulos.iterator();
        Iterator<Ambo> itrAmbo = listaAmbos.iterator();
        Articulo ar;
        Ambo am;
        Object[] o = new Object[7];
        while (itrArticulo.hasNext()) {
            ar = itrArticulo.next();
            o[0] = (ar.getId());
            o[1] = (ar.getString("modelo"));
            o[2] = (ar.getString("marca"));
            o[3] = (ar.getString("tipo"));
            o[4] = (ar.getString("talle"));
            o[5] = (ar.getString("descripcion"));
            o[6] = (ar.getString("precio_alquiler"));
            modelo.addRow(o);

        }
        while (itrAmbo.hasNext()) {
            am = itrAmbo.next();
            o[0] = (am.getId());
            o[1] = (am.get("nombre"));
            o[2] = (am.getString("marca"));
            o[3] = ("ambo");
            o[4] = (am.getString("talle"));
            o[5] = (am.getString("descripcion"));
            o[6] = (am.getString("precio_alquiler"));
            modelo.addRow(o);

        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    /*
     * Elimino el articulo de la tabla de productos de una reserva, si se hace doble click sobre el
     */
    private void eliminarFilaArticulosReserva() {
        int selectedRow = reservaGui.getTablaArticulosReserva().getSelectedRow();
        DefaultTableModel modeloReserva = ((DefaultTableModel) reservaGui.getTablaArticulosReserva().getModel());
        modeloReserva.removeRow(selectedRow);
    }

    //Carga una reserva en la gui (Reserva guardada previamente a modificar)
    private void cargarReserva(Reserva r) throws SQLException {
        Cliente c = busqueda.buscarCliente(r.get("cliente_id"));
        reservaGui.setBusquedaCliente(c.getId() + " - " + c.getString("nombre") + " " + c.getString("dni"));
        Date dateFR = r.getDate("fecha_reserva");
        Date dateFER = r.getDate("fecha_entrega_reserva");
        reservaGui.setFechaReserva(dateFR);
        reservaGui.setFechaEntregaReserva(dateFER);
        //Saco todos los articulos y ambos de la reserva a cargar
        listaAmbos = r.getAll(Ambo.class);
        listaArticulos = r.getAll(Articulo.class);

        DefaultTableModel modeloArticulos = ((DefaultTableModel) reservaGui.getTablaArticulosReserva().getModel());
        modeloArticulos.setRowCount(0);
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Object[] o = new Object[6];
        if (listaArticulos != null) {
            Articulo ar;
            Iterator<Articulo> itrArticulo = listaArticulos.iterator();
            while (itrArticulo.hasNext()) {
                ar = itrArticulo.next();
                o[0] = (ar.getId());
                o[1] = (ar.getString("modelo"));
                o[2] = (ar.getString("marca"));
                o[3] = (ar.getString("tipo"));
                o[4] = (ar.getString("talle"));
                o[5] = (ar.getString("precio_alquiler"));
                modeloArticulos.addRow(o);

            }
        }
        if (listaAmbos != null) {
            Ambo am;
            Iterator<Ambo> itrAmbo = listaAmbos.iterator();
            while (itrAmbo.hasNext()) {
                am = itrAmbo.next();
                o[0] = (am.getId());
                o[1] = (am.get("nombre"));
                o[2] = (am.getString("marca"));
                o[3] = ("ambo");
                o[4] = (am.getString("talle"));
                o[5] = (am.getString("precio_alquiler"));
                modeloArticulos.addRow(o);
            }
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*Si presiono el boton de agregar una reserva, y ademas la reserva es nueva,
         * la doy de alta en la base de datos.
         */
        if (e.getSource().equals(reservaGui.getConfirmarReserva()) && isNuevaReserva) {
            fechaReserva = reservaGui.getFechaReserva();
            fechaEntregaReserva = reservaGui.getFechaEntregaReserva();
            DefaultTableModel modeloArticulos = (DefaultTableModel) reservaGui.getTablaArticulosReserva().getModel();
            if (idCliente != null && fechaEntregaReserva != null && fechaReserva != null && modeloArticulos.getRowCount() != 0) {
                reserva.set("fecha_reserva", fechaReserva);
                reserva.set("fecha_entrega_reserva", fechaEntregaReserva);
                reserva.set("cliente_id", idCliente);
                try {
                    if (abmReserva.alta(reserva)) {//si la reserva pudo ser creada, procedo a guardar la demas informacion
                        reserva.set("id", abmReserva.getUltimoId());
                        //Saco los articulos de la reserva, de la tabla correspondiente y los guardo en la BD
                        BaseDatos.abrirBase();
                        BaseDatos.openTransaction();
                        Articulo artAux = null;
                        Ambo amboAux = null;
                        for (int i = 0; i < modeloArticulos.getRowCount(); i++) {
                            //si es un ambo lo busco por su id en la base
                            if (modeloArticulos.getValueAt(i, 3).equals("ambo")) {
                                amboAux = Ambo.findById(modeloArticulos.getValueAt(i, 0));
                                this.reserva.add(amboAux);
                                //si no es un ambo, es un articulo
                            } else {
                                artAux = Articulo.findById(modeloArticulos.getValueAt(i, 0));
                                this.reserva.add(artAux);

                            }
                        }
                        BaseDatos.commitTransaction();
                        BaseDatos.cerrarBase();

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(reservaGui, "La Reserva ha sido realizada con éxito!.");
                this.reservaGui.limpiarComponentes();
            } else {
                JOptionPane.showMessageDialog(reservaGui, "La informacion es insuficiente o erronea, por favor complete todos los campos.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        /*Si presiono el boton de agregar una reserva, y no es una reserva nueva(reserva a modifcar),
         * la modifico en la base de datos.
         */
        if (e.getSource().equals(reservaGui.getConfirmarReserva()) && !isNuevaReserva) {
            fechaReserva = reservaGui.getFechaReserva();
            fechaEntregaReserva = reservaGui.getFechaEntregaReserva();
            DefaultTableModel modeloArticulos = (DefaultTableModel) reservaGui.getTablaArticulosReserva().getModel();
            if (idCliente != null && fechaEntregaReserva != null && fechaReserva != null && modeloArticulos.getRowCount() != 0) {
                this.reserva.set("fecha_reserva", fechaReserva);
                this.reserva.set("fecha_entrega_reserva", fechaEntregaReserva);
                this.reserva.set("cliente_id", idCliente);
                try {
                    if (abmReserva.modificar(reserva)) {
                        try {
                            BaseDatos.abrirBase();

                            BaseDatos.openTransaction();
                            //Elimino los Ambos y articulos de la reserva, en la base de datos
                            if (listaAmbos != null || listaArticulos != null) {
                                if (listaArticulos != null) {
                                    Articulo ar;
                                    Iterator<Articulo> itrArticulo = listaArticulos.iterator();
                                    while (itrArticulo.hasNext()) {
                                        ar = itrArticulo.next();
                                        this.reserva.remove(ar);

                                    }
                                }
                                if (listaAmbos != null) {
                                    Ambo am;
                                    Iterator<Ambo> itrAmbo = listaAmbos.iterator();
                                    while (itrAmbo.hasNext()) {
                                        am = itrAmbo.next();
                                        this.reserva.remove(am);
                                    }
                                }
                                //Saco los articulos nuevos del remito, de la tabla correspondiente y los guardo en la BD
                                Articulo artAux = null;
                                Ambo amboAux = null;
                                for (int i = 0; i < modeloArticulos.getRowCount(); i++) {
                                    //si es un ambo lo busco por su id en la base
                                    if (modeloArticulos.getValueAt(i, 3).equals("ambo")) {
                                        amboAux = Ambo.findById(modeloArticulos.getValueAt(i, 0));
                                        this.reserva.add(amboAux);
                                        //si no es un ambo, es un articulo
                                    } else {
                                        artAux = Articulo.findById(modeloArticulos.getValueAt(i, 0));
                                        this.reserva.add(artAux);

                                    }
                                }
                                BaseDatos.commitTransaction();
                                BaseDatos.cerrarBase();
                            } else {
                                JOptionPane.showMessageDialog(reservaGui, "No se encontraron Artículos ni Ambos en esta Reserva.", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                            JOptionPane.showMessageDialog(reservaGui, "La Reserva ha sido modificada con éxito!.");
                            this.reservaGui.limpiarComponentes();
                        } catch (SQLException ex) {
                            Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(reservaGui, "La Reserva no pudo ser modificada.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(reservaGui, "La informacion es insuficiente o erronea, por favor complete todos los campos.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        /*
        * Si presiono el boton de crear Remito en la gui de Reservas
        * este abrira un nuevo Remito con los datos obtenidos en la reserva
        */
        if (e.getSource().equals(reservaGui.getBttnCrearRemito())) {
            fechaReserva = reservaGui.getFechaReserva();
            fechaEntregaReserva = reservaGui.getFechaEntregaReserva();
            DefaultTableModel modeloArticulos = (DefaultTableModel) reservaGui.getTablaArticulosReserva().getModel();
            if (idCliente != null && fechaEntregaReserva != null && fechaReserva != null && modeloArticulos.getRowCount() != 0) {
                try {
                //limpio la remitoGui
                this.remitoGui.limpiarComponentes();
                //creo el controlador de la RemitoGui
                this.controladorRemito = new ControladorRemito(remitoGui, null);
                //Cargo los articulos del Remito en la gui
                this.remitoGui.getTablaArticulosRemito().setModel(modeloArticulos);
                //Cargo el total del remito en la gui
                int cantFilas = modeloArticulos.getRowCount();
                Double totalRemito = 0.0;
                for (int i = 0;i<cantFilas;i++){
                    //saco el precio de cada articulo en la tabla de Articulos Reserva, que se encuentra en la fila "i" columna "5"
                    totalRemito += Double.parseDouble((String)reservaGui.getTablaArticulosReserva().getValueAt(i, 5));
                }
                this.remitoGui.setjTextTotalRemito(totalRemito);
                //Cargo el cliente en la gui
                BaseDatos.abrirBase();
                BaseDatos.openTransaction();
                Cliente c = Cliente.findById(idCliente);
                DefaultTableModel modeloClientes = (DefaultTableModel) remitoGui.getTablaClienteRemito().getModel();
                Object[] o = new Object[3];
                o[0] = (c.getId());
                o[1] = (c.getString("nombre"));
                o[2] = (c.getString("dni"));
                modeloClientes.addRow(o);
                BaseDatos.commitTransaction();
                BaseDatos.cerrarBase();
                //marco como seleccionada la primera fila, que es la que contiene el cliente que agrege a la tabla
                (this.remitoGui.getTablaClienteRemito().getSelectionModel()).addSelectionInterval(0, 0);
                //seteo el jtext de busqueda de clientes de RemitoGui
                this.remitoGui.getBusquedaClienteRemito().setText(modeloClientes.getValueAt(0, 0) + " - "
                + modeloClientes.getValueAt(0, 1) + "  " + modeloClientes.getValueAt(0, 2));
                //seteo la fecha del remito como la fecha de entrega de la reserva
                this.remitoGui.setjDateFechaRemito(this.reservaGui.getFechaEntregaReservaDate());
                //Seteo RemitoGui visible y en pantalla completa dentro de su contenedor
                this.remitoGui.setVisible(true);
                this.remitoGui.setMaximum(true);
                } catch (SQLException | PropertyVetoException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(reservaGui, "La informacion es insuficiente o erronea, por favor complete todos los campos.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        /* Si presiona el boton Cancelar, se cierra la ventana
        */
        if (e.getSource().equals(reservaGui.getBttnCancelar())) {
            this.reservaGui.hide();
        }
        /* Si presiona el boton Eliminar Reserva y esta todavia no fue creada
        * en la base de datos, solamente cierro la ventana.
        */
        if (e.getSource().equals(reservaGui.getBttnEliminar()) && isNuevaReserva) {
            this.reservaGui.hide();
        }
        /* Si presiona el boton Eliminar Reserva y esta habia sido creada
        * previamente, la reserva se borra de la base de datos.
        */
        if (e.getSource().equals(reservaGui.getBttnEliminar()) && !isNuevaReserva) {
            try {
                this.abmReserva.baja(this.reserva);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.reservaGui.hide();
        }
        
    }
}
