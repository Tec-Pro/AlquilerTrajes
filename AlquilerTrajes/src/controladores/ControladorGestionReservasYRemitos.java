/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import busqueda.Busqueda;
import busqueda.BusquedaReserva;
import interfaz.GestionReservasGui;
import interfaz.RemitoGui;
import interfaz.ReservaGui;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelos.Cliente;
import modelos.Remito;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class ControladorGestionReservasYRemitos implements ActionListener {

    private final GestionReservasGui gestionReservasGui; //gui de la gestion de reservas y facturas
    private final RemitoGui remitoGui; //gui de la gestion de remitos
    private final ReservaGui reservaGui; // gui de una reserva
    private ControladorReserva controladorReserva; //controlador de la gui de una reserva
    private ControladorRemito controladorRemito; //controlador de la gui de un remito
    private final Busqueda busqueda;
    private final BusquedaReserva busquedaReserva;
    private Reserva reserva; //Resultado de la busqueda en la tabla de reservas
    private Remito remito; //Resultado de la busqueda en la tabla de remitos
    

    public ControladorGestionReservasYRemitos(GestionReservasGui gestionReservasGui, ReservaGui resGui, RemitoGui remGui) throws SQLException {
        this.gestionReservasGui = gestionReservasGui;
        this.reservaGui = resGui;
        this.remitoGui = remGui;
        this.reserva = null;
        this.remito = null;
        this.gestionReservasGui.setActionListener(this);
        this.busqueda = new Busqueda();
        this.busquedaReserva = new BusquedaReserva();
        //escucho en el JText lo que se va ingresando para buscar una Reserva
        this.gestionReservasGui.getjTxtBuscarReserva().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                List<Reserva> listaReservas;
                try {
                    listaReservas = busquedaReservas(evt); //busco las reservas
                    if (listaReservas != null) { //si hay reservas los cargo en la gui
                        actualizarTablaReservas(listaReservas);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorGestionReservasYRemitos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        //reviso si se clickea alguna fila de la tabla Buscar Reservas
        this.gestionReservasGui.getTablaBuscarReserva().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    tablaBuscarReservaMouseClicked(evt); //si se clickea alguna fila, saco el id de la reserva seleccionada
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorGestionReservasYRemitos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /*Saca el id de la reserva seleccionado en la tabla de busqueda de Reservas
     * y setea el JText de busqueda con la reserva seleccionada
     */
    private void tablaBuscarReservaMouseClicked(MouseEvent evt) throws SQLException {
        int selectedRow = gestionReservasGui.getTablaBuscarReserva().getSelectedRow();
        DefaultTableModel modelo = ((DefaultTableModel) gestionReservasGui.getTablaBuscarReserva().getModel());
        gestionReservasGui.getjTxtBuscarReserva().setText("ID: " + modelo.getValueAt(selectedRow, 0) + " - Fecha de Entrega y Cliente:  "
                + modelo.getValueAt(selectedRow, 2) + " " + modelo.getValueAt(selectedRow, 3));
        this.reserva = busquedaReserva.buscarReserva(modelo.getValueAt(selectedRow, 0));
    }

    //Busco los clientes a partir de los datos ingresados en el JText de busqueda
    private List<Reserva> busquedaReservas(KeyEvent evt) throws SQLException {
        String textBusquedaReserva = gestionReservasGui.getjTxtBuscarReserva().getText();
        List<Reserva> listReservas;
        //Si la busqueda empieza con un numero, busco al/las reservas por su fecha de entrega
        if (textBusquedaReserva.startsWith("0") || textBusquedaReserva.startsWith("1") || textBusquedaReserva.startsWith("2") || textBusquedaReserva.startsWith("3") || textBusquedaReserva.startsWith("4")
                || textBusquedaReserva.startsWith("5") || textBusquedaReserva.startsWith("6") || textBusquedaReserva.startsWith("7") || textBusquedaReserva.startsWith("8")
                || textBusquedaReserva.startsWith("9")) {

            listReservas = busquedaReserva.buscarReservaPorFechaEntrega(textBusquedaReserva);

            //Sino, busco los clientes por su nombre
        } else {
            List<Cliente> listClientes = busqueda.buscarCliente(textBusquedaReserva);
            listReservas = new LinkedList<>();
            BaseDatos.abrirBase();
            BaseDatos.openTransaction();
            Iterator<Cliente> itrC = listClientes.iterator();
            while(itrC.hasNext()){
                List<Reserva> listAux = Reserva.where("id_cliente like ?", "%" +((Integer) itrC.next().getId())+ "%");
                listReservas.addAll(listAux);
            }
            BaseDatos.commitTransaction();
            BaseDatos.close();
        }
        return listReservas;
    }

    //Carga la lista de reservas encontrados en la tabla de busqueda reservas
    private void actualizarTablaReservas(List listaReservas) throws SQLException {
        DefaultTableModel modelo = ((DefaultTableModel) gestionReservasGui.getTablaBuscarReserva().getModel());
        modelo.setRowCount(0);
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Iterator<Reserva> itr = listaReservas.iterator();
        Reserva r;
        Cliente c;
        Object[] o = new Object[4];
        while (itr.hasNext()) {
            r = itr.next();
            o[0] = (r.getId());
            o[1] = (r.getString("fecha_reserva"));
            o[2] = (r.getString("fecha_entrega_reserva"));
            c = Cliente.findById(r.get("id_cliente"));
            o[3] = (c.get("nombre"));
            modelo.addRow(o);

        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Si presiono el boton de crear una Nueva Reserva
        if (ae.getSource() == gestionReservasGui.getBttnNuevaReserva()) {
            try {
                this.controladorReserva = new ControladorReserva(reservaGui, null);
                reservaGui.limpiarComponentes();
                reservaGui.setVisible(true);
                reservaGui.toFront();
                reservaGui.setMaximum(true);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorGestionReservasYRemitos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControladorGestionReservasYRemitos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        //Si presiono el boton de crear un Nuevo Remito
        if (ae.getSource() == gestionReservasGui.getBttnNuevoRemito()) {
            try {    
                this.controladorRemito = new ControladorRemito(remitoGui, null);
                remitoGui.setVisible(true);
                remitoGui.toFront();
                remitoGui.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControladorGestionReservasYRemitos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        //Si presiono el boton de Aceptar de una reserva encontrada
        if (ae.getSource() == gestionReservasGui.getBttnReservaEncontrada() && this.reserva != null) {
            try {
                this.controladorReserva = new ControladorReserva(reservaGui, this.reserva);
                reservaGui.setVisible(true);
                reservaGui.toFront();
                reservaGui.setMaximum(true);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorGestionReservasYRemitos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControladorGestionReservasYRemitos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
