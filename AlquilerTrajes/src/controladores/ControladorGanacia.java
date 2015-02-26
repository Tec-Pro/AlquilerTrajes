/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import com.toedter.calendar.JDateChooser;
import interfaz.GananciaGui;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Baja;
import modelos.Remito;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ControladorGanacia {

    private java.util.List<Baja> listBajas;
    private java.util.List<Remito> listRemitos;
    private GananciaGui gananciaGui;
    private JDateChooser desde;
    private JDateChooser hasta;

    public ControladorGanacia(GananciaGui gananciaGui) throws SQLException {
        this.gananciaGui = gananciaGui;
        desde = gananciaGui.getFechaDesde();
        hasta = gananciaGui.getFechaHasta();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        listBajas = Baja.findAll();
        listRemitos = Remito.findAll();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        desde.getJCalendar().addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                try {
                    calenPropertyChange(e);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVerBajas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        hasta.getJCalendar().addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                try {
                    calenPropertyChange(e);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVerBajas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void calenPropertyChange(PropertyChangeEvent e) throws SQLException {
        realizarBusquedaPorFecha();
    }

    private void realizarBusquedaPorFecha() throws SQLException {
        BaseDatos.abrirBase();
        Base.openTransaction();
        listBajas = Baja.where("fecha between ? AND ? ", gananciaGui.getFechaDesde().getDate(), gananciaGui.getFechaHasta().getDate());
        listRemitos = Remito.where("fecha_de_remito between ? AND ? ", gananciaGui.getFechaDesde().getDate(), gananciaGui.getFechaHasta().getDate());
        Base.openTransaction();
        BaseDatos.cerrarBase();
        actualizarPrecio();
    }

    private void actualizarPrecio() throws SQLException {
        BaseDatos.abrirBase();
        Base.openTransaction();
        Iterator<Baja> it = listBajas.iterator();
        Iterator<Remito> it2 = listRemitos.iterator();
        float ganancia = 0;
        while (it.hasNext()) {
            ganancia += it.next().getFloat("cobro");
        }
        while (it2.hasNext()) {
            ganancia += it2.next().getFloat("total");
        }
        gananciaGui.getGanancia().setText(String.valueOf(ganancia));
        Base.openTransaction();
        BaseDatos.cerrarBase();
    }
}
