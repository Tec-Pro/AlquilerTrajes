/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import abm.ABMArticulo;
import abm.ABMTrabajo;
import interfaz.Trabajos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;
import modelos.Auto;
import modelos.Cliente;
import modelos.Proveedor;
import modelos.Trabajo;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author nico
 */
public class ControladorTrabajo implements ActionListener {

    Trabajos trabajoGui;
    Trabajo trabajo;

    private java.util.List<Auto> listAutos;
    private ABMTrabajo abmTrabajo;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private DefaultTableModel tablaAutosDefault;
    private JTable tablaAutos;
    private Auto auto;
    private Cliente cliente;
    private ControladorJReport reporteTrab;

    public ControladorTrabajo(Trabajos trabajoGui) {
        this.trabajoGui = trabajoGui;
        this.trabajoGui.setActionListener(this);
        isNuevo = true; //para saber si es nuevo o no
        editandoInfo = false; // se esta editando la info
        trabajo = new Trabajo();

        tablaAutosDefault = trabajoGui.getAutosDefault();
        tablaAutos = trabajoGui.getTablaAutos();
        listAutos = new LinkedList();
        abmTrabajo = new ABMTrabajo();
        listAutos=Auto.findAll();
        actualizarLista();
        try {
            reporteTrab = new ControladorJReport("trabajo.jasper");
        } catch (JRException ex) {
            Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }

        trabajoGui.getBusquedaAuto().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleasedAuto(evt);
            }
        });
        trabajoGui.getTablaAutos().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });


    
    
        

    }

    public void busquedaKeyReleasedAuto(java.awt.event.KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }


        private void realizarBusqueda() {
     listAutos=Auto.findBySQL("SELECT a.id, a.patente, a.modelo, a.marca, a.cliente_id, a.ult_cambio_aceite FROM cacique.autos as a,cacique.clientes as c where c.dni like ? or c.nombre like ? or a.patente like ? or a.id like ? group by a.id", "%" + trabajoGui.getBusquedaAuto().getText() + "%", "%" + trabajoGui.getBusquedaAuto().getText() + "%", "%" + trabajoGui.getBusquedaAuto().getText() + "%", "%" + trabajoGui.getBusquedaAuto().getText() + "%");  
        actualizarLista();
    }

        public void cargarTodos() {
        
        listAutos = Auto.findAll();
        if (!listAutos.isEmpty()) {
            realizarBusqueda();
            System.out.println("cargue todo");
        }
        }
        
         public void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            auto= Auto.findById(tablaAutos.getValueAt(tablaAutos.getSelectedRow(), 0));
            cliente= Cliente.findById(tablaAutos.getValueAt(tablaAutos.getSelectedRow(), 3));
            trabajoGui.getDuenio().setText(cliente.getString("nombre"));
            trabajoGui.getAuto().setText(auto.getString("patente"));
        }
         }
         
             private void actualizarLista() {
        
        tablaAutosDefault.setRowCount(0);
        Iterator<Auto> it = listAutos.iterator();
        System.out.println(listAutos.size());
        while (it.hasNext()) {
            Auto aut = it.next();
            Cliente cli= aut.parent(Cliente.class);
            Object row[] = new String[6];
            row[0] = aut.getString("id");
            row[1] = aut.getString("patente");
            row[2] = aut.getString("modelo");
            row[3] = aut.getString("cliente_id");
            row[4] = cli.getString("nombre");
            row[5] = cli.getString("dni");
            tablaAutosDefault.addRow(row);

        }
                
    }

    @Override
    public void actionPerformed(ActionEvent e) {

     if (e.getSource() == trabajoGui.getNuevo()) {
            System.out.println("Boton nuevo pulsado");
            trabajoGui.limpiarCampos();
            trabajoGui.bloquearCampos(true);
            isNuevo = true;
            editandoInfo = true;
            trabajo= new Trabajo();
            trabajoGui.getBorrar().setEnabled(false);
            trabajoGui.getModificar().setEnabled(false);
            trabajoGui.getGuardar().setEnabled(true);
            trabajoGui.getBotImprimir().setEnabled(false);
        }
      if (e.getSource() == trabajoGui.getGuardar() && editandoInfo && isNuevo) {
            System.out.println("Boton guardar pulsado");
                        if(!trabajoGui.getDuenio().getText().equals("")&&!trabajoGui.getAuto().getText().equals("")){

            if (cargarDatosTrabajo(trabajo)) {
                
                if (abmTrabajo.alta(trabajo,auto,cliente)) {

                    trabajoGui.bloquearCampos(false);
                    trabajoGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(trabajoGui, "¡trabjo guardado exitosamente!");
                    trabajoGui.getNuevo().setEnabled(true);
                    trabajoGui.getGuardar().setEnabled(false);
                    try {
                        System.out.println(abmTrabajo.getIdTrab());
                        reporteTrab.mostrarTrabajo(abmTrabajo.getIdTrab());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JRException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else 
                    JOptionPane.showMessageDialog(trabajoGui, "ocurrió un error", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                
                        }else{
                                                               JOptionPane.showMessageDialog(trabajoGui, "Seleccione un auto", "Error!", JOptionPane.ERROR_MESSAGE);
 
                        }
            }
      
      if (e.getSource() == trabajoGui.getBorrar()) {

            System.out.println("Boton borrar pulsado");
            trabajoGui.bloquearCampos(false);
            
            
            if (trabajo.getString("id") != null && !editandoInfo) {
                Integer resp = JOptionPane.showConfirmDialog(trabajoGui, "¿Desea borrar el trabajo " , "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    
                    Boolean seBorro = abmTrabajo.baja(trabajo);
                    
                    if (seBorro) {
                        JOptionPane.showMessageDialog(trabajoGui, "¡Trabajo borrado exitosamente!");
                        trabajoGui.limpiarCampos();
                        trabajoGui.getBorrar().setEnabled(false);
                        trabajoGui.getModificar().setEnabled(false);
                        trabajoGui.getBotImprimir().setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(trabajoGui, "Ocurrió un error, no se borró el trabajo", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(trabajoGui, "No se seleccionó un trabajo");
            }



        }
      
      if (e.getSource() == trabajoGui.getModificar()) {
            System.out.println("Boton modificar pulsado");
            auto= trabajoGui.getAutoModel();
            cliente= trabajoGui.getClienteModel();
            trabajoGui.bloquearCampos(true);
            editandoInfo = true;
            isNuevo = false;
            //articuloGui.getCodigo().setEnabled(false);
            trabajoGui.getBorrar().setEnabled(false);
            trabajoGui.getGuardar().setEnabled(true);
            trabajoGui.getModificar().setEnabled(false);
             trabajoGui.getBotImprimir().setEnabled(false);
            
        }
      
      if (e.getSource() == trabajoGui.getGuardar() && editandoInfo && !isNuevo) {
            System.out.println("Boton guardar pulsado");
            if(!trabajoGui.getDuenio().getText().equals("")&&!trabajoGui.getAuto().getText().equals("")){
            if (cargarDatosTrabajo(trabajo)) {
                trabajo.set("id",trabajoGui.getIdTrabajo().getText());
                if (abmTrabajo.modificar(trabajo,auto,cliente)) {
                    

                    trabajoGui.bloquearCampos(false);
                    trabajoGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(trabajoGui, "¡Trabajo modificado exitosamente!");
                    trabajoGui.getNuevo().setEnabled(true);
                    trabajoGui.getGuardar().setEnabled(false);
                    try {
                        System.out.println((Integer)abmTrabajo.getIdTrab());
                        reporteTrab.mostrarTrabajo((Integer)abmTrabajo.getIdTrab());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JRException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(trabajoGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        }else{
                                    JOptionPane.showMessageDialog(trabajoGui, "Seleccione un auto", "Error!", JOptionPane.ERROR_MESSAGE);

            }
      }
      if(e.getSource()==trabajoGui.getBotImprimir()){
          try {
                        System.out.println((Integer)abmTrabajo.getIdTrab());
                        reporteTrab.mostrarTrabajo(Integer.parseInt(trabajoGui.getIdTrabajo().getText()));
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JRException ex) {
                        Logger.getLogger(ControladorTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    }
      }
        
    
    }
    
     private boolean cargarDatosTrabajo(Trabajo trab) {
         boolean ret = true;
                  BigDecimal precioBat=new BigDecimal(BigInteger.ZERO);
                  BigDecimal costo= new BigDecimal(0.0);
                   try {
            Double costoD = Double.valueOf(trabajoGui.getCostoTotal().getText());
            costo=BigDecimal.valueOf(costoD).setScale(2, RoundingMode.CEILING);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(trabajoGui, "Error en costo total", "Error!", JOptionPane.ERROR_MESSAGE);
        }

         if(trabajoGui.getCambioBateria().isSelected()){
         
         try {
            Double precio = Double.valueOf(trabajoGui.getPrecioBateria().getText());
            precioBat=BigDecimal.valueOf(precio).setScale(2, RoundingMode.CEILING);
        } catch (NumberFormatException | ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(trabajoGui, "Error en precio de bateria", "Error!", JOptionPane.ERROR_MESSAGE);
        }
         }
         if(ret){
             
trab.set(       
                "fecha", trabajoGui.getFecha().getDate(),
"kilometraje",trabajoGui.getKilometraje().getText(),
"observaciones",trabajoGui.getObservaciones().getText(),
"aceite_caja",trabajoGui.getAceiteCaja().getSelectedItem(),
"aceite_diferencial",trabajoGui.getAceiteDif().getSelectedItem(),
"filtro_aire",trabajoGui.getFiltroAire().getSelectedItem(),
"filtro_combustible",trabajoGui.getFiltroCombustible().getSelectedItem(),
"filtro_aceite",trabajoGui.getFiltroAceite().getSelectedItem(),
"filtro_habitaculo",trabajoGui.getFiltroHabitaculo().getSelectedItem(),
"liquido_freno",trabajoGui.getLiquidoFreno().getSelectedItem(),
"anticongelante",trabajoGui.getAnticongelante().getSelectedItem(),
"correa_multicanal",trabajoGui.getCorreaMulticanal().getSelectedItem(),
"tensor_correa_multicanal",trabajoGui.getTensorMulticanal().getSelectedItem(),
"correa_distribucion",trabajoGui.getCorreaDistrib().getSelectedItem(),
"tensores",trabajoGui.getTensores().getSelectedItem(),
"bomba_agua",trabajoGui.getBombaAgua().getSelectedItem(),
"bateria",trabajoGui.getCambioBateria().isSelected(),
"tipo_bateria",trabajoGui.getTipoBateria().getText(),
"importe_bateria",precioBat,
"descripcion_bateria",trabajoGui.getDescripcionBateria().getText(),
"costo",costo,
"descripcion_adicional",trabajoGui.getObservGeneral().getText(),
"aceite_motor",trabajoGui.getAceiteMotor().getSelectedItem(),
"mecanico",trabajoGui.getMecanico().getText(),
"aceite_usa",trabajoGui.getAceiteUsa().getText());
         }
         return ret;
     }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
     
     

}
