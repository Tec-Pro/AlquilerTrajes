/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMArticulo;
import interfaz.ArticuloGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ControladorArticulo implements ActionListener, FocusListener {

    private ArticuloGui articuloGui;
    private DefaultTableModel tablaArtDefault;
    private java.util.List<Articulo> listArticulos;
    private JTable tablaArticulos;
    private ABMArticulo abmArticulo;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Articulo articulo;

    public ControladorArticulo(ArticuloGui articuloGui) throws JRException, ClassNotFoundException, SQLException {
        isNuevo = true; //para saber si es nuevo o no
        editandoInfo = false; // se esta editando la info
        articulo = new Articulo();
        this.articuloGui = articuloGui;
        this.articuloGui.setActionListener(this);
        tablaArtDefault = articuloGui.getTablaArticulosDefault();
        tablaArticulos = articuloGui.getArticulos();
        listArticulos = new LinkedList();        
        abmArticulo = new ABMArticulo();
        Base.openTransaction();
        listArticulos = Articulo.findAll();
        Base.commitTransaction();
        actualizarLista();        
        articuloGui.getBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
        articuloGui.getArticulos().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });

    }

    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

    private void realizarBusqueda() {
        Base.openTransaction();
        listArticulos = Articulo.where("es_articulo = 1 and (modelo like ? or descripcion like ? or marca like ? or id like ? or nombre like ? or id like ?)", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%", "%" + articuloGui.getBusqueda().getText() + "%");
        Base.openTransaction();
        actualizarLista();
    }

    public void cargarTodos() {
        Base.openTransaction();
        listArticulos = Articulo.findAll();
        if (!listArticulos.isEmpty()) {
            realizarBusqueda();
            System.out.println("cargue todo");
        }
        Base.commitTransaction(); 
        
    }

    public void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            //bloqueo los campos y habilito botones
            articuloGui.habilitarCampos(false);
            articuloGui.getBorrar().setEnabled(true);
            articuloGui.getModificar().setEnabled(true);
            articuloGui.getGuardar().setEnabled(false);
            articuloGui.getNuevo().setEnabled(true);
            editandoInfo = false;
            articuloGui.limpiarCampos();
            Base.openTransaction();
            articulo = Articulo.findFirst("id = ?", tablaArticulos.getValueAt(tablaArticulos.getSelectedRow(), 0));
            Base.commitTransaction();
            articuloGui.CargarCampos(articulo);
            

        }
    }

    private void actualizarLista() {
        
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
                
                realizarBusqueda();
            }
        }
        if (e.getSource() == articuloGui.getBorrar()) {

            System.out.println("Boton borrar pulsado");
            articuloGui.habilitarCampos(false);
            
            
            if (articulo.getString("id") != null && !editandoInfo) {
                Integer resp = JOptionPane.showConfirmDialog(articuloGui, "¿Desea borrar el artículo " + articuloGui.getId().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    
                    Boolean seBorro = abmArticulo.baja(articulo);
                    
                    if (seBorro) {
                        JOptionPane.showMessageDialog(articuloGui, "¡Artículo borrado exitosamente!");
                        articuloGui.limpiarCampos();
                        realizarBusqueda();
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
        if (e.getSource() == articuloGui.getModificar()) {
            System.out.println("Boton modificar pulsado");
            
            articuloGui.habilitarCampos(true);
            articuloGui.getId().setEnabled(false);
            editandoInfo = true;
            isNuevo = false;
            //articuloGui.getCodigo().setEnabled(false);
            articuloGui.getBorrar().setEnabled(false);
            articuloGui.getGuardar().setEnabled(true);
            articuloGui.getModificar().setEnabled(false);
        }

        if (e.getSource() == articuloGui.getGuardar() && editandoInfo && !isNuevo) {
            System.out.println("Boton guardar pulsado");
            if (cargarDatosProd(articulo)) {
                
                if (abmArticulo.modificar(articulo)) {
                    

                    articuloGui.habilitarCampos(false);
                    articuloGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(articuloGui, "¡Artículo modificado exitosamente!");
                    articuloGui.getNuevo().setEnabled(true);
                    articuloGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(articuloGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                
                realizarBusqueda();
            }
        }
   }

    private boolean cargarDatosProd(Articulo art) {
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
            Double precioCompra = Double.valueOf(articuloGui.getPrecioCompra().getText());
            art.set("precio_compra", BigDecimal.valueOf(precioCompra).setScale(2, RoundingMode.CEILING));
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en precio de compra", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Double precioAlquiler = Double.valueOf(articuloGui.getPrecioAlquiler().getText());
            art.set("precio_alquiler", BigDecimal.valueOf(precioAlquiler).setScale(2, RoundingMode.CEILING));
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en precio de alquiler", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            Float stock= Float.valueOf(articuloGui.getStock().getText());
            art.set("stock", articuloGui.getStock().getText());

                 } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el stock", "Error!", JOptionPane.ERROR_MESSAGE);
        }
          try {
            String tipo =articuloGui.getTipo().getSelectedItem().toString();
            art.set("tipo",tipo);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(articuloGui, "Error en el tipo", "Error!", JOptionPane.ERROR_MESSAGE);
        }
            try {
            String talle = articuloGui.getTalle().getText();
            art.set("precio_alquiler", talle);
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
