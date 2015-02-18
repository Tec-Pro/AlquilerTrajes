/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import abm.ABMArticulo;
import abm.ABMBaja;
import interfaz.BajaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.Baja;

/**
 *
 * @author jacinto
 */
public class ControladorBaja implements ActionListener {

    private BajaGui bajaGui;
    private DefaultTableModel tablaArtDefault;
    private JTable tablaArticulos;
    private Articulo articulo;
    private java.util.List<Articulo> listArticulos;
    private ABMArticulo abmArticulo;
    private ABMBaja abmBaja;

    public ControladorBaja(BajaGui bajaGui) throws SQLException {
        this.bajaGui = bajaGui;
        articulo = new Articulo();
        this.bajaGui.setActionListener(this);
        tablaArtDefault = bajaGui.getTablaArticulosDefault();
        tablaArticulos = bajaGui.getTablaArticulos();
        listArticulos = new LinkedList();
        abmBaja = new ABMBaja();
        abmArticulo = new ABMArticulo();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        listArticulos = Articulo.where("stock >? ", 0);
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        actualizarLista();
        bajaGui.getBusquedaCodigoArticulo().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
    }

    private void busquedaKeyReleased(KeyEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void actualizarLista() throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        tablaArtDefault.setRowCount(0);
        Iterator<Articulo> it = listArticulos.iterator();
        while (it.hasNext()) {
            Articulo art = it.next();
            Object row[] = new String[6];
            row[0] = art.getString("id");
            row[1] = art.getString("modelo");
            row[2] = art.getString("marca");
            row[3] = art.getString("tipo");
            row[4] = art.getString("talle");
            row[5] = art.getBigDecimal("precio_compra").setScale(2, RoundingMode.CEILING).toString();
            tablaArtDefault.addRow(row);
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bajaGui.getDarBaja()) {
            tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 0);
            Articulo art = Articulo.findById(tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 0));
            Baja b = new Baja();
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String currentTime = sdf.format(dt);
            b.set("cobro", Float.valueOf(bajaGui.getCobro().getText()),
                    "descripcion", bajaGui.getDescripcion(),
                    "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "talle", art.get("talle"),
                    "fecha", currentTime,
                    "tipo", art.get("tipo"));
            try {
                abmBaja.alta(b);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorBaja.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
