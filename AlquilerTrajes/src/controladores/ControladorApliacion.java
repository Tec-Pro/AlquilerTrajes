/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfaz.AplicacionGui;
import interfaz.ArticuloGui;
import interfaz.BajaGui;
import interfaz.ClienteGui;
import interfaz.RegistroAmboGui;
import interfaz.ReservaGui;
import interfaz.VerBajasGui;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ControladorApliacion implements ActionListener {

    private AplicacionGui aplicacionGui;
    private ArticuloGui articuloGui;
    private ControladorArticulo controladorArticulo;
    private ClienteGui clienteGui;
    private ControladorCliente controladorCliente;
    private File archivoBackup;
    private int selecEnviarBack = 0;
    private ControladorBaja controladorBaja;
    private BajaGui bajaGui;
    private ControladorVerBajas controladorVerBajas;
    private VerBajasGui VerbajasGui;
    private RegistroAmboGui registroAmbo;
    private ReservaGui reservaGui;
    private ControladorReserva controladorReserva;

    public ControladorApliacion() throws JRException, ClassNotFoundException, SQLException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        aplicacionGui = new AplicacionGui();
        aplicacionGui.setCursor(Cursor.WAIT_CURSOR);
        aplicacionGui.setActionListener(this);
        aplicacionGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        articuloGui = new ArticuloGui();
        clienteGui = new ClienteGui();
        bajaGui = new BajaGui();
        reservaGui = new ReservaGui();
        registroAmbo = new RegistroAmboGui();
        VerbajasGui = new VerBajasGui();
        controladorVerBajas = new ControladorVerBajas(VerbajasGui);
        controladorBaja = new ControladorBaja(bajaGui);
        controladorArticulo = new ControladorArticulo(articuloGui,registroAmbo);
        controladorCliente = new ControladorCliente(clienteGui);
        controladorReserva = new ControladorReserva(reservaGui);
        
        
        
        
        
        aplicacionGui.getContenedor().add(articuloGui);
        aplicacionGui.getContenedor().add(clienteGui);
        aplicacionGui.getContenedor().add(bajaGui);
        aplicacionGui.getContenedor().add(VerbajasGui);
        aplicacionGui.getContenedor().add(registroAmbo);
        
        aplicacionGui.getContenedor().add(reservaGui);
        
        aplicacionGui.setCursor(Cursor.DEFAULT_CURSOR);
        aplicacionGui.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException, JRException {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "root", "root");
        Base.connection().setAutoCommit(true);
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
            reservaGui.setVisible(true);
            reservaGui.toFront();
        }
    }
}
