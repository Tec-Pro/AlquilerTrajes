/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMReserva;
import interfaz.ReservaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class ControladorReserva implements ActionListener{
    
    
    private final ABMReserva abmReserva;
    private final ReservaGui reservaGui;
    private final Reserva reserva;

    public ControladorReserva(ReservaGui reservaGui) {
        this.reservaGui = reservaGui;
        this.reserva = new Reserva();
        this.abmReserva = new ABMReserva();
        this.reservaGui.setActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Estoy afuera");
        if (e.getSource().equals(reservaGui.getConfirmarReserva())) {
            System.out.println("Entre1");
            //if(reservaGui.getFechaReserva() != "" && reservaGui.getFechaEntregaReserva() != "" 
                //  && reservaGui.getBusquedaCliente() != ""){
                System.out.println("Entre2");
                System.out.println(reservaGui.getFechaReserva() + "  "+reservaGui.getFechaEntregaReserva()
                +  "  "+reservaGui.getBusquedaCliente());
                reserva.set("fecha_reserva", reservaGui.getFechaReserva());
                reserva.set("fecha_entrega_reserva", reservaGui.getFechaEntregaReserva());
                reserva.set("id_cliente", reservaGui.getBusquedaCliente());
                abmReserva.alta(reserva);
            //}
        }
    }
    
    
}
