/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import com.toedter.calendar.JDateChooser;
import interfaz.GananciaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
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
public class ControladorGanacia implements ActionListener{

    private java.util.List<Baja> listBajas;
    private java.util.List<Remito> listRemitos;
    private GananciaGui gananciaGui;
    private JDateChooser desde;
    private JDateChooser hasta;
    private Date fechaDesde;
    private Date fechaHasta;

    public ControladorGanacia(GananciaGui gananciaGui) throws SQLException {
        this.gananciaGui = gananciaGui;
        this.gananciaGui.setActionListener(this);
        desde = gananciaGui.getFechaDesde();
        hasta = gananciaGui.getFechaHasta();
        fechaDesde = desde.getDate();
        fechaHasta = hasta.getDate();
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
                    calenDesdePropertyChange(e);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVerBajas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        hasta.getJCalendar().addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                try {
                    calenHastaPropertyChange(e);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVerBajas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void calenDesdePropertyChange(PropertyChangeEvent e) throws SQLException {
        final Calendar c = (Calendar) e.getNewValue();
        fechaDesde = c.getTime();
        realizarBusquedaPorFecha();
    }

    private void calenHastaPropertyChange(PropertyChangeEvent e) throws SQLException {
        final Calendar c = (Calendar) e.getNewValue();
        fechaHasta = c.getTime();
        realizarBusquedaPorFecha();
    }

    private void realizarBusquedaPorFecha() throws SQLException {
        BaseDatos.abrirBase();
        Base.openTransaction();
        listBajas = Baja.where("fecha between ? AND ? ", fechaDesde, fechaHasta);
        listRemitos = Remito.where("fecha_de_remito between ? AND ? and cerrado = ?", fechaDesde, fechaHasta, 1);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
