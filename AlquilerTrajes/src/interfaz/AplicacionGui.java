/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

/**
 *
 * @author nico
 */
public class AplicacionGui extends javax.swing.JFrame {

    /**
     * Creates new form AplicacionGui
     */
    public AplicacionGui() {
        initComponents();
    }

    public JDesktopPane getContenedor() {
        return contenedor;
    }

    public JButton getArticulos() {
        return articulos;
    }

    public JButton getReservas() {
        return reservas;
    }

    public JButton getDisponibilidadArticulos() {
        return disponibilidadArticulos;
    }
    
    public void setActionListener(ActionListener lis) {
        this.getArticulos().addActionListener(lis);
        this.clientes.addActionListener(lis);
        this.reservas.addActionListener(lis);
        this.baja.addActionListener(lis);
        this.verBajas.addActionListener(lis);
        this.ganancia.addActionListener(lis);
        this.disponibilidadArticulos.addActionListener(lis);
    }

    public JButton getBaja() {
        return baja;
    }

    public JButton getVerBajas() {
        return verBajas;
    }

    public JButton getGanancia() {
        return ganancia;
    }
          
    public JButton getClientes() {
        return clientes;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new javax.swing.JDesktopPane();
        panelBotones = new javax.swing.JPanel();
        reservas = new javax.swing.JButton();
        disponibilidadArticulos = new javax.swing.JButton();
        articulos = new javax.swing.JButton();
        clientes = new javax.swing.JButton();
        baja = new javax.swing.JButton();
        verBajas = new javax.swing.JButton();
        ganancia = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        salir = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        tecPro = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Alquiler de Trajes Hombre");
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/Icons/logo.png")).getImage());

        panelBotones.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelBotones.setLayout(new java.awt.GridLayout(1, 0));

        reservas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/reservas_y_remitos.png"))); // NOI18N
        reservas.setToolTipText("Gestión de Reservas y Alquileres");
        panelBotones.add(reservas);

        disponibilidadArticulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/disponibilidadDeArticulos.png"))); // NOI18N
        disponibilidadArticulos.setToolTipText("Chequeo de Disponibilidad de Artículos");
        panelBotones.add(disponibilidadArticulos);

        articulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/productos.png"))); // NOI18N
        articulos.setToolTipText("Gestión de artículos");
        panelBotones.add(articulos);

        clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/clients.png"))); // NOI18N
        clientes.setToolTipText("Gestión de clientes");
        panelBotones.add(clientes);

        baja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/darBaja.png"))); // NOI18N
        baja.setToolTipText("Dar de baja");
        panelBotones.add(baja);

        verBajas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/baja.png"))); // NOI18N
        verBajas.setToolTipText("Ver bajas");
        panelBotones.add(verBajas);

        ganancia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/ganancia.png"))); // NOI18N
        ganancia.setToolTipText("Ganancias");
        panelBotones.add(ganancia);

        jMenu1.setText("Archivo");

        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/apagar.png"))); // NOI18N
        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        jMenu1.add(salir);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Acerca de");

        tecPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/Icons/acerca.png"))); // NOI18N
        tecPro.setText("Tec-Pro Software");
        tecPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tecProActionPerformed(evt);
            }
        });
        jMenu3.add(tecPro);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contenedor, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        int ret = JOptionPane.showConfirmDialog(this, "¿Desea salir de la aplicación?", "Cerrar aplicación", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            System.exit(0);
        }

    }//GEN-LAST:event_salirActionPerformed

    private void tecProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tecProActionPerformed
        AcercaDe acercaDe = new AcercaDe(this, true);
        acercaDe.setLocationRelativeTo(this);
        acercaDe.setVisible(true);
    }//GEN-LAST:event_tecProActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton articulos;
    private javax.swing.JButton baja;
    private javax.swing.JButton clientes;
    private javax.swing.JDesktopPane contenedor;
    private javax.swing.JButton disponibilidadArticulos;
    private javax.swing.JButton ganancia;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JButton reservas;
    private javax.swing.JMenuItem salir;
    private javax.swing.JMenuItem tecPro;
    private javax.swing.JButton verBajas;
    // End of variables declaration//GEN-END:variables
}
