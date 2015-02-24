/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import abm.ABMAmbo;
import abm.ABMArticulo;
import interfaz.ArticuloGui;
import interfaz.RegistroAmboGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Ambo;
import modelos.Articulo;

/**
 *
 * @author nico
 */
public class ControladorArticulo implements ActionListener, FocusListener {

    private ArticuloGui articuloGui;
    private DefaultTableModel tablaArtDefault;
    private java.util.List<Articulo> listArticulos;
    private java.util.List<Ambo> listAmbo;
    private JTable tablaArticulos;
    private ABMArticulo abmArticulo;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Articulo articulo;
    private RegistroAmboGui registroAmbo;
    private Ambo ambo;
    private ABMAmbo abmAmbo;

    public ControladorArticulo(ArticuloGui articuloGui, RegistroAmboGui registroAmbo) throws ClassNotFoundException, SQLException {
        isNuevo = true; //para saber si es nuevo o no
        editandoInfo = false; // se esta editando la info
        articulo = new Articulo();
        ambo = new Ambo();
        this.registroAmbo = registroAmbo;
        this.articuloGui = articuloGui;
        this.articuloGui.setActionListener(this);
        tablaArtDefault = articuloGui.getTablaArticulosDefault();
        tablaArticulos = articuloGui.getArticulos();
        listArticulos = new LinkedList();
        listAmbo = new LinkedList();
        abmArticulo = new ABMArticulo();
        abmAmbo = new ABMAmbo();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        listArticulos = Articulo.findAll();
        listAmbo = Ambo.findAll();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        actualizarLista();
        articuloGui.getBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    busquedaKeyReleased(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        articuloGui.getArticulos().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    tablaMouseClicked(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) throws SQLException {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

    private void realizarBusqueda() throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        listArticulos = Articulo.where("(modelo like ? or descripcion like ? or marca like ? or id like ? or tipo like ? or talle like ?)", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%");
        listAmbo = Ambo.where("(nombre like ? or descripcion like ? or marca like ? or id like ? or talle like ?)", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%");
        BaseDatos.openTransaction();
        BaseDatos.cerrarBase();
        actualizarLista();
    }

    public void tablaMouseClicked(java.awt.event.MouseEvent evt) throws SQLException {
        if (evt.getClickCount() == 2) {
            //bloqueo los campos y habilito botones
            articuloGui.habilitarCampos(false);
            articuloGui.getBorrar().setEnabled(true);
            articuloGui.getModificar().setEnabled(true);
            articuloGui.getGuardar().setEnabled(false);
            articuloGui.getNuevo().setEnabled(true);
            editandoInfo = false;
            articuloGui.limpiarCampos();
            BaseDatos.abrirBase();
            BaseDatos.openTransaction();
            if (tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 1).equals("Ambo")) {
                ambo = Ambo.findFirst("id = ?", tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 0));
                articuloGui.CargarCampos(ambo);
            } else {
                articulo = Articulo.findFirst("id = ?", tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 0));
                articuloGui.CargarCampos(articulo);
            }
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
        }
    }

    private void actualizarLista() throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        tablaArtDefault.setRowCount(0);
        Iterator<Articulo> it = listArticulos.iterator();
        while (it.hasNext()) {
            Articulo art = it.next();
            Object row[] = new String[7];
            row[0] = art.getString("id");
            row[1] = art.getString("tipo");
            row[2] = art.getString("modelo");
            row[3] = art.getString("marca");
            row[4] = art.getString("talle");
            row[5] = art.getString("stock");
            row[6] = art.getBigDecimal("precio_alquiler").setScale(2, RoundingMode.CEILING).toString();
            tablaArtDefault.addRow(row);
        }
        Iterator<Ambo> it2 = listAmbo.iterator();
        while (it2.hasNext()) {
            Ambo art = it2.next();
            Object row[] = new String[7];
            row[0] = art.getString("id");
            row[1] = "Ambo";
            row[2] = art.getString("nombre");
            row[3] = art.getString("marca");
            row[4] = art.getString("talle");
            row[5] = art.getString("stock");
            row[6] = art.getBigDecimal("precio_alquiler").setScale(2, RoundingMode.CEILING).toString();
            tablaArtDefault.addRow(row);
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        articuloGui.getCantidadArticulos().setText(String.valueOf(tablaArticulos.getRowCount()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == articuloGui.getNuevo()) {
            System.out.println("Boton nuevo pulsado");
            articuloGui.limpiarCampos();
            articuloGui.habilitarCampos(true);
            isNuevo = true;
            editandoInfo = true;
            articuloGui.getBorrar().setEnabled(false);
            articuloGui.getModificar().setEnabled(false);
            articuloGui.getGuardar().setEnabled(true);
        }
        if (e.getSource() == articuloGui.getGuardar() && editandoInfo && isNuevo) {
            System.out.println("Boton guardar pulsado");
            if (articuloGui.getTipo().getSelectedItem().toString().equals("Ambo")) {
                JOptionPane.showMessageDialog(articuloGui, "No se puede crear un ambo desde esta venta, por favor precione el boton Registrar Ambo", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    if (cargarDatosProd(articulo)) {
                        if (abmArticulo.alta(articulo)) {
                            articuloGui.habilitarCampos(false);
                            articuloGui.limpiarCampos();
                            editandoInfo = false;
                            JOptionPane.showMessageDialog(articuloGui, "¡Artículo guardado exitosamente!");
                            articuloGui.getNuevo().setEnabled(true);
                            articuloGui.getGuardar().setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(articuloGui, "codigo repetido, no se guardó el artículo", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                        try {
                            realizarBusqueda();
                        } catch (SQLException ex) {
                            Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == articuloGui.getBorrar()) {
            System.out.println("Boton borrar pulsado");
            articuloGui.habilitarCampos(false);
            if (articuloGui.getTipo().getSelectedItem().toString().equals("Ambo")) {
                if (ambo.getString("id") != null && !editandoInfo) {
                    Integer resp = JOptionPane.showConfirmDialog(articuloGui, "¿Desea borrar el artículo " + articuloGui.getId().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        Boolean seBorro = false;
                        try {
                            seBorro = abmAmbo.baja(ambo);
                        } catch (SQLException ex) {
                            Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (seBorro) {
                            JOptionPane.showMessageDialog(articuloGui, "¡Artículo borrado exitosamente!");
                            articuloGui.limpiarCampos();
                            try {
                                realizarBusqueda();
                            } catch (SQLException ex) {
                                Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            articuloGui.getBorrar().setEnabled(false);
                            articuloGui.getModificar().setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error, no se borró el artículo", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(articuloGui, "No se seleccionó un artículo");
                }
            } else {
                if (articulo.getString("id") != null && !editandoInfo) {
                    Integer resp = JOptionPane.showConfirmDialog(articuloGui, "¿Desea borrar el artículo " + articuloGui.getId().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        Boolean seBorro = false;
                        try {
                            seBorro = abmArticulo.baja(articulo);
                        } catch (SQLException ex) {
                            Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (seBorro) {
                            JOptionPane.showMessageDialog(articuloGui, "¡Artículo borrado exitosamente!");
                            articuloGui.limpiarCampos();
                            try {
                                realizarBusqueda();
                            } catch (SQLException ex) {
                                Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            articuloGui.getBorrar().setEnabled(false);
                            articuloGui.getModificar().setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error, no se borró el artículo", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(articuloGui, "No se seleccionó un artículo");
                }
            }
        }
        if (e.getSource() == articuloGui.getModificar()) {
            System.out.println("Boton modificar pulsado");
            articuloGui.habilitarCampos(true);
            articuloGui.getTipo().setEnabled(false);
            articuloGui.getId().setEnabled(false);
            editandoInfo = true;
            isNuevo = false;
            articuloGui.getBorrar().setEnabled(false);
            articuloGui.getGuardar().setEnabled(true);
            articuloGui.getModificar().setEnabled(false);
            if (articuloGui.getTipo().getSelectedItem().equals("Ambo")) {
                articuloGui.getStock().setEnabled(false);
            }
        }
        if (e.getSource() == articuloGui.getGuardar() && editandoInfo && !isNuevo) {
            if (articuloGui.getTipo().getSelectedItem().toString().equals("Ambo")) {
                try {
                    if (cargarDatosProd(ambo)) {
                        if (abmAmbo.modificar(ambo)) {
                            articuloGui.habilitarCampos(false);
                            articuloGui.limpiarCampos();
                            editandoInfo = false;
                            JOptionPane.showMessageDialog(articuloGui, "¡Artículo modificado exitosamente!");
                            articuloGui.getNuevo().setEnabled(true);
                            articuloGui.getGuardar().setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    if (cargarDatosProd(articulo)) {
                        if (abmArticulo.modificar(articulo)) {
                            int idInt = Integer.valueOf(articulo.getString("id"));
                            if (abmAmbo.revisarStock(idInt)) {
                                articuloGui.habilitarCampos(false);
                                articuloGui.limpiarCampos();
                                editandoInfo = false;
                                JOptionPane.showMessageDialog(articuloGui, "¡Artículo modificado exitosamente!");
                                articuloGui.getNuevo().setEnabled(true);
                                articuloGui.getGuardar().setEnabled(false);
                            } else {
                                JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                        try {
                            realizarBusqueda();
                        } catch (SQLException ex) {
                            Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorArticulo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == articuloGui.getRegistrarAmbo()) {
            System.out.println("clik crear ambo");
            registroAmbo.setVisible(true);
            registroAmbo.toFront();
            articuloGui.hide();
        }
    }

    private boolean cargarDatosProd(Articulo art) throws SQLException {
        boolean ret = true;
        try {
            art.set("id", articuloGui.getId().getText());
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el id", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String marca = articuloGui.getMarca().getText();
            art.set("marca", marca);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en la marca", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        try {
            String modelo = articuloGui.getModelo().getText();
            art.set("modelo", modelo);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el modelo", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        try {
            String desc = articuloGui.getDescripcion().getText();
            art.set("descripcion", desc);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en la descripcion", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Float precioCompra = Float.valueOf(articuloGui.getPrecioCompra().getText());
            art.set("precio_compra", precioCompra);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en precio de compra", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Float precioAlquiler = Float.valueOf(articuloGui.getPrecioAlquiler().getText());
            art.set("precio_alquiler", precioAlquiler);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en precio de alquiler", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            int stock = Integer.valueOf(articuloGui.getStock().getText());
            art.set("stock", stock);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el stock", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String tipo = articuloGui.getTipo().getSelectedItem().toString();
            art.set("tipo", tipo);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el tipo", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String talle = articuloGui.getTalle().getText();
            art.set("talle", talle);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el talle", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }

    private boolean cargarDatosProd(Ambo art) throws SQLException {
        boolean ret = true;
        try {
            art.set("id", articuloGui.getId().getText());
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el id", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String marca = articuloGui.getMarca().getText();
            art.set("marca", marca);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en la marca", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        try {
            String modelo = articuloGui.getModelo().getText();
            art.set("nombre", modelo);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el modelo", "Error!", JOptionPane.ERROR_MESSAGE);
        }

        try {
            String desc = articuloGui.getDescripcion().getText();
            art.set("descripcion", desc);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en la descripcion", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Float precioAlquiler = Float.valueOf(articuloGui.getPrecioAlquiler().getText());
            art.set("precio_alquiler", precioAlquiler);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en precio de alquiler", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            int stock = Integer.valueOf(articuloGui.getStock().getText());
            art.set("stock", stock);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el stock", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String talle = articuloGui.getTalle().getText();
            art.set("talle", talle);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el talle", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }

    @Override
    public void focusGained(FocusEvent fe) {
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if (fe.getSource() == articuloGui.getPrecioCompra()) {
            System.out.println("perdi el foco de precio compra");
        }
    }
}
