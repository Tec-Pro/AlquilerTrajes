/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import busqueda.BusquedaArticulo;
import interfaz.DisponibilidadArticulosGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelos.Ambo;
import modelos.Articulo;
import modelos.Remito;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class ControladorDisponibilidadArticulos implements ActionListener {

    private final DisponibilidadArticulosGui disponibilidadArticulosGui; //gui busqueda de articulos disponibles
    private String tipo; //tipo del articulo en la busqueda

    ControladorDisponibilidadArticulos(DisponibilidadArticulosGui estArtGui) {
        this.disponibilidadArticulosGui = estArtGui;
        this.disponibilidadArticulosGui.setActionListener(this);
        this.tipo = "Todos"; //por defecto, todos
        /*
         * Si modifico la fecha en el jdatechooser
         */
        this.disponibilidadArticulosGui.getDateFecha().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (disponibilidadArticulosGui.getDateFechaString() != null) {
                    try {
                        //limpio la tabla y luego la cargo con los datos nuevos
                        ((DefaultTableModel) disponibilidadArticulosGui.getTablaDisponibilidadArticulos().getModel()).setRowCount(0);
                        cargarTablaArticulosDisponibles();
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorDisponibilidadArticulos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void cargarTablaArticulosDisponibles() throws SQLException {
        tipo = (String) this.disponibilidadArticulosGui.getComboTipo().getSelectedItem();
        ///SI HAY QUE BUSCAR ARTICULOS Y AMBOS DISPONIBLES DE TODOS LOS TIPOS
        if ("Todos".equals(tipo)) {
            cargarArticulosDisponibles();
            cargarAmbosDisponibles();

        } else {
            //SI HAY QUE BUSCAR AMBOS DISPONIBLES
            if ("Ambo".equals(tipo)) {
                cargarAmbosDisponibles();

            }//SINO HAY QUE BUSCAR ARTICULOS DE TODOS LOS TIPOS QUE NO SON AMBOS
            else {
                cargarArticulosDisponibles();
            }
        }

    }

    //cargo en la tabla solo los ambos con su disponibilidad
    private void cargarAmbosDisponibles() throws SQLException {
        tipo = (String) this.disponibilidadArticulosGui.getComboTipo().getSelectedItem();
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
        List<Object[]> listaDeFilas = new LinkedList<>(), listaDeFilasAmbos = new LinkedList<>(), listaDeFilasArticulos = new LinkedList<>();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        ///saco todos los articulos de la base
        listaArticulo = Articulo.findAll();
        //saco todos los ambos de la base
        listaAmbo = Ambo.findAll();
        //saco los articulos y ambos de los remitos en una fecha determinada
        List<Remito> listaRemitos = Remito.where("fecha_de_remito = ?", this.disponibilidadArticulosGui.getDateFechaString());
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
        List<Reserva> listaReservas = Reserva.where("fecha_entrega_reserva= ?", this.disponibilidadArticulosGui.getDateFechaString());
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
        /* Creo una lista de filas, con la totalidad de Articulos y Ambos
         * Cada arreglo(fila) de la lista(listaDeFilas) contiene 
         * {id,modelo,marca,tipo,talle,descripcion,precio_alquiler,disponible(booleano),cantidad disp.}
         */
        if (listaArticulo != null) {
            Iterator<Articulo> itrArticulos = listaArticulo.iterator();
            while (itrArticulos.hasNext()) {
                Object[] fila = new Object[10];
                Articulo ar = itrArticulos.next();
                fila[0] = (ar.getId());
                fila[1] = (ar.getString("modelo"));
                fila[2] = (ar.getString("marca"));
                fila[3] = (ar.getString("tipo"));
                fila[4] = (ar.getString("talle"));
                fila[5] = (ar.getString("stock"));
                fila[6] = (ar.getString("descripcion"));
                fila[7] = (ar.getString("precio_alquiler"));
                fila[9] = (ar.getString("stock"));
                listaDeFilasArticulos.add(fila);
            }
        }
        if (listaAmbo != null) {
            Iterator<Ambo> itrAmbos = listaAmbo.iterator();
            while (itrAmbos.hasNext()) {
                Object[] fila = new Object[10];
                Ambo am = itrAmbos.next();
                fila[0] = (am.getId());
                fila[1] = (am.get("nombre"));
                fila[2] = (am.getString("marca"));
                fila[3] = ("ambo");
                fila[4] = (am.getString("talle"));
                fila[5] = (am.getString("stock"));
                fila[6] = (am.getString("descripcion"));
                fila[7] = (am.getString("precio_alquiler"));
                fila[9] = (am.getString("stock"));
                listaDeFilasAmbos.add(fila);
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
            Iterator<Object[]> itrObjAux = listaDeFilasArticulos.iterator();
            while (itrObjAux.hasNext()) {
                Object[] filaAux = itrObjAux.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en 1 la cantidad disponible
                 * del mismo para alquilar
                 */
                if (filaAux[0].equals(artAux.getId()) && filaAux[3].equals(artAux.getString("tipo"))) {
                    filaAux[9] = Integer.parseInt(filaAux[9].toString()) - 1;
                }
            }
        }

        Iterator<Articulo> itrArticuloReservas = listaArticulosReserva.iterator();
        while (itrArticuloReservas.hasNext()) {
            Articulo artAux = itrArticuloReservas.next();
            Iterator<Object[]> itrObjAux = listaDeFilasArticulos.iterator();
            while (itrObjAux.hasNext()) {
                Object[] filaAux = itrObjAux.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en 1 la cantidad disponible
                 * del mismo para alquilar
                 */
                if (filaAux[0].equals(artAux.getId()) && filaAux[3].equals(artAux.getString("tipo"))) {
                    filaAux[9] = Integer.parseInt(filaAux[9].toString()) - 1;
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
                Iterator<Object[]> itrObjAuxArticulo = listaDeFilasArticulos.iterator();
                Articulo artAmbo = itrArticulosAmbos.next();
                while (itrObjAuxArticulo.hasNext()) {
                    Object[] filaAux = itrObjAuxArticulo.next();
                    /*si el id de articulo del ambo coincide con el de la listaDeFilasArticulos
                     * (lista que contiene la cantidad disponible de cada articulo), el stock
                     * del art es diferente a la cantidad disponible del mismo, y no es un ambo
                     * ya que estoy comparando solo articulos
                     */
                    if (filaAux[0].equals(artAmbo.getId()) && !filaAux[5].equals(filaAux[9]) && !filaAux[3].equals("ambo")) {
                        /* Si la cantADescontar es menor a la cantidad de articulos alquilados
                         * (stock - artDisponibles)
                         */
                        if ((Integer.parseInt(filaAux[5].toString()) - Integer.parseInt(filaAux[9].toString())) > cantADescontar) {
                            //actualizo la cantidad a descontar de ambos disponibles
                            cantADescontar = (Integer.parseInt(filaAux[5].toString()) - Integer.parseInt(filaAux[9].toString()));
                        }
                    }
                }

            }
            Iterator<Object[]> itrObjAuxAmbo = listaDeFilasAmbos.iterator();
            while (itrObjAuxAmbo.hasNext()) {
                Object[] filaAux = itrObjAuxAmbo.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en cantADescontar calculado
                 * previamente la cantidad disponible del mismo para
                 * alquilar
                 */
                if (filaAux[0].equals(amboRemito.getId()) && filaAux[3].equals("ambo")) {
                    filaAux[9] = Integer.parseInt(filaAux[9].toString()) - cantADescontar;
                }
            }
        }
        //si el ambo entero fue alquilado
        Iterator<Ambo> itrAmboRemitos = listaAmbosRemito.iterator();
        while (itrAmboRemitos.hasNext()) {
            Ambo ambAux = itrAmboRemitos.next();
            Iterator<Object[]> itrObjAux = listaDeFilasAmbos.iterator();
            while (itrObjAux.hasNext()) {
                Object[] filaAux = itrObjAux.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en 1 la cantidad disponible
                 * del mismo para alquilar
                 */
                if (filaAux[0].equals(ambAux.getId())) {
                    filaAux[9] = Integer.parseInt(filaAux[9].toString()) - 1;
                }
            }
        }

        Iterator<Ambo> itrAmboReservas = listaAmbosReserva.iterator();
        while (itrAmboReservas.hasNext()) {
            Ambo ambAux = itrAmboReservas.next();
            Iterator<Object[]> itrObjAux = listaDeFilasAmbos.iterator();
            while (itrObjAux.hasNext()) {
                Object[] filaAux = itrObjAux.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en 1 la cantidad disponible
                 * del mismo para alquilar
                 */
                if (filaAux[0].equals(ambAux.getId()) && filaAux[3].equals("ambo")) {
                    filaAux[9] = Integer.parseInt(filaAux[9].toString()) - 1;
                }
            }
        }

        listaDeFilas.addAll(listaDeFilasAmbos);

        //cargo los datos en la tabla
        DefaultTableModel modelo = ((DefaultTableModel) disponibilidadArticulosGui.getTablaDisponibilidadArticulos().getModel());
        Iterator<Object[]> itrListaDeFilas = listaDeFilas.iterator();
        while (itrListaDeFilas.hasNext()) {
            Object[] objFila = itrListaDeFilas.next();
            /* Si la cantidad disponible es mayor a 0,
             * marco en la tabla que este articulo esta disponible para alquilar */
            if (Integer.parseInt(objFila[9].toString()) > 0) {
                objFila[8] = true;
            }
            modelo.addRow(objFila);
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    //cargo en la tabla todos los articulos(sin los ambos) con su disponibilidad
    private void cargarArticulosDisponibles() throws SQLException {
        tipo = (String) this.disponibilidadArticulosGui.getComboTipo().getSelectedItem();
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
        List<Object[]> listaDeFilas = new LinkedList<>(), listaDeFilasAmbos = new LinkedList<>(), listaDeFilasArticulos = new LinkedList<>();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        //saco todos los articulos de la base
        if ("Todos".equals(tipo)) {
            listaArticulo = Articulo.findAll();

        }else{
            listaArticulo = Articulo.where("tipo = ?", tipo);
        }
        //saco los articulos y ambos de los remitos en una fecha determinada
        List<Remito> listaRemitos = Remito.where("fecha_de_remito = ?", this.disponibilidadArticulosGui.getDateFechaString());
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
        List<Reserva> listaReservas = Reserva.where("fecha_entrega_reserva= ?", this.disponibilidadArticulosGui.getDateFechaString());
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
        /* Creo una lista de filas, con la totalidad de Articulos
         * Cada arreglo(fila) de la lista(listaDeFilas) contiene 
         * {id,modelo,marca,tipo,talle,descripcion,precio_alquiler,disponible(booleano),cantidad disp.}
         */
        if (listaArticulo != null) {
            Iterator<Articulo> itrArticulos = listaArticulo.iterator();
            while (itrArticulos.hasNext()) {
                Object[] fila = new Object[10];
                Articulo ar = itrArticulos.next();
                fila[0] = (ar.getId());
                fila[1] = (ar.getString("modelo"));
                fila[2] = (ar.getString("marca"));
                fila[3] = (ar.getString("tipo"));
                fila[4] = (ar.getString("talle"));
                fila[5] = (ar.getString("stock"));
                fila[6] = (ar.getString("descripcion"));
                fila[7] = (ar.getString("precio_alquiler"));
                fila[9] = (ar.getString("stock"));
                listaDeFilasArticulos.add(fila);
            }
        }

        //guardo en listaAmbo todos los ambos reservados o alquilados para una fecha determinada;
        listaAmbo.addAll(listaAmbosRemito);
        listaAmbo.addAll(listaAmbosReserva);

        /*
         * Resto la cantidad de articulos y articulos de ambos, de Remitos y Reservas en la fecha especificada,
         * al total de articulos y ambos encontrados en la base de datos (calculando asi la cant.
         * de articulos y ambos disponibles para alquilar)
         */
        Iterator<Articulo> itrArticuloRemitos = listaArticulosRemito.iterator();
        while (itrArticuloRemitos.hasNext()) {
            Articulo artAux = itrArticuloRemitos.next();
            Iterator<Object[]> itrObjAux = listaDeFilasArticulos.iterator();
            while (itrObjAux.hasNext()) {
                Object[] filaAux = itrObjAux.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en 1 la cantidad disponible
                 * del mismo para alquilar
                 */
                if (filaAux[0].equals(artAux.getId()) && filaAux[3].equals(artAux.getString("tipo"))) {
                    filaAux[9] = Integer.parseInt(filaAux[9].toString()) - 1;
                }
            }
        }

        Iterator<Articulo> itrArticuloReservas = listaArticulosReserva.iterator();
        while (itrArticuloReservas.hasNext()) {
            Articulo artAux = itrArticuloReservas.next();
            Iterator<Object[]> itrObjAux = listaDeFilasArticulos.iterator();
            while (itrObjAux.hasNext()) {
                Object[] filaAux = itrObjAux.next();
                /* Comparo si el id y el tipo del art son iguales,
                 * si lo son decremento en 1 la cantidad disponible
                 * del mismo para alquilar
                 */
                if (filaAux[0].equals(artAux.getId()) && filaAux[3].equals(artAux.getString("tipo"))) {
                    filaAux[9] = Integer.parseInt(filaAux[9].toString()) - 1;
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
                Iterator<Object[]> itrObjAux = listaDeFilasArticulos.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] filaAux = itrObjAux.next();
                    /* Comparo si el id y el tipo del art son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (filaAux[0].equals(artAux.getId()) && filaAux[3].equals(artAux.getString("tipo"))) {
                        filaAux[9] = Integer.parseInt(filaAux[9].toString()) - 1;
                    }
                }
            }

        }

        listaDeFilas.addAll(listaDeFilasArticulos);

        //cargo los datos en la tabla
        DefaultTableModel modelo = ((DefaultTableModel) disponibilidadArticulosGui.getTablaDisponibilidadArticulos().getModel());
        Iterator<Object[]> itrListaDeFilas = listaDeFilas.iterator();
        while (itrListaDeFilas.hasNext()) {
            Object[] objFila = itrListaDeFilas.next();
            /* Si la cantidad disponible es mayor a 0,
             * marco en la tabla que este articulo esta disponible para alquilar */
            if (Integer.parseInt(objFila[9].toString()) > 0) {
                objFila[8] = true;
            }
            modelo.addRow(objFila);
        }

        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        /*
         * Si selecciono algo en el combo box de tipos
         */
        if (ae.getSource() == this.disponibilidadArticulosGui.getComboTipo()) {
            if (disponibilidadArticulosGui.getDateFechaString() != null) {
                try {
                    //limpio la tabla y luego la cargo con los datos nuevos
                    ((DefaultTableModel) disponibilidadArticulosGui.getTablaDisponibilidadArticulos().getModel()).setRowCount(0);
                    cargarTablaArticulosDisponibles();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorDisponibilidadArticulos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
