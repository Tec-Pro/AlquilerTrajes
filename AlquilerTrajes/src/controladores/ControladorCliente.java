    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMCliente;
import busqueda.Busqueda;
import interfaz.AplicacionGui;
import interfaz.ClienteGui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelos.Cliente;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ControladorCliente implements ActionListener {

    private JTextField nomcli;
    private ClienteGui clienteGui;
    private AplicacionGui aplicacionGui;
    private DefaultTableModel tablaCliDefault;
    private java.util.List<Cliente> listClientes;
    private JTable tablaCliente;   
    private ABMCliente abmCliente;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Cliente cliente;
    private JComboBox ver;
    Busqueda busqueda;
    private Color Color;
   

    public ControladorCliente(ClienteGui clienteGui, AplicacionGui aplicacionGui) {
            this.aplicacionGui = aplicacionGui;
            this.clienteGui = clienteGui;
            this.clienteGui.setActionListener(this);
            isNuevo = true;
            editandoInfo = false;
            busqueda = new Busqueda();
            tablaCliDefault = clienteGui.getClientes();
            tablaCliente = clienteGui.getTablaClientes();
            listClientes = new LinkedList();
            abmCliente = new ABMCliente();
            cliente = new Cliente();          
            Base.openTransaction();
            listClientes = Cliente.findAll();
            Base.commitTransaction();
            actualizarLista();          
            nomcli = clienteGui.getBusqueda();
            nomcli.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    busquedaKeyReleased(evt);
                }
            });
            tablaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tablaClienteMouseClicked(evt);
                }
            });                  
    }

    private void busquedaKeyReleased(KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

      // Carga todos los clientes
    public void cargarTodos() {
        Base.openTransaction();
     
        listClientes = Cliente.findAll();
                Base.commitTransaction();

        if (!listClientes.isEmpty()) {
            realizarBusqueda();
        }

    }

    // doble click en un cliente = muestra los datos y habilita los botones
    private void tablaClienteMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            clienteGui.habilitarCampos(false);
            clienteGui.getBorrar().setEnabled(true);
            clienteGui.getModificar().setEnabled(true);
            clienteGui.getGuardar().setEnabled(false);
            clienteGui.getNuevo().setEnabled(true);          
            System.out.println("hice doble click en un cliente");
            clienteGui.limpiarCampos();
            cliente = busqueda.buscarCliente(tablaCliente.getValueAt(tablaCliente.getSelectedRow(), 0));
            clienteGui.CargarCampos(cliente);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clienteGui.getNuevo()) { //crear un nuevo cliente  
            System.out.println("Boton nuevo pulsado");
            clienteGui.limpiarCampos();
            clienteGui.habilitarCampos(true);
            isNuevo = true;
            editandoInfo = true;
            clienteGui.getBorrar().setEnabled(false);           
            clienteGui.getModificar().setEnabled(false);
            clienteGui.getGuardar().setEnabled(true);
        }
        if (e.getSource() == clienteGui.getGuardar() && editandoInfo && isNuevo) { //Guardar
            System.out.println("Boton guardar pulsado");

            if (cargarDatosCliente(cliente)) {
                if (abmCliente.alta(cliente)) {
                    clienteGui.habilitarCampos(false);
                    clienteGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(clienteGui, "¡Cliente guardado exitosamente!");
                    clienteGui.getNuevo().setEnabled(true);
                    clienteGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(clienteGui, "Ocurrió un error, revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                realizarBusqueda();
            }


        }
        if (e.getSource() == clienteGui.getBorrar()) { //borrar cliente 
            System.out.println("Boton borrar pulsado");
            clienteGui.habilitarCampos(false);
            if (cliente.getString("id") != null && !editandoInfo) {
                Integer resp = JOptionPane.showConfirmDialog(clienteGui, "¿Desea borrar el cliente " + clienteGui.getNombre().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    Boolean seBorro = abmCliente.baja(cliente);
                    if (seBorro) {
                        JOptionPane.showMessageDialog(clienteGui, "¡Cliente borrado exitosamente!");
                        clienteGui.limpiarCampos();
                        realizarBusqueda();
                        clienteGui.getBorrar().setEnabled(false);
                        clienteGui.getModificar().setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(clienteGui, "Ocurrió un error, no se borró el cliente", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(clienteGui, "No se seleccionó un cliente");
            }

        }
        if (e.getSource() == clienteGui.getModificar()) { //modificar cliente
            System.out.println("Boton modificar pulsado");
            clienteGui.habilitarCampos(true);
            editandoInfo = true;
            isNuevo = false;
            clienteGui.getBorrar().setEnabled(false);
            clienteGui.getGuardar().setEnabled(true);
            clienteGui.getModificar().setEnabled(false);        
        }

        if (e.getSource() == clienteGui.getGuardar() && editandoInfo && !isNuevo) {
            System.out.println("Boton guardar pulsado");

            if (cargarDatosCliente(cliente)) {
                if (abmCliente.modificar(cliente)) {
                    clienteGui.habilitarCampos(false);
                    clienteGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(clienteGui, "¡Cliente modificado exitosamente!");
                    clienteGui.getNuevo().setEnabled(true);
                    clienteGui.getGuardar().setEnabled(false);                    
                } else {
                    JOptionPane.showMessageDialog(clienteGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                realizarBusqueda();
            }

        }        
    }

    private void actualizarLista() {

        tablaCliDefault.setRowCount(0);
        Iterator<Cliente> it = listClientes.iterator();
        while (it.hasNext()) {
            Cliente c = it.next();
            Object row[] = new String[6];
            row[0] = c.getString("id");
            row[1] = c.getString("nombre");
            row[2] = c.getString("telefono");
            row[3] = c.getString("celular");
            tablaCliDefault.addRow(row);

        }
    }

    private void realizarBusqueda() {
        listClientes = busqueda.buscarCliente(nomcli.getText());
        actualizarLista();
    }

    private boolean cargarDatosCliente(Cliente c) {
        boolean ret = true;
        try {
            String nombre = TratamientoString.eliminarTildes(clienteGui.getNombre().getText());
            System.out.println(nombre);
            c.set("nombre", nombre);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(clienteGui, "Error en el nombre", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String telefono = TratamientoString.eliminarTildes(clienteGui.getTelefono().getText());
            c.set("telefono", telefono);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(clienteGui, "Error en el telefono", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String celular = TratamientoString.eliminarTildes(clienteGui.getCelular().getText());
            c.set("celular", celular);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(clienteGui, "Error en el celular", "Error!", JOptionPane.ERROR_MESSAGE);
        }        
        try {
            String direccion = TratamientoString.eliminarTildes(clienteGui.getDireccion().getText());
            c.set("direccion", direccion);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(clienteGui, "Error en el direccion", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String dni = TratamientoString.eliminarTildes(clienteGui.getDni().getText());
            c.set("dni", dni);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(clienteGui, "Error en el dni", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }

}
