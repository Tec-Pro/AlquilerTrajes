/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import com.toedter.calendar.JDateChooser;
import interfaz.VerBajasGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Baja;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ControladorVerBajas implements ActionListener {

    private VerBajasGui verBajasGui;
    private java.util.List<Baja> listArticulos;
    private DefaultTableModel tablaArtDefault;
    private JTable tablaArticulos;
    private JDateChooser fechaBusqueda;

    public ControladorVerBajas(VerBajasGui verBajasGui) throws SQLException {
        this.verBajasGui = verBajasGui;
        this.verBajasGui.setActionListener(this);
        listArticulos = new LinkedList();
        tablaArticulos = verBajasGui.getArticulos();
        tablaArtDefault = verBajasGui.getTablaArticulosDefault();
        fechaBusqueda = verBajasGui.getFechaBusqueda();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        listArticulos = Baja.findAll();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        verBajasGui.getBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    busquedaKeyReleased(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVerBajas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fechaBusqueda.getJCalendar().addPropertyChangeListener("calendar", new PropertyChangeListener() {
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

    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) throws SQLException {
        realizarBusqueda();
    }

    private void calenPropertyChange(PropertyChangeEvent e) throws SQLException {
        realizarBusquedaPorFecha();
    }

    private void realizarBusqueda() throws SQLException {
        BaseDatos.abrirBase();
        Base.openTransaction();
        listArticulos = Baja.where("(modelo like ? or descripcion like ? or marca like ? or id like ? or tipo like ?)", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%");
        Base.openTransaction();
        BaseDatos.cerrarBase();
        actualizarLista();
    }

    private void realizarBusquedaPorFecha() throws SQLException {
        if (verBajasGui.getBuscarPorFecha().isSelected()) {
            BaseDatos.abrirBase();
            Base.openTransaction();            
            listArticulos = Baja.where("fecha = ?", verBajasGui.getFechaBusqueda().getDate());
            Base.openTransaction();
            BaseDatos.cerrarBase();
            actualizarLista();
        }
    }

    private void actualizarLista() throws SQLException {
        BaseDatos.abrirBase();
        Base.openTransaction();
        tablaArtDefault.setRowCount(0);
        Iterator<Baja> it = listArticulos.iterator();
        while (it.hasNext()) {
            Baja art = it.next();
            Object row[] = new String[7];
            row[0] = art.getString("id");
            row[1] = art.getString("tipo");
            row[2] = art.getString("modelo");
            row[3] = art.getString("marca");
            row[4] = art.getString("talle");
            row[5] = art.getString("fecha");
            row[6] = art.getBigDecimal("cobro").setScale(2, RoundingMode.CEILING).toString();
            tablaArtDefault.addRow(row);
        }
        Base.openTransaction();
        BaseDatos.cerrarBase();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
