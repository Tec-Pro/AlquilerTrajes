/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import busqueda.BusquedaArticulo;
import interfaz.DisponibilidadArticulosGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Ambo;
import modelos.Articulo;

/**
 *
 * @author eze
 */
public class ControladorDisponibilidadArticulos implements ActionListener {

    private final DisponibilidadArticulosGui disponibilidadArticulosGui; //gui busqueda de articulos disponibles
    private String disponibilidad; //disponibilidad del articulo en la busqueda
    private String tipo; //tipo del articulo en la busqueda
    private final BusquedaArticulo busquedaArticulo;

    ControladorDisponibilidadArticulos(DisponibilidadArticulosGui estArtGui) {
        this.disponibilidadArticulosGui = estArtGui;
        this.disponibilidadArticulosGui.setActionListener(this);
        this.tipo = "Todos"; //por defecto, todos
        this.disponibilidad = "Todos";
        this.busquedaArticulo = new BusquedaArticulo();
        /*
         * Si modifico la fecha en el jdatechooser
         */
        this.disponibilidadArticulosGui.getDateFecha().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (disponibilidadArticulosGui.getDateFechaString() != null) {
                    try {
                        cargarTablaArticulosDisponibles();
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorDisponibilidadArticulos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void cargarTablaArticulosDisponibles() throws SQLException {
        /* CAMBIOS A REALIZAR EN PROXIMO COMMIT
        
        disponibilidad = (String) this.disponibilidadArticulosGui.getComboDisponibilidad().getSelectedItem();
        tipo = (String) this.disponibilidadArticulosGui.getComboTipo().getSelectedItem();
        List<Articulo> listaArticulo;
        List<Ambo> listaAmbo;
        if ("Todos".equals(disponibilidad) && "Todos".equals(tipo)) { //si hay que buscar todo
            //Busco todos los articulos y ambos
            listaArticulo = busquedaArticulo.buscarTodosLosArticulos();
            listaAmbo = busquedaArticulo.buscarTodosLosAmbos();
            //aca tengo que poner si estan disponibles o no y cargarlos en la tabla
            
        } else {
            if ("Todos".equals(disponibilidad)) { //si solo hay que buscar todas las disponibilidades
                //La disponibilidad es todos, busco solo los articulos por su tipo
                if (tipo.equals("Ambo")) {
                    listaAmbo = busquedaArticulo.buscarTodosLosAmbos();
                } else {
                    listaArticulo = busquedaArticulo.buscarArticulosPorTipo(tipo);
                }
                //aca tengo que poner si estan disponibles o no y cargarlos en la tabla
                
            } else {
                if ("Todos".equals(tipo)) { //si hay que buscar todos los tipos
                    listaArticulo = busquedaArticulo.buscarTodosLosArticulos();
                    listaAmbo = busquedaArticulo.buscarTodosLosAmbos();
                    //aca tengo que chequear que disponibilidad quiere y cargar solo esos articulos en la 
                    //tabla, tildando si estan disponibles o no
                } else { //sino hay que buscar tipo y disponibilidad especifica
                    //Busco solo los articulos por su tipo
                    if (tipo.equals("Ambo")) {
                        listaAmbo = busquedaArticulo.buscarTodosLosAmbos();
                    } else {
                        listaArticulo = busquedaArticulo.buscarArticulosPorTipo(tipo);
                    }
                    //Verifico la disponibilidad y filtro por este campo
                    
                    // aca tengo que cargar todos los datos en la tabla con su disponibilidad
                }
            }
        }*/
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        /*
         * Si selecciono algo en el combo box de disponibilidad
         */
        if (ae.getSource() == this.disponibilidadArticulosGui.getComboDisponibilidad()) {
            if (disponibilidadArticulosGui.getDateFechaString() != null) {
                try {
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
                    cargarTablaArticulosDisponibles();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorDisponibilidadArticulos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
