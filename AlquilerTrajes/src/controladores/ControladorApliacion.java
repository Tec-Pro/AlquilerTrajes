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

    public ControladorApliacion() throws JRException, ClassNotFoundException, SQLException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "tecpro", "tecpro");
        }
        aplicacionGui = new AplicacionGui();
        aplicacionGui.setCursor(Cursor.WAIT_CURSOR);
        aplicacionGui.setActionListener(this);
        aplicacionGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        articuloGui = new ArticuloGui();
        clienteGui = new ClienteGui();
        bajaGui = new BajaGui();
        VerbajasGui = new VerBajasGui();
        controladorVerBajas = new ControladorVerBajas(VerbajasGui);
        controladorBaja = new ControladorBaja(bajaGui);
        controladorArticulo = new ControladorArticulo(articuloGui);
        controladorCliente = new ControladorCliente(clienteGui);
        aplicacionGui.getContenedor().add(articuloGui);
        aplicacionGui.getContenedor().add(clienteGui);
        aplicacionGui.setCursor(Cursor.DEFAULT_CURSOR);
        aplicacionGui.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException, JRException {
       if (!Base.hasConnection()) {
            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "tecpro", "tecpro");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error, no se realizó la conexión con el servidor, verifique la conexión \n " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(null, "Se cerrará el programa", null, JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
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
    }

    private void abrirBase() {
        if (!Base.hasConnection()) {
            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "tecpro", "tecpro");
                Base.connection().setAutoCommit(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error, no se realizó la conexión con el servidor, verifique la conexión \n " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
