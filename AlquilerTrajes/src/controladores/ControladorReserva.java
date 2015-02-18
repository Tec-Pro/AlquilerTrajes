/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import abm.ABMReserva;
import busqueda.Busqueda;
import interfaz.ReservaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import modelos.Cliente;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class ControladorReserva implements ActionListener {

    private final ABMReserva abmReserva;
    private final ReservaGui reservaGui;
    private final Reserva reserva;
    private final Busqueda busqueda;

    public ControladorReserva(ReservaGui reservaGui) {
        this.reservaGui = reservaGui;
        this.reserva = new Reserva();
        this.abmReserva = new ABMReserva();
        this.busqueda = new Busqueda();
        this.reservaGui.setActionListener(this);
        this.reservaGui.getBusquedaCliente().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                List<Cliente> listaClientes;
                try {
                    listaClientes = busquedaClientes(evt);
                    if (listaClientes != null) {
                        actualizarListaClientes(listaClientes);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
    }

    private List<Cliente> busquedaClientes(KeyEvent evt) throws SQLException {
        String cliente = reservaGui.getBusquedaCliente().getText();
        List<Cliente> listClientes;
        //Si la busqueda empieza con un numero, busco al/los clientes por el id
        if (cliente.startsWith("1") || cliente.startsWith("2") || cliente.startsWith("3") || cliente.startsWith("4") || 
                cliente.startsWith("5") || cliente.startsWith("6") || cliente.startsWith("7") || cliente.startsWith("8") || 
                cliente.startsWith("9") ){
            listClientes = busqueda.buscarClientes(Integer.parseInt(cliente));
        //Sino, busco los clientes por su nombre
        }else{
            listClientes = busqueda.buscarCliente(cliente);
        }   
        return listClientes;
    }

    private void actualizarListaClientes(List<Cliente> lista) throws SQLException {
        DefaultListModel modelo = new DefaultListModel();
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Iterator<Cliente> itr = lista.iterator();
        while (itr.hasNext()) {
            Cliente c = itr.next();
            modelo.addElement(c.getId() + " " + c.getString("nombre"));
        }
        reservaGui.getListClientes().setModel(modelo);
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(reservaGui.getConfirmarReserva())) {
            if (!"".equals(reservaGui.getFechaReserva()) && reservaGui.getFechaEntregaReserva() != "" && reservaGui.getBusquedaCliente().getText() != "") {
                System.out.println(reservaGui.getFechaReserva() + "  " + reservaGui.getFechaEntregaReserva()
                        + "  " + reservaGui.getBusquedaCliente());
                reserva.set("fecha_reserva", reservaGui.getFechaReserva());
                reserva.set("fecha_entrega_reserva", reservaGui.getFechaEntregaReserva());
                reserva.set("id_cliente", reservaGui.getBusquedaCliente());
                try {
                    abmReserva.alta(reserva);
                    //}
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
