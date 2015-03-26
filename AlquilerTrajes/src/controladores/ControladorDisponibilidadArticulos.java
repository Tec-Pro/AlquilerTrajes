/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
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
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import modelos.Ambo;
import modelos.Articulo;
import modelos.Remito;
import modelos.Reserva;
import org.codehaus.groovy.syntax.Numbers;

/**
 *
 * @author eze
 */
public class ControladorDisponibilidadArticulos implements ActionListener {

    private final DisponibilidadArticulosGui disponibilidadArticulosGui; //gui busqueda de articulos disponibles
    private String disponibilidad; //disponibilidad del articulo en la busqueda
    private String tipo; //tipo del articulo en la busqueda

    ControladorDisponibilidadArticulos(DisponibilidadArticulosGui estArtGui) {
        this.disponibilidadArticulosGui = estArtGui;
        this.disponibilidadArticulosGui.setActionListener(this);
        this.tipo = "Todos"; //por defecto, todos
        this.disponibilidad = "Disponibles";
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
        disponibilidad = (String) this.disponibilidadArticulosGui.getComboDisponibilidad().getSelectedItem();
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
        List<Object[]> listaDeFilas = new LinkedList<>();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        /* ---------------------------------------------------------------------------------------------------------
         * SI HAY QUE BUSCAR ARTICULOS Y AMBOS DISPONIBLES DE TODOS LOS TIPOS
         * ---------------------------------------------------------------------------------------------------------
         */
        if ("Disponibles".equals(disponibilidad) && "Todos".equals(tipo)) {
            //saco todos los articulos de la base
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
                    listaArticulosReserva.addAll(rAux.getAll(Articulo.class));
                    listaAmbosReserva.addAll(rAux.getAll(Ambo.class));
                }
            }
            /* Creo una lista de filas, con la totalidad de Articulos y Ambos
             * Cada arreglo(fila) de la lista(listaDeFilas) contiene 
             * {id,modelo,marca,tipo,talle,descripcion,precio_alquiler,disponible(booleano),cantidad disp.}
             */
            if (listaArticulo != null) {
                Iterator<Articulo> itrArticulos = listaArticulo.iterator();
                while (itrArticulos.hasNext()) {
                    Object[] fila = new Object[9];
                    Articulo ar = itrArticulos.next();
                    fila[0] = (ar.getId());
                    fila[1] = (ar.getString("modelo"));
                    fila[2] = (ar.getString("marca"));
                    fila[3] = (ar.getString("tipo"));
                    fila[4] = (ar.getString("talle"));
                    fila[5] = (ar.getString("descripcion"));
                    fila[6] = (ar.getString("precio_alquiler"));
                    fila[8] = (ar.getString("stock"));
                    listaDeFilas.add(fila);
                }
            }
            if (listaAmbo != null) {
                Iterator<Ambo> itrAmbos = listaAmbo.iterator();
                while (itrAmbos.hasNext()) {
                    Object[] fila = new Object[9];
                    Ambo am = itrAmbos.next();
                    fila[0] = (am.getId());
                    fila[1] = (am.get("nombre"));
                    fila[2] = (am.getString("marca"));
                    fila[3] = ("ambo");
                    fila[4] = (am.getString("talle"));
                    fila[5] = (am.getString("descripcion"));
                    fila[6] = (am.getString("precio_alquiler"));
                    fila[8] = (am.getString("stock"));
                    listaDeFilas.add(fila);
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
                Iterator<Object[]> itrObjAux = listaDeFilas.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] filaAux = itrObjAux.next();
                    /* Comparo si el id y el tipo del art son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (filaAux[0].equals(artAux.getId()) && filaAux[3].equals(artAux.getString("tipo"))) {
                        filaAux[8] = Integer.parseInt(filaAux[8].toString()) - 1;
                    }
                }
            }

            Iterator<Articulo> itrArticuloReservas = listaArticulosReserva.iterator();
            while (itrArticuloReservas.hasNext()) {
                Articulo artAux = itrArticuloReservas.next();
                Iterator<Object[]> itrObjAux = listaDeFilas.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] filaAux = itrObjAux.next();
                    /* Comparo si el id y el tipo del art son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (filaAux[0].equals(artAux.getId()) && filaAux[3].equals(artAux.getString("tipo"))) {
                        filaAux[8] = Integer.parseInt(filaAux[8].toString()) - 1;
                    }
                }
            }

            Iterator<Ambo> itrAmboRemitos = listaAmbosRemito.iterator();
            while (itrAmboRemitos.hasNext()) {
                Ambo ambAux = itrAmboRemitos.next();
                Iterator<Object[]> itrObjAux = listaDeFilas.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] filaAux = itrObjAux.next();
                    /* Comparo si el id y el tipo del art son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (filaAux[0].equals(ambAux.getId()) && filaAux[3].equals("ambo")) {
                        filaAux[8] = Integer.parseInt(filaAux[8].toString()) - 1;
                    }
                }
            }

            Iterator<Ambo> itrAmboReservas = listaAmbosReserva.iterator();
            while (itrAmboReservas.hasNext()) {
                Ambo ambAux = itrAmboReservas.next();
                Iterator<Object[]> itrObjAux = listaDeFilas.iterator();
                while (itrObjAux.hasNext()) {
                    Object[] filaAux = itrObjAux.next();
                    /* Comparo si el id y el tipo del art son iguales,
                     * si lo son decremento en 1 la cantidad disponible
                     * del mismo para alquilar
                     */
                    if (filaAux[0].equals(ambAux.getId()) && filaAux[3].equals("ambo")) {
                        filaAux[8] = Integer.parseInt(filaAux[8].toString()) - 1;
                    }
                }
            }

            //cargo los datos en la tabla
            DefaultTableModel modelo = ((DefaultTableModel) disponibilidadArticulosGui.getTablaDisponibilidadArticulos().getModel());
            Iterator<Object[]> itrListaDeFilas = listaDeFilas.iterator();
            while (itrListaDeFilas.hasNext()) {
                modelo.addRow(itrListaDeFilas.next());
            }

        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        /*
         * Si selecciono algo en el combo box de disponibilidad
         */
        if (ae.getSource() == this.disponibilidadArticulosGui.getComboDisponibilidad()) {
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
