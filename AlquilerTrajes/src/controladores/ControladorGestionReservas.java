/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfaz.GestionReservasGui;
import interfaz.ReservaGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class ControladorGestionReservas implements ActionListener{
    
    private final GestionReservasGui gestionReservasGui;
    private final ReservaGui reservaGui;
    private ControladorReserva controladorReserva;

    public ControladorGestionReservas(GestionReservasGui gestionReservasGui, ReservaGui resGui) throws SQLException {
        this.gestionReservasGui = gestionReservasGui;
        this.reservaGui = resGui;
        this.gestionReservasGui.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == gestionReservasGui.getBttnNuevaReserva()) {
            try {
                this.controladorReserva = new ControladorReserva(reservaGui, null);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorGestionReservas.class.getName()).log(Level.SEVERE, null, ex);
            }
            reservaGui.setVisible(true);
            reservaGui.toFront();
            try {
                reservaGui.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControladorGestionReservas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
