/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import abm.ABMReserva;
import busqueda.Busqueda;
import interfaz.RemitoGui;
import interfaz.ReservaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Cliente;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class ControladorReserva implements ActionListener {

    private ABMReserva abmReserva; //abm de una reserva
    private final ReservaGui reservaGui; // gui de una reserva
    private Reserva reserva; // modelo de reserva
    private final Busqueda busqueda; //busquedas de clientes
    private final boolean isNuevaReserva; //si la reserva es nueva, o es una que se modificara
    private String fechaReserva; //fecha en que se realiza la reserva
    private String fechaEntregaReserva; //fecha en que se debe entregar la reserva
    private Integer idCliente; //ID del cliente que realizo la reserva

    public ControladorReserva(ReservaGui reservaGui, Reserva r) throws SQLException {
        this.reservaGui = reservaGui;
        this.busqueda = new Busqueda();
        this.reservaGui.setActionListener(this);
        // si r es distinto de null, tenemos una reserva a modificar
        if (r != null) {
            this.isNuevaReserva = false; //la reserva no es nueva
            this.reserva = r; // le asigno a la reserva, que pasada por parametro para modificar
            this.abmReserva = new ABMReserva();
            this.idCliente = (Integer)reserva.get("id_cliente");
            cargarReserva(this.reserva); //cargo la reserva en la gui
            // sino creamos una nueva reserva
        } else {
            this.isNuevaReserva = true; // la reserva es nueva
            this.fechaEntregaReserva = null;
            this.fechaReserva = null;
            this.idCliente = null;
            this.reserva = new Reserva(); // creo un modelo nuevo de reserva
            this.abmReserva = new ABMReserva();
        }
        //escucho en el JText lo que se va ingresando para buscar un cliente
        this.reservaGui.getBusquedaCliente().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                List<Cliente> listaClientes;
                try {
                    listaClientes = busquedaClientes(evt); //busco los clientes
                    if (listaClientes != null) { //si hay clientes los cargo en la gui
                        actualizarTablaClientes(listaClientes);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        //reviso si se clickea alguna fila de la tabla
        this.reservaGui.getTablaClienteReserva().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt); //si se clickea alguna fila, saco el id del cliente seleccionado
            }
        });
    }

    /*Saca el id del cliente seleccionado en la tabla de busqueda de clientes
     * y setea el JText de busqueda con el cliente seleccionado
     */
    private void tablaMouseClicked(MouseEvent evt) {
        int selectedRow = reservaGui.getTablaClienteReserva().getSelectedRow();
        DefaultTableModel modelo = ((DefaultTableModel) reservaGui.getTablaClienteReserva().getModel());
        reservaGui.getBusquedaCliente().setText(modelo.getValueAt(selectedRow, 0) + " - "
                + modelo.getValueAt(selectedRow, 1) + "  " + modelo.getValueAt(selectedRow, 2));
        idCliente = (Integer) modelo.getValueAt(selectedRow, 0);
    }

    //Busco los clientes a partir de los datos ingresados en el JText de busqueda
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

    //Carga la lista de clientes encontrados en la tabla de busqueda de clientes
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

    //Carga una reserva en la gui
    private void cargarReserva(Reserva r) throws SQLException {
        Cliente c = busqueda.buscarCliente(r.get("id_cliente"));
        reservaGui.setBusquedaCliente(c.getId() + " - " + c.getString("nombre") + " " + c.getString("dni"));
        Date dateFR = r.getDate("fecha_reserva");
        Date dateFER = r.getDate("fecha_entrega_reserva");
        reservaGui.setFechaReserva(dateFR);
        reservaGui.setFechaEntregaReserva(dateFER);
        // FALTA CARGAR los articulos
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*Si presiono el boton de agregar una reserva, y ademas la reserva es nueva,
         * la doy de alta en la base de datos.
         */
        if (e.getSource().equals(reservaGui.getConfirmarReserva()) && isNuevaReserva) {
            fechaReserva = reservaGui.getFechaReserva();
            fechaEntregaReserva = reservaGui.getFechaEntregaReserva();
            if (idCliente != null && fechaEntregaReserva != null && fechaReserva != null) {
                reserva.set("fecha_reserva", fechaReserva);
                reserva.set("fecha_entrega_reserva", fechaEntregaReserva);
                reserva.set("id_cliente", idCliente);
                try {
                    abmReserva.alta(reserva);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(reservaGui, "La Reserva ha sido realizada con éxito!.");
            } else {
                JOptionPane.showMessageDialog(reservaGui, "La informacion es insuficiente o erronea, por favor complete todos los campos.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        /*Si presiono el boton de agregar una reserva, y no es una reserva nueva(reserva a modifcar),
         * la modifico en la base de datos.
         */
        if (e.getSource().equals(reservaGui.getConfirmarReserva()) && !isNuevaReserva) {
            fechaReserva = reservaGui.getFechaReserva();
            fechaEntregaReserva = reservaGui.getFechaEntregaReserva();
            if (idCliente != null && fechaEntregaReserva != null && fechaReserva != null) {
                this.reserva.set("fecha_reserva", fechaReserva);
                this.reserva.set("fecha_entrega_reserva", fechaEntregaReserva);
                this.reserva.set("id_cliente", idCliente);
                try {
                    abmReserva.modificar(reserva);
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
