/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;


import interfaz.AplicacionGui;
import interfaz.ArticuloGui;
import interfaz.ClienteGui;
import interfaz.VentaGui;
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
    private VentaGui ventaGui;
    
    public ControladorApliacion() throws JRException, ClassNotFoundException, SQLException {
       
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/cacique", "tecpro", "tecpro");
        }
        aplicacionGui = new AplicacionGui();
        aplicacionGui.setCursor(Cursor.WAIT_CURSOR);
        aplicacionGui.setActionListener(this);
        aplicacionGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        articuloGui = new ArticuloGui();       
        clienteGui = new ClienteGui();
        ventaGui = new VentaGui();
        controladorArticulo = new ControladorArticulo(articuloGui);
        controladorCliente = new ControladorCliente(clienteGui, aplicacionGui);
        aplicacionGui.getContenedor().add(articuloGui);        
        aplicacionGui.getContenedor().add(clienteGui);
        aplicacionGui.getContenedor().add(ventaGui);
        aplicacionGui.setCursor(Cursor.DEFAULT_CURSOR);
        

    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException, JRException {


        if (!Base.hasConnection()) {
            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/cacique", "tecpro", "tecpro");
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
            controladorArticulo.cargarTodos();
            articuloGui.setVisible(true);
            articuloGui.toFront();
        }
             
        if (ae.getSource() == aplicacionGui.getClientes()) {
            controladorCliente.cargarTodos();
            clienteGui.setVisible(true);
            clienteGui.toFront();
        }      
    }

    private static class SQLFilter extends javax.swing.filechooser.FileFilter {

        final static String sql = "sql";

        public SQLFilter() {
        }

        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 && i < s.length() - 1) {
                String extension = s.substring(i + 1).toLowerCase();
                if (sql.equals(extension)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return "Archivos .sql";
        }
    }
    
        private void abrirBase() {
        if (!Base.hasConnection()) {
            try {
                
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/cacique", "tecpro", "tecpro");
                            Base.connection().setAutoCommit(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error, no se realizó la conexión con el servidor, verifique la conexión \n " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
