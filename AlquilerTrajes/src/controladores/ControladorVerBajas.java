/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import interfaz.VerBajasGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
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

    public ControladorVerBajas(VerBajasGui verBajasGui) throws SQLException {
        this.verBajasGui = verBajasGui;
        listArticulos = new LinkedList();
        tablaArticulos = verBajasGui.getArticulos();
        tablaArtDefault = verBajasGui.getTablaArticulosDefault();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        listArticulos = Baja.findAll();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        verBajasGui.getBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
    }

    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

    private void realizarBusqueda() {
        Base.openTransaction();
        listArticulos = Baja.where("(modelo like ? or descripcion like ? or marca like ? or id like ? or nombre like ? or id like ?)", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%", "%" + verBajasGui.getBusqueda().getText() + "%");
        Base.openTransaction();
        actualizarLista();
    }

    private void actualizarLista() {
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
        verBajasGui.getCantidadArticulos().setText(String.valueOf(tablaArticulos.getRowCount()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
