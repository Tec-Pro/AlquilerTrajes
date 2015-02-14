/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMAmbo;
import interfaz.RegistroAmboGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import org.javalite.activejdbc.Base;

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
   

    public ControladorRegistroAmbo(RegistroAmboGui registroAmboGui) {
        this.registroAmboGui = registroAmboGui;
        this.registroAmboGui.setActionListener(this);
        tablaArtDefault = registroAmboGui.getTablaArticulosDefault();
        tablaArticulos = registroAmboGui.getTablaArticulos();
        tablaAmboDefault = registroAmboGui.getTablaFacturaDefault();
        tablaAmbo = registroAmboGui.getTablaFactura();
        Base.openTransaction();
        listArticulos = Articulo.findAll();
        Base.commitTransaction();
        actualizarLista();
        this.registroAmboGui.getBusquedaCodigoArticulo().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
        tablaArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        
    }
    
        public void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            //Agregar el row con el articulo seleccionado
        }
    }
    
    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) {
        realizarBusqueda();
    }

    
    private void realizarBusqueda() {
        Base.openTransaction();
        listArticulos = Articulo.where("(modelo like ? or descripcion like ? or marca like ? or id like ? or tipo like ? or talle like ?)", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%", "%" + registroAmboGui.getBusquedaCodigoArticulo().getText() + "%");
        Base.openTransaction();
        actualizarLista();
    }
        
    
     private void actualizarLista() {
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
     }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
