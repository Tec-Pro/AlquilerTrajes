/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMAmbo;
import interfaz.RegistroAmboGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import BD.BaseDatos;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelos.Ambo;

/**
 *
 * @author jacinto
 */
public class ControladorRegistroAmbo implements ActionListener {

    private RegistroAmboGui registroAmboGui;
    private DefaultTableModel tablaArtDefault;
    private JTable tablaArticulos;
    private DefaultTableModel tablaAmboDefault;
    private JTable tablaAmbo;
    private Articulo articulo;
    private java.util.List<Articulo> listArticulos;
    private ABMAmbo abmAmbo;

    public ControladorRegistroAmbo(RegistroAmboGui registroAmboGui) throws SQLException {
        this.registroAmboGui = registroAmboGui;
        this.registroAmboGui.setActionListener(this);
        tablaArtDefault = registroAmboGui.getTablaArticulosDefault();
        tablaArticulos = registroAmboGui.getTablaArticulos();
        tablaAmboDefault = registroAmboGui.getTablaFacturaDefault();
        tablaAmbo = registroAmboGui.getTablaFactura();
        abmAmbo = new ABMAmbo();
        listArticulos = new LinkedList();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        listArticulos = Articulo.findAll();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        actualizarLista();
        this.registroAmboGui.getBusquedaCodigoArticulo().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    realizarBusqueda();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tablaArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    tablaMouseClicked(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorRegistroAmbo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void actualizarLista() throws SQLException {
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
            row[5] = art.getString("descripcion");
            tablaArtDefault.addRow(row);
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    public void tablaMouseClicked(java.awt.event.MouseEvent evt) throws SQLException {
        if (!BaseDatos.hasConnection()) {
            BaseDatos.abrirBase();
            BaseDatos.openTransaction();
        }
        int[] rows = tablaArticulos.getSelectedRows();
        if (rows.length > 0) {
            for (int i = 0; i < rows.length; i++) {
                if (!existeProdAmbo(Integer.valueOf((String) tablaArticulos.getValueAt(rows[i], 0)))) {
                    Articulo p = Articulo.findFirst("id = ?", (tablaArticulos.getValueAt(rows[i], 0)));
                    Object cols[] = new Object[7];
                    cols[0] = p.get("id");
                    cols[1] = p.get("modelo");
                    cols[2] = p.get("marca");
                    cols[3] = p.get("tipo");
                    cols[4] = p.get("talle");
                    cols[5] = p.getFloat("precio_alquiler");
                    tablaAmboDefault.addRow(cols);
                    actualizarPrecio();
                } else {
                    System.out.println("que hace guacho");
                }
            }
        }
        if (BaseDatos.hasConnection()) {
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
        }
    }

    private boolean existeProdAmbo(int id) {
        boolean ret = false;
        for (int i = 0; i < tablaAmbo.getRowCount() && !ret; i++) {
            ret = (Integer) tablaAmbo.getValueAt(i, 0) == id;
        }
        return ret;
    }

    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) throws SQLException {
        System.out.println("estoy buscando como un campeon");
        realizarBusqueda();
    }

    private void realizarBusqueda() throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        listArticulos = Articulo.where("modelo like ? or descripcion like ? or marca like ? or id like ? or tipo like ? or talle like ?", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%");
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        actualizarLista();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registroAmboGui.getRegistrar()) {
            if (registroAmboGui.getNombreAmbo().getText().isEmpty()) {
                JOptionPane.showMessageDialog(registroAmboGui, "Ambo sin nombre, no se guardó el ambo", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    try {
                        BaseDatos.abrirBase();
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorRegistroAmbo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    BaseDatos.openTransaction();
                    if (abmAmbo.findNombre(registroAmboGui.getNombreAmbo().getText())) {
                        BaseDatos.commitTransaction();
                        BaseDatos.cerrarBase();
                        JOptionPane.showMessageDialog(registroAmboGui, "nombre repetido, no se guardó el ambo", "Error!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Ambo am = new Ambo();
                        am.set("nombre", registroAmboGui.getNombreAmbo().getText());
                        am.set("precio_alquiler", Float.valueOf(registroAmboGui.getTotalFactura().getText()));
                        String marca = "";
                        String talle = "";
                        int stock = 0;
                        for (int i = 0; i < tablaAmbo.getRowCount(); i++) {
                            if (i == tablaAmbo.getRowCount() - 1) {
                                marca += tablaAmbo.getValueAt(i, 2);
                                talle += tablaAmbo.getValueAt(i, 4);
                            } else {
                                marca += tablaAmbo.getValueAt(i, 2) + "-";
                                talle += tablaAmbo.getValueAt(i, 4) + "-";
                            }
                            if (stock > Articulo.findById(tablaAmbo.getValueAt(i, 0)).getInteger("stock")) {
                                stock = Articulo.findById(tablaAmbo.getValueAt(i, 0)).getInteger("stock");
                            }
                        }
                        BaseDatos.commitTransaction();
                        BaseDatos.cerrarBase();
                        am.set("marca", marca);
                        am.set("talle", talle);
                        am.set("stock", stock);
                        if (abmAmbo.alta(am)) {
                            for (int i = 0; i < tablaAmbo.getRowCount(); i++) {
                                try {
                                    BaseDatos.abrirBase();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ControladorRegistroAmbo.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                BaseDatos.openTransaction();
                                Ambo b = Ambo.findById(abmAmbo.getUltimoId());
                                Articulo a = Articulo.findById(tablaAmbo.getValueAt(i, 0));
                                b.add(a);
                                BaseDatos.commitTransaction();
                                BaseDatos.cerrarBase();
                            }
                            registroAmboGui.clearAll();
                            JOptionPane.showMessageDialog(registroAmboGui, "¡Ambo guardado exitosamente!");
                        } else {
                            JOptionPane.showMessageDialog(registroAmboGui, "Error, no se guardó el ambo", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorRegistroAmbo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == registroAmboGui.getBorrarArticulosSeleccionados()) {
            int[] rows = tablaAmbo.getSelectedRows();
            if (rows.length > 0) {
                Integer[] idABorrar = new Integer[rows.length];
                for (int i = 0; i < rows.length; i++) {
                    idABorrar[i] = (Integer) tablaAmbo.getValueAt(rows[i], 0);
                }
                int i = 0;
                int cantABorrar = 0;
                while (cantABorrar < rows.length) {
                    while (i < tablaAmbo.getRowCount()) {
                        if ((Integer) tablaAmbo.getValueAt(i, 0) == idABorrar[cantABorrar]) {
                            tablaAmboDefault.removeRow(i);
                            cantABorrar++;
                        }
                        i++;
                    }
                    i = 0;
                }
                if (tablaAmbo.getRowCount() == 0) {
                    registroAmboGui.getTotalFactura().setText("0");
                } else {
                    actualizarPrecio();
                }
            }
        }
    }

    public void actualizarPrecio() {
        float total = 0;
        for (int i = 0; i < tablaAmbo.getRowCount(); i++) {
            total = total + (float) tablaAmbo.getValueAt(i, 5);
        }
        registroAmboGui.getTotalFactura().setText(String.valueOf(total));
    }
}
