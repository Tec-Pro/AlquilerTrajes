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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
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
    private boolean isNuevaReserva; //si la reserva es nueva, o es una que se modificara
    private String fechaReserva; //fecha en que se realiza la reserva
    private String fechaEntregaReserva; //fecha en que se debe entregar la reserva
    private Integer idCliente; //ID del cliente que realizo la reserva
    private final BusquedaArticulo busquedaArticulo;//busqueda de articulos
    private List<Ambo> listaAmbos; //lista de Ambos de una Reserva a modificar
    private List<Articulo> listaArticulos; //lista de Articulos de una Reserva a modificar
    private final RemitoGui remitoGui;

    public ControladorReserva(final ReservaGui reservaGui, RemitoGui remGui) throws SQLException {
        this.reservaGui = reservaGui;
        this.remitoGui = remGui;
        this.busqueda = new Busqueda();
        this.busquedaArticulo = new BusquedaArticulo();
        this.reservaGui.setActionListener(this);
        this.listaAmbos = null;
        this.listaArticulos = null;
        this.abmReserva = new ABMReserva();
        //por default asumo que se esta creando una nueva reserva
        this.setReserva(null);
        //si cambio la fecha de entrega de la reserva, limpio la tabla de busqueda de articulos para realizar una busqueda nueva
        this.reservaGui.getFechaEntregaReservaDateChooser().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                ((DefaultTableModel) reservaGui.getTablaBusquedaArticulosReserva().getModel()).setRowCount(0);
            }
        });
        //escucho en el JText lo que se va ingresando para buscar un cliente
        this.reservaGui.getBusquedaCliente().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    actualizarTablaClientes();
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
                    actualizarTablaBusquedaArticulos();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        //reviso si se clickea alguna fila de la tabla clientes
        this.reservaGui.getTablaClienteReserva().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClickedClientes(); //si se clickea alguna fila, saco el id del cliente seleccionado
            }
        });
        //reviso si se clickea alguna fila de la tabla de busqueda de articulos
        this.reservaGui.getTablaBusquedaArticulosReserva().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClickedBusquedaArticulos();//cargo la fila clickeada en la tabla de Articulos de la Reserva
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

    //Seteo una reserva en la gui
    public void setReserva(Reserva r) throws SQLException {
        // si r es distinto de null, tenemos una reserva a modificar
        if (r != null) {
            this.isNuevaReserva = false; //la reserva no es nueva
            this.reserva = r; // le asigno a la reserva, que pasada por parametro para modificar
            this.idCliente = (Integer) reserva.get("cliente_id");
            cargarReserva(this.reserva); //cargo la reserva en la gui
        } else {// sino creamos una nueva reserva
            this.isNuevaReserva = true; // la reserva es nueva
            this.fechaEntregaReserva = null;
            this.fechaReserva = null;
            this.idCliente = null;
            this.reserva = new Reserva(); // creo un modelo nuevo de reserva

        }
    }

    /*Saca el id del cliente seleccionado en la tabla de busqueda de clientes
     * y setea el JText de busqueda con el cliente seleccionado
     */
    private void tablaMouseClickedClientes() {
        int selectedRow = reservaGui.getTablaClienteReserva().getSelectedRow();
        DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaClienteReserva().getModel());
        reservaGui.getBusquedaCliente().setText(modelo.getValueAt(selectedRow, 0) + " - "
                + modelo.getValueAt(selectedRow, 1) + "  " + modelo.getValueAt(selectedRow, 2));
        idCliente = (Integer) modelo.getValueAt(selectedRow, 0);
    }

    /* Inserto las filas seleccionadas en la tabla Busqueda de Articulos en la 
     * tabla Articulos reservas.
     */
    private void tablaMouseClickedBusquedaArticulos() {
        /*Por defecto se utiliza la seleccion de filas individuales en la tabla*/
        int selectedRow = reservaGui.getTablaBusquedaArticulosReserva().getSelectedRow();
        Object[] row;
        boolean existRow = false;
        DefaultTableModel modeloBusqueda = ((DefaultTableModel) reservaGui.getTablaBusquedaArticulosReserva().getModel());
        DefaultTableModel modeloReserva = ((DefaultTableModel) reservaGui.getTablaArticulosReserva().getModel());
        int i = 0, rowCount = modeloReserva.getRowCount();
        while (i < rowCount && !existRow) {
            //Si el articulo(el id y tipo de art) ya estan en la tabla de Reserva
            if (modeloBusqueda.getValueAt(selectedRow, 0).equals(modeloReserva.getValueAt(i, 0))
                    && modeloBusqueda.getValueAt(selectedRow, 3).equals(modeloReserva.getValueAt(i, 3))) {
                existRow = true;
            }
            i++;
        }
        if (!existRow) {
            row = new Object[6];
            row[0] = (modeloBusqueda.getValueAt(selectedRow, 0));
            row[1] = (modeloBusqueda.getValueAt(selectedRow, 1));
            row[2] = (modeloBusqueda.getValueAt(selectedRow, 2));
            row[3] = (modeloBusqueda.getValueAt(selectedRow, 3));
            row[4] = (modeloBusqueda.getValueAt(selectedRow, 4));
            row[5] = (modeloBusqueda.getValueAt(selectedRow, 6));
            modeloReserva.addRow(row);
        }
    }

    //Busco los clientes a partir de los datos ingresados en el JText de busqueda
    private List<Cliente> busquedaClientes() throws SQLException {
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
    private void actualizarTablaClientes() throws SQLException {
        List<Cliente> listaClientes = busquedaClientes(); //busco los clientes
        if (listaClientes != null) { //si hay clientes los cargo en la gui
            DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaClienteReserva().getModel());
            modelo.setRowCount(0);
            BaseDatos.abrirBase();
            BaseDatos.openTransaction();
            Iterator<Cliente> itr = listaClientes.iterator();
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
    }

    //Busco los articulos a partir de los datos ingresados en el JText de busqueda
    private List<Articulo> busquedaArticulos() throws SQLException {
        /*
         * "listaArticulo", lista de articulos en base de datos
         * "listaArticulosRemito", lista de articulos de remitos en una fecha determinada
         * "listaArticulosReserva" lista de articulos de reservas en una fecha determinada
         */
        List<Articulo> listaArticulo = new LinkedList<>(), listaArticulosRemito = new LinkedList<>(), listaArticulosReserva = new LinkedList<>();
        //lista de ambos de remitos y reservas en una fecha determinada
        List<Ambo> listaAmbo = new LinkedList<>();
        //lista de pares de Id de articulo y cantidad disponible
        List<Object[]> listaDePares = new LinkedList<>();
        String textBusquedaArticulo = reservaGui.getBusquedaCodigoArticulo().getText();
        String fechaDeBusqueda = reservaGui.getFechaEntregaReserva();
        //saco todos los articulos de la base en base al texto de busqueda
        listaArticulo = busquedaArticulo.buscarArticulos(textBusquedaArticulo);

        //Si tenemos una fecha en la cual revisar que articulos disponibles hay
        if (fechaDeBusqueda != null) {
            BaseDatos.abrirBase();
            BaseDatos.openTransaction();
            //saco los articulos de los remitos en una fecha determinada
            List<Remito> listaRemitos = Remito.where("fecha_de_remito = ?", fechaDeBusqueda);
            if (listaRemitos != null) {
                Iterator<Remito> itrRemito = listaRemitos.iterator();
                while (itrRemito.hasNext()) {
                    Remito rAux = itrRemito.next();
                    if (rAux.getAll(Articulo.class) != null) {
                        listaArticulosRemito.addAll(rAux.getAll(Articulo.class));
                    }
                    if (rAux.getAll(Ambo.class) != null) {
                        listaAmbo.addAll(rAux.getAll(Ambo.class));
                    }
                }
            }
            //saco los articulos de las reservas en una fecha determinada
            List<Reserva> listaReservas = Reserva.where("fecha_entrega_reserva= ?", fechaDeBusqueda);
            if (listaReservas != null) {
                Iterator<Reserva> itrReserva = listaReservas.iterator();
                while (itrReserva.hasNext()) {
                    Reserva rAux = itrReserva.next();
                    if (rAux.getAll(Articulo.class) != null) {
                        listaArticulosReserva.addAll(rAux.getAll(Articulo.class));
                    }
                    if (rAux.getAll(Ambo.class) != null) {
                        listaAmbo.addAll(rAux.getAll(Ambo.class));
                    }
                }
            }
            /* Creo una lista de pares, con la totalidad de Articulos
             * Cada arreglo(par) de la lista(listaDePares) contiene 
             * {idArticulo,cantidadDisponible}
             */
            if (listaArticulo != null) {
                Iterator<Articulo> itrArticulos = listaArticulo.iterator();
                while (itrArticulos.hasNext()) {
                    Object[] par = new Object[2];
                    Articulo ar = itrArticulos.next();
                    par[0] = (ar.getId());
                    par[1] = (ar.getString("stock"));
                    listaDePares.add(par);
                }
            }
            /*
             * Resto la cantidad de articulos, de Remitos y Reservas en la fecha especificada,
             * al total de articulos encontrados en la base de datos (calculando asi la cant.
             * de articulos disponibles para alquilar)
             */
            Iterator<Articulo> itrArticuloRemitos = listaArticulosRemito.iterator();
            while (itrArticuloRemitos.hasNext()) {
                Articulo artAux = itrArticuloRemitos.next();
                Iterator<Object[]> itrObjAux = listaDePares.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] parAux = itrObjAux.next();
                    /* Comparo si el id de art son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (parAux[0].equals(artAux.getId())) {
                        parAux[1] = Integer.parseInt(parAux[1].toString()) - 1;
                    }
                }
            }

            Iterator<Articulo> itrArticuloReservas = listaArticulosReserva.iterator();
            while (itrArticuloReservas.hasNext()) {
                Articulo artAux = itrArticuloReservas.next();
                Iterator<Object[]> itrObjAux = listaDePares.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] parAux = itrObjAux.next();
                    /* Comparo si el id de art son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (parAux[0].equals(artAux.getId())) {
                        parAux[1] = Integer.parseInt(parAux[1].toString()) - 1;
                    }
                }
            }
            
            //si hay ambos alquilados, descuento los articulos de dicho ambo a la cantidad disp. de articulos
        //busco en todos los ambos para descontar la disponibilidad los articulos que fueron alquilados
        Iterator<Ambo> itrAmbo = listaAmbo.iterator();
        while (itrAmbo.hasNext()) {
            Ambo amboRemito = itrAmbo.next();
            //saco los articulos de un ambo
            List<Articulo> listaArticulosAmbo = amboRemito.getAll(Articulo.class);
            //resto en 1 los articulos de cada ambo a la cantidad disponible de articulos.
            Iterator<Articulo> itrArticulosAmbo = listaArticulosAmbo.iterator();
            while (itrArticulosAmbo.hasNext()) {
                Articulo artAux = itrArticulosAmbo.next();
                Iterator<Object[]> itrObjAux = listaDePares.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] parAux = itrObjAux.next();
                    /* Comparo si el id y el tipo del art son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (parAux[0].equals(artAux.getId())) {
                        parAux[1] = Integer.parseInt(parAux[1].toString()) - 1;
                    }
                }
            }

        }
            
            
            //lista de resultado con los articulos disponibles
            List<Articulo> listaResultado = new LinkedList<>();
            Iterator<Object[]> itrListaDePares = listaDePares.iterator();
            while (itrListaDePares.hasNext()) {
                Object[] objPar = itrListaDePares.next();
                /* Si la cantidad disponible es mayor a 1,
                 * agrego el articulo la lista de disponibles para alquilar */
                if (Integer.parseInt(objPar[1].toString()) > 0) {
                    Iterator<Articulo> itrListaArticulo = listaArticulo.iterator();
                    while (itrListaArticulo.hasNext()) {
                        Articulo artAux = itrListaArticulo.next();
                        if (artAux.getId().equals(Integer.parseInt(objPar[0].toString()))) {
                            listaResultado.add(artAux);
                        }
                    }
                }
            }
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return listaResultado;
        } else {
            // Si la fecha no fue ingresada, no hay articulos para buscar, retorno null
            return null;
        }

    }

    //Busco los ambos a partir de los datos ingresados en el JText de busqueda
    private List<Ambo> busquedaAmbos() throws SQLException {
        /*
         * "listaArticulo", lista de articulos en base de datos
         * "listaArticulosRemito", lista de articulos de remitos en una fecha determinada
         * "listaArticulosReserva" lista de articulos de reservas en una fecha determinada
         * "listaAmbo", lista de ambos en base de datos
         * "listaAmbosRemito", lista de ambos de remitos en una fecha determinada
         * "listaAmbosReserva" lista de ambos de reservas en una fecha determinada
         */
        List<Articulo> listaArticulo = new LinkedList<>(), listaArticulosRemito = new LinkedList<>(), listaArticulosReserva = new LinkedList<>();
        List<Ambo> listaAmbo = new LinkedList<>(), listaAmbosRemito = new LinkedList<>(), listaAmbosReserva = new LinkedList<>();
        List<Object[]> listaDeTriplas = new LinkedList<>(), listaDeTriplasAmbos = new LinkedList<>(), listaDeTriplasArticulos = new LinkedList<>();
        String fechaDeBusqueda = reservaGui.getFechaEntregaReserva();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        //saco todos los articulos de la base
        listaArticulo = Articulo.findAll();
        //saco todos los ambos de la base
        listaAmbo = Ambo.findAll();
        //saco los articulos y ambos de los remitos en una fecha determinada
        List<Remito> listaRemitos = Remito.where("fecha_de_remito = ?", fechaDeBusqueda);
        if (listaRemitos != null) {
            Iterator<Remito> itrRemito = listaRemitos.iterator();
            while (itrRemito.hasNext()) {
                Remito rAux = itrRemito.next();
                if (rAux.getAll(Articulo.class) != null) {
                    listaArticulosRemito.addAll(rAux.getAll(Articulo.class));
                }
                if (rAux.getAll(Ambo.class) != null) {
                    listaAmbosRemito.addAll(rAux.getAll(Ambo.class));
                }
            }
        }
        //saco los articulos y ambos de las reservas en una fecha determinada
        List<Reserva> listaReservas = Reserva.where("fecha_entrega_reserva= ?", fechaDeBusqueda);
        if (listaReservas != null) {
            Iterator<Reserva> itrReserva = listaReservas.iterator();
            while (itrReserva.hasNext()) {
                Reserva rAux = itrReserva.next();
                if (rAux.getAll(Articulo.class) != null) {
                    listaArticulosReserva.addAll(rAux.getAll(Articulo.class));
                }
                if (rAux.getAll(Ambo.class) != null) {
                    listaAmbosReserva.addAll(rAux.getAll(Ambo.class));
                }
            }
        }
            /* Creo una lista de triplas, con la totalidad de Ambos y Articulos
             * Cada arreglo(tripla) de la lista(listaDeFilas*) contiene 
             * {idArt,tipo,cantidadDisponible}
             */
            if (listaArticulo != null) {
            Iterator<Articulo> itrArticulos = listaArticulo.iterator();
            while (itrArticulos.hasNext()) {
                Object[] tripla = new Object[3];
                Articulo ar = itrArticulos.next();
                tripla[0] = (ar.getId());
                tripla[1] = (ar.getString("tipo"));
                tripla[2] = (ar.getString("stock"));
                listaDeTriplasArticulos.add(tripla);
            }
        }
        if (listaAmbo != null) {
            Iterator<Ambo> itrAmbos = listaAmbo.iterator();
            while (itrAmbos.hasNext()) {
                Object[] tripla = new Object[3];
                Ambo am = itrAmbos.next();
                tripla[0] = (am.getId());
                tripla[1] = ("ambo");
                tripla[2] = (am.getString("stock"));
                listaDeTriplasAmbos.add(tripla);
            }
        }
            
        /*
         * Resto la cantidad de articulos y ambos, de Remitos y Reservas en la fecha especificada,
         * al total de articulos y ambos encontrados en la base de datos (calculando asi la cant.
         * de articulos y ambos disponibles para alquilar)
         */
        Iterator<Articulo> itrArticuloRemitos = listaArticulosRemito.iterator();
        while (itrArticuloRemitos.hasNext()) {
            Articulo artAux = itrArticuloRemitos.next();
            Iterator<Object[]> itrObjAux = listaDeTriplasArticulos.iterator();
            while (itrObjAux.hasNext()) {
                Object[] filaAux = itrObjAux.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en 1 la cantidad disponible
                 * del mismo para alquilar
                 */
                if (filaAux[0].equals(artAux.getId()) && filaAux[1].equals(artAux.getString("tipo"))) {
                    filaAux[2] = Integer.parseInt(filaAux[2].toString()) - 1;
                }
            }
        }

        Iterator<Articulo> itrArticuloReservas = listaArticulosReserva.iterator();
        while (itrArticuloReservas.hasNext()) {
            Articulo artAux = itrArticuloReservas.next();
            Iterator<Object[]> itrObjAux = listaDeTriplasArticulos.iterator();
            while (itrObjAux.hasNext()) {
                Object[] filaAux = itrObjAux.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en 1 la cantidad disponible
                 * del mismo para alquilar
                 */
                if (filaAux[0].equals(artAux.getId()) && filaAux[1].equals(artAux.getString("tipo"))) {
                    filaAux[2] = Integer.parseInt(filaAux[2].toString()) - 1;
                }
            }
        }

        //si los articulos del ambo ya estan alquilados
        //busco en todos los ambos para descontar la disponibilidad del cual sus articulos fueron alquilados
        Iterator<Ambo> itrAmbo = listaAmbo.iterator();
        while (itrAmbo.hasNext()) {
            Ambo amboRemito = itrAmbo.next();
            //saco los articulos de un ambo
            List<Articulo> listaArticulosAmbo = amboRemito.getAll(Articulo.class);
            //me fijo si los articulos fueron alquilados independientemente del ambo
            int cantADescontar = 0; //cantidad de ambos disponibles a descontar
            Iterator<Articulo> itrArticulosAmbos = listaArticulosAmbo.iterator();
            /*comparo cada articulo de un ambo, con los articulos de listaDeFilasArticulos
             * y seteo la cantidad disponible del ambo con la del articulo que menor cantidad
             * disponible tenga.
             */
            while (itrArticulosAmbos.hasNext()) {
                Iterator<Object[]> itrObjAuxArticulo = listaDeTriplasArticulos.iterator();
                Articulo artAmbo = itrArticulosAmbos.next();
                while (itrObjAuxArticulo.hasNext()) {
                    Object[] filaAux = itrObjAuxArticulo.next();
                    /*si el id de articulo del ambo coincide con el de la listaDeFilasArticulos
                     * (lista que contiene la cantidad disponible de cada articulo), el stock
                     * del art es diferente a la cantidad disponible del mismo, y no es un ambo
                     * ya que estoy comparando solo articulos
                     */
                    Integer artAmboStock = Integer.parseInt(artAmbo.getString("stock"));
                    if (filaAux[0].equals(artAmbo.getId()) && !artAmboStock.equals(Integer.parseInt(filaAux[2].toString())) && !filaAux[1].equals("ambo")) {
                        /* Si la cantADescontar es menor a la cantidad de articulos alquilados
                         * (stock - artDisponibles)
                         */
                        if ((artAmboStock - Integer.parseInt(filaAux[2].toString())) > cantADescontar) {
                            //actualizo la cantidad a descontar de ambos disponibles
                            cantADescontar = (artAmboStock - Integer.parseInt(filaAux[2].toString()));
                        }
                    }
                }

            }
            Iterator<Object[]> itrObjAuxAmbo = listaDeTriplasAmbos.iterator();
            while (itrObjAuxAmbo.hasNext()) {
                Object[] filaAux = itrObjAuxAmbo.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en cantADescontar calculado
                 * previamente la cantidad disponible del mismo para
                 * alquilar
                 */
                if (filaAux[0].equals(amboRemito.getId()) && filaAux[1].equals("ambo")) {
                    filaAux[2] = Integer.parseInt(filaAux[2].toString()) - cantADescontar;
                }
            }
        }
        //si el ambo entero fue alquilado
        Iterator<Ambo> itrAmboRemitos = listaAmbosRemito.iterator();
            while (itrAmboRemitos.hasNext()) {
                Ambo amAux = itrAmboRemitos.next();
                Iterator<Object[]> itrObjAux = listaDeTriplasAmbos.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] parAux = itrObjAux.next();
                    /* Comparo si el id de ambo son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (parAux[0].equals(amAux.getId())) {
                        parAux[2] = Integer.parseInt(parAux[2].toString()) - 1;
                    }
                }
            }
            Iterator<Ambo> itrAmboReservas = listaAmbosReserva.iterator();
            while (itrAmboReservas.hasNext()) {
                Ambo amAux = itrAmboReservas.next();
                Iterator<Object[]> itrObjAux = listaDeTriplasAmbos.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] parAux = itrObjAux.next();
                    /* Comparo si el id de ambo son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (parAux[0].equals(amAux.getId())) {
                        parAux[2] = Integer.parseInt(parAux[2].toString()) - 1;
                    }
                }
            }
            listaDeTriplas.addAll(listaDeTriplasAmbos);
            //lista de resultado con los ambos disponibles
            List<Ambo> listaResultado = new LinkedList<>();
            Iterator<Object[]> itrListaDePares = listaDeTriplas.iterator();
            while (itrListaDePares.hasNext()) {
                Object[] objPar = itrListaDePares.next();
                /* Si la cantidad disponible es mayor a 1,
                 * agrego el articulo la lista de disponibles para alquilar */
                if (Integer.parseInt(objPar[2].toString()) > 0) {
                    Iterator<Ambo> itrListaAmbo = listaAmbo.iterator();
                    while (itrListaAmbo.hasNext()) {
                        Ambo amAux = itrListaAmbo.next();
                        if (amAux.getId().equals(Integer.parseInt(objPar[0].toString()))) {
                            listaResultado.add(amAux);
                        }
                    }
                }
            }
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return listaResultado;
    }

    //Carga la lista de articulos y ambos encontrados en la tabla busqueda de articulos
    private void actualizarTablaBusquedaArticulos() throws SQLException {
        String fechaDeBusqueda = reservaGui.getFechaEntregaReserva();
        if (fechaDeBusqueda != null) {
            List<Articulo> listaArticulosEncontrados = busquedaArticulos();
            List<Ambo> listaAmbosEncontrados = busquedaAmbos();
            if (listaArticulosEncontrados != null || listaAmbosEncontrados != null) { //si hay articulos o ambos los cargo en la gui
                DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaBusquedaArticulosReserva().getModel());
                modelo.setRowCount(0);
                BaseDatos.abrirBase();
                BaseDatos.openTransaction();
                Articulo ar;
                Ambo am;
                Object[] o = new Object[7];
                if (listaArticulosEncontrados != null) {
                    Iterator<Articulo> itrArticulo = listaArticulosEncontrados.iterator();
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
                }
                if (listaAmbosEncontrados != null) {
                    Iterator<Ambo> itrAmbo = listaAmbosEncontrados.iterator();
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
                }
                BaseDatos.commitTransaction();
                BaseDatos.cerrarBase();
            }
        }else{
            JOptionPane.showMessageDialog(reservaGui, "Por favor, ingrese la Fecha de Retiro para poder realizar la busqueda de artículos", "Error!", JOptionPane.ERROR_MESSAGE);
            }
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
                    //Cargo los articulos del Remito en la gui
                    this.remitoGui.getTablaArticulosRemito().setModel(modeloArticulos);
                    //Cargo el total del remito en la gui
                    int cantFilas = modeloArticulos.getRowCount();
                    Double totalRemito = 0.0;
                    for (int i = 0; i < cantFilas; i++) {
                        //saco el precio de cada articulo en la tabla de Articulos Reserva, que se encuentra en la fila "i" columna "5"
                        totalRemito += Double.parseDouble((String) reservaGui.getTablaArticulosReserva().getValueAt(i, 5));
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
                    this.reservaGui.hide();
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
