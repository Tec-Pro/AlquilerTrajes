/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfaz.AplicacionGui;
import interfaz.ArticuloGui;
import interfaz.BajaGui;
import interfaz.ClienteGui;
import interfaz.GestionReservasGui;
import interfaz.RegistroAmboGui;
import interfaz.ReservaGui;
import interfaz.VerBajasGui;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author nico
 */
public class ControladorApliacion implements ActionListener {

    private final AplicacionGui aplicacionGui;
    private final ArticuloGui articuloGui;
    private final ControladorArticulo controladorArticulo;
    private final ClienteGui clienteGui;
    private final ControladorCliente controladorCliente;
    private final ControladorGestionReservas controladorGestReserv;
    private File archivoBackup;
    private int selecEnviarBack = 0;
    private final ControladorBaja controladorBaja;
    private final BajaGui bajaGui;
    private final ControladorVerBajas controladorVerBajas;
    private final VerBajasGui VerbajasGui;
    private final RegistroAmboGui registroAmbo;
    private final GestionReservasGui gestResGui;
    private final ReservaGui reservaGui;
    private final ControladorRegistroAmbo controladorRegistroAmbo;
    

    public ControladorApliacion() throws JRException, ClassNotFoundException, SQLException, PropertyVetoException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        aplicacionGui = new AplicacionGui();
        aplicacionGui.setCursor(Cursor.WAIT_CURSOR);
        aplicacionGui.setActionListener(this);
        aplicacionGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        articuloGui = new ArticuloGui();
        clienteGui = new ClienteGui();
        bajaGui = new BajaGui();
        registroAmbo = new RegistroAmboGui();
        VerbajasGui = new VerBajasGui();
        gestResGui = new GestionReservasGui();
        reservaGui = new ReservaGui();
        controladorVerBajas = new ControladorVerBajas(VerbajasGui);
        controladorBaja = new ControladorBaja(bajaGui);
        controladorArticulo = new ControladorArticulo(articuloGui, registroAmbo);
        controladorCliente = new ControladorCliente(clienteGui);
        controladorGestReserv = new ControladorGestionReservas(gestResGui,reservaGui);
        controladorRegistroAmbo = new ControladorRegistroAmbo(registroAmbo);
        aplicacionGui.getContenedor().add(articuloGui);
        aplicacionGui.getContenedor().add(clienteGui);
        aplicacionGui.getContenedor().add(bajaGui);
        aplicacionGui.getContenedor().add(VerbajasGui);
        aplicacionGui.getContenedor().add(registroAmbo);
        aplicacionGui.getContenedor().add(gestResGui);
        aplicacionGui.getContenedor().add(reservaGui);
        aplicacionGui.setCursor(Cursor.DEFAULT_CURSOR);
        aplicacionGui.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException, JRException, PropertyVetoException {
        ControladorApliacion controladorAplicacion = new ControladorApliacion();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == aplicacionGui.getArticulos()) {
            articuloGui.setVisible(true);
            articuloGui.toFront();
        }

        if (ae.getSource() == aplicacionGui.getClientes()) {
            clienteGui.setVisible(true);
            clienteGui.toFront();
        }
        if (ae.getSource() == aplicacionGui.getBaja()) {
            bajaGui.setVisible(true);
            bajaGui.toFront();
        }
        if (ae.getSource() == aplicacionGui.getVerBajas()) {
            VerbajasGui.setVisible(true);
            VerbajasGui.toFront();
        }
        if (ae.getSource() == aplicacionGui.getReservas()) {
            try {
                gestResGui.limpiarComponentes();
                gestResGui.setVisible(true);
                gestResGui.toFront();
                gestResGui.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControladorApliacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
