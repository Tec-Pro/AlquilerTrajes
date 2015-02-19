/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import abm.ABMReserva;
import busqueda.Busqueda;
import com.toedter.calendar.JDateChooser;
import interfaz.ReservaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelos.Cliente;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class ControladorReserva implements ActionListener {

    private  ABMReserva abmReserva;
    private final ReservaGui reservaGui;
    private  Reserva reserva;
    private final Busqueda busqueda;
    private final boolean isNuevaReserva;
    private String fechaReserva; //fecha en que se realiza la reserva
    private String fechaEntregaReserva; //fecha en que se debe entregar la reserva
    private Integer idCliente; //ID del cliente que realizo la reserva

    public ControladorReserva(ReservaGui reservaGui, Reserva r) throws SQLException {
        this.reservaGui = reservaGui;
        this.busqueda = new Busqueda();
        this.reservaGui.setActionListener(this);
        if(r!= null){
            this.isNuevaReserva = false;
            this.reserva = r;
            cargarReserva(this.reserva);
        }else{
            this.isNuevaReserva = true;
            this.fechaEntregaReserva = null;
            this.fechaReserva = null;
            this.idCliente = null;
            this.reserva = new Reserva();
            this.abmReserva = new ABMReserva();
        }
        this.reservaGui.getBusquedaCliente().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                List<Cliente> listaClientes;
                try {
                    listaClientes = busquedaClientes(evt);
                    if (listaClientes != null) {
                        actualizarTablaClientes(listaClientes);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        reservaGui.getTablaClienteReserva().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
    }

    private void tablaMouseClicked(MouseEvent evt) {
        int selectedRow = reservaGui.getTablaClienteReserva().getSelectedRow();
        DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaClienteReserva().getModel());
        reservaGui.getBusquedaCliente().setText(modelo.getValueAt(selectedRow, 0) + " - "
                + modelo.getValueAt(selectedRow, 1) + "  " + modelo.getValueAt(selectedRow, 2));
        idCliente = (Integer) modelo.getValueAt(selectedRow, 0);
    }

    private List<Cliente> busquedaClientes(KeyEvent evt) throws SQLException {
        String textBusquedaCliente = reservaGui.getBusquedaCliente().getText();
        List<Cliente> listClientes;
        //Si la busqueda empieza con un numero, busco al/los clientes por el id y dni simultaneamente
        if (textBusquedaCliente.startsWith("0") || textBusquedaCliente.startsWith("1") || textBusquedaCliente.startsWith("2") || textBusquedaCliente.startsWith("3") || textBusquedaCliente.startsWith("4")
                || textBusquedaCliente.startsWith("5") || textBusquedaCliente.startsWith("6") || textBusquedaCliente.startsWith("7") || textBusquedaCliente.startsWith("8")
                || textBusquedaCliente.startsWith("9")) {
            listClientes = busqueda.buscarClientesPorIDyDni(Integer.parseInt(textBusquedaCliente));

            //Sino, busco los clientes por su nombre
        } else {
            listClientes = busqueda.buscarCliente(textBusquedaCliente);
        }
        return listClientes;
    }

    private void actualizarTablaClientes(List<Cliente> lista) throws SQLException {
        DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaClienteReserva().getModel());
        modelo.setRowCount(0);
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Iterator<Cliente> itr = lista.iterator();
        Cliente c;
        Object[] o = new Object[3];
        while (itr.hasNext()) {
            c = itr.next();
            o[0] = (c.getId());
            o[1] = (c.getString("nombre"));
            o[2] = (c.getString("dni"));
            modelo.addRow(o);

        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    private void cargarReserva(Reserva r) throws SQLException {
        Cliente c = busqueda.buscarCliente(r.get("id_cliente"));
        reservaGui.setBusquedaCliente(c.getId()+" - "+c.getString("nombre")+" "+c.getString("dni"));
        // FALTA CARGAR EL RESTO DE LOS DATOS DE LA RESERVA PASADA POR
        // PARAMETRO EN LA GUI
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(reservaGui.getConfirmarReserva()) && isNuevaReserva) {
            fechaReserva = reservaGui.getFechaReserva();
            fechaEntregaReserva = reservaGui.getFechaEntregaReserva();
            if (idCliente != null && fechaEntregaReserva != null && fechaReserva != null) {
                reserva.set("fecha_reserva", fechaReserva);
                reserva.set("fecha_entrega_reserva", fechaEntregaReserva);
                reserva.set("id_cliente", idCliente);
                try {
                    abmReserva.alta(reserva);
                    //}
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(reservaGui, "La Reserva ha sido realizada con éxito!.");
            } else {
                JOptionPane.showMessageDialog(reservaGui, "La informacion es insuficiente o erronea, por favor complete todos los campos.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
