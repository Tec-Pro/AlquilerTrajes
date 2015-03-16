/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import BD.BaseDatos;
import abm.ABMRemito;
import busqueda.Busqueda;
import busqueda.BusquedaArticulo;
import interfaz.RemitoGui;
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
import modelos.Ambo;
import modelos.Articulo;
import modelos.Cliente;
import modelos.Remito;

/**
 *
 * @author eze
 */
public class ControladorRemito implements ActionListener {

    private ABMRemito abmRemito; //abm de un Remito
    private final RemitoGui remitoGui; // gui de una remito
    private Remito remito; // modelo de remito
    private final Busqueda busqueda; //busquedas de clientes
    private final boolean isNuevoRemito; //si el remito es nuevo, o es uno que se modificara
    private String fechaRemito; //fecha en que se realiza el remito
    private Integer idCliente; //ID del cliente que realizo el remito
    private final BusquedaArticulo busquedaArticulo;//busqueda de articulos
    private Integer numeroRemito;//numero de remito
    private Double señaRemito;//seña del remito
    private Double totalRemito;//total del remito
    private List<Ambo> listaAmbos; //lista de Ambos de un Remito a modificar
    private List<Articulo> listaArticulos; //lista de Articulos de un remito a modificar

    ControladorRemito(RemitoGui remitoGui, Remito r) throws SQLException {
        this.remitoGui = remitoGui;
        this.busqueda = new Busqueda();
        this.busquedaArticulo = new BusquedaArticulo();
        this.remitoGui.setActionListener(this);
        this.listaAmbos = null;
        this.listaArticulos = null;
        // si r es distinto de null, tenemos un remito a modificar
        if (r != null) {
            this.isNuevoRemito = false; //el remito no es nuevo
            this.remito = r; // le asigno al remito, que es pasado por parametro para modificar
            this.abmRemito = new ABMRemito();
            this.idCliente = (Integer) remito.get("cliente_id");
            cargarRemito(this.remito); //cargo la remito en la gui
            // sino creamos una nueva remito
        } else {
            this.isNuevoRemito = true; // el remito es nuevo
            this.fechaRemito = null;
            this.idCliente = null;
            this.señaRemito = null;
            this.totalRemito = null;
            this.numeroRemito = null;
            this.remito = new Remito(); // creo un modelo nuevo de remito
            this.abmRemito = new ABMRemito();
        }
        //escucho en el JText lo que se va ingresando para buscar un cliente
        this.remitoGui.getBusquedaClienteRemito().addKeyListener(new java.awt.event.KeyAdapter() {
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
        //escucho en el JText lo que se va ingresando para buscar un articulo
        this.remitoGui.getBusquedaCodigoArticulo().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    List<Articulo> listaArticulos;
                    List<Ambo> listaAmbos;
                    listaArticulos = busquedaArticulos(evt);
                    listaAmbos = busquedaAmbos(evt);
                    if (listaArticulos != null || listaAmbos != null) { //si hay articulos o ambos los cargo en la gui
                        actualizarTablaArticulos(listaArticulos, listaAmbos);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

        //reviso si se clickea alguna fila de la tabla clientes
        this.remitoGui.getTablaClienteRemito().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClickedClientes(evt); //si se clickea alguna fila, saco el id del cliente seleccionado
            }
        });
        //reviso si se clickea alguna fila de la tabla de busqueda de articulos
        this.remitoGui.getTablaBusquedaArticulosRemito().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClickedBusquedaArticulos(evt); //cargo la fila clickeada en la tabla de Articulos del Remito
            }
        });
        //reviso si se clickea alguna fila de la tabla de articulos para el remito (Doble click sobre el articulo lo elimina de la tabla)
        this.remitoGui.getTablaArticulosRemito().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    eliminarFilaArticulosRemito();
                }
            }
        });
    }

    /*Saca el id del cliente seleccionado en la tabla de busqueda de clientes
     * y setea el JText de busqueda con el cliente seleccionado
     */
    private void tablaMouseClickedClientes(MouseEvent evt) {
        int selectedRow = remitoGui.getTablaClienteRemito().getSelectedRow();
        DefaultTableModel modelo = ((DefaultTableModel) remitoGui.getTablaClienteRemito().getModel());
        remitoGui.getBusquedaClienteRemito().setText(modelo.getValueAt(selectedRow, 0) + " - "
                + modelo.getValueAt(selectedRow, 1) + "  " + modelo.getValueAt(selectedRow, 2));
        idCliente = (Integer) modelo.getValueAt(selectedRow, 0);
    }

    /* Inserto las filas seleccionadas en la tabla Busqueda de Articulos en la 
     * tabla Articulos remitos.
     */
    private void tablaMouseClickedBusquedaArticulos(MouseEvent evt) {
        /*Por defecto se utiliza la seleccion de filas individuales en la tabla*/
        int selectedRow = remitoGui.getTablaBusquedaArticulosRemito().getSelectedRow();
        DefaultTableModel modeloBusqueda = ((DefaultTableModel) remitoGui.getTablaBusquedaArticulosRemito().getModel());
        Object[] row;
        DefaultTableModel modeloRemito = ((DefaultTableModel) remitoGui.getTablaArticulosRemito().getModel());
        row = new Object[6];
        row[0] = (modeloBusqueda.getValueAt(selectedRow, 0));
        row[1] = (modeloBusqueda.getValueAt(selectedRow, 1));
        row[2] = (modeloBusqueda.getValueAt(selectedRow, 2));
        row[3] = (modeloBusqueda.getValueAt(selectedRow, 3));
        row[4] = (modeloBusqueda.getValueAt(selectedRow, 4));
        row[5] = (modeloBusqueda.getValueAt(selectedRow, 6));
        modeloRemito.addRow(row);
        Double precio = Double.parseDouble((String) modeloBusqueda.getValueAt(selectedRow, 6));
        //si agrego un articulo a la tabla de articulos del remito, sumo su precio del total
        remitoGui.setjTextTotalRemito((Double.parseDouble(remitoGui.getjTextTotalRemito().getText())) + precio);
        
    }

    //Busco los clientes a partir de los datos ingresados en el JText de busqueda
    private List<Cliente> busquedaClientes(KeyEvent evt) throws SQLException {
        String textBusquedaCliente = remitoGui.getBusquedaClienteRemito().getText();
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
        DefaultTableModel modelo = ((DefaultTableModel) remitoGui.getTablaClienteRemito().getModel());
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

    //Busco los articulos a partir de los datos ingresados en el JText de busqueda
    private List<Articulo> busquedaArticulos(KeyEvent evt) throws SQLException {
        String textBusquedaArticulo = remitoGui.getBusquedaCodigoArticulo().getText();
        List<Articulo> listArticulo = busquedaArticulo.buscarArticulos(textBusquedaArticulo);
        return listArticulo;
    }

    //Busco los ambos a partir de los datos ingresados en el JText de busqueda
    private List<Ambo> busquedaAmbos(KeyEvent evt) throws SQLException {
        String textBusquedaAmbo = remitoGui.getBusquedaCodigoArticulo().getText();
        List<Ambo> listAmbo = busquedaArticulo.buscarAmbos(textBusquedaAmbo);
        return listAmbo;
    }

    //Carga la lista de articulos y ambos encontrados en la tabla busqueda de articulos
    private void actualizarTablaArticulos(List<Articulo> listaArticulos, List<Ambo> listaAmbos) throws SQLException {
        DefaultTableModel modelo = ((DefaultTableModel) remitoGui.getTablaBusquedaArticulosRemito().getModel());
        modelo.setRowCount(0);
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Iterator<Articulo> itrArticulo = listaArticulos.iterator();
        Iterator<Ambo> itrAmbo = listaAmbos.iterator();
        Articulo ar;
        Ambo am;
        Object[] o = new Object[7];
        while (itrArticulo.hasNext()) {
            ar = itrArticulo.next();
            o[0] = (ar.getId());
            o[1] = (ar.getString("modelo"));
            o[2] = (ar.getString("marca"));
            o[3] = (ar.getString("tipo"));
            o[4] = (ar.getString("talle"));
            o[5] = (ar.getString("descripcion"));
            o[6] = (ar.getString("precio_alquiler"));
            modelo.addRow(o);

        }
        while (itrAmbo.hasNext()) {
            am = itrAmbo.next();
            o[0] = (am.getId());
            o[1] = (am.get("nombre"));
            o[2] = (am.getString("marca"));
            o[3] = ("ambo");
            o[4] = (am.getString("talle"));
            o[5] = (am.getString("descripcion"));
            o[6] = (am.getString("precio_alquiler"));
            modelo.addRow(o);

        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    /*
     * Elimino el articulo de la tabla de productos de una remito, si se hace doble click sobre el
     */
    private void eliminarFilaArticulosRemito() {
        int selectedRow = remitoGui.getTablaArticulosRemito().getSelectedRow();
        DefaultTableModel modeloRemito = ((DefaultTableModel) remitoGui.getTablaArticulosRemito().getModel());
        Double precio = Double.parseDouble((String) modeloRemito.getValueAt(selectedRow, 5));
        //si quito un articulo de la tabla de articulos del remito, resto su precio del total
        remitoGui.setjTextTotalRemito((Double.parseDouble(remitoGui.getjTextTotalRemito().getText())) - precio);
        modeloRemito.removeRow(selectedRow);
    }

    //Carga un remito en la gui (Remito guardado previamente a modificar)
    private void cargarRemito(Remito r) throws SQLException {
        Cliente c = busqueda.buscarCliente(r.get("cliente_id"));
        remitoGui.setBusquedaClienteRemito(c.getId() + " - " + c.getString("nombre") + " " + c.getString("dni"));
        Date dateFR = r.getDate("fecha_de_remito");
        remitoGui.setjDateFechaRemito(dateFR);
        remitoGui.setjTextNumeroRemito(r.getString("numero"));
        remitoGui.setjTextSeñaRemito(r.getString("senia"));
        remitoGui.setjTextTotalRemito(r.getString("total"));
        //actualizo el checkbox de pago confirmado (remito cerrado o abierto)
        if (r.getBoolean("cerrado")){
            remitoGui.getjCheckConfirmarPago().setSelected(true);
        }else{
            remitoGui.getjCheckConfirmarPago().setSelected(false);
        }
        //Saco todos los articulos y ambos de la remito a cargar
        this.listaAmbos = r.getAll(Ambo.class);
        this.listaArticulos = r.getAll(Articulo.class);

        DefaultTableModel modeloArticulos = ((DefaultTableModel) remitoGui.getTablaArticulosRemito().getModel());
        modeloArticulos.setRowCount(0);
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Object[] o = new Object[6];
        if (listaArticulos != null) {
            Articulo ar;
            Iterator<Articulo> itrArticulo = listaArticulos.iterator();
            while (itrArticulo.hasNext()) {
                ar = itrArticulo.next();
                o[0] = (ar.getId());
                o[1] = (ar.getString("modelo"));
                o[2] = (ar.getString("marca"));
                o[3] = (ar.getString("tipo"));
                o[4] = (ar.getString("talle"));
                o[5] = (ar.getDouble("precio_alquiler"));
                modeloArticulos.addRow(o);
            }
        }
        if (listaAmbos != null) {
            Ambo am;
            Iterator<Ambo> itrAmbo = listaAmbos.iterator();
            while (itrAmbo.hasNext()) {
                am = itrAmbo.next();
                o[0] = (am.getId());
                o[1] = (am.get("nombre"));
                o[2] = (am.getString("marca"));
                o[3] = ("ambo");
                o[4] = (am.getString("talle"));
                o[5] = (am.getDouble("precio_alquiler"));
                modeloArticulos.addRow(o);
            }
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        /*Si presiono el boton de agregar un remito, y ademas el remito es nuevo,
         * lo doy de alta en la base de datos.
         */
        if (ae.getSource().equals(remitoGui.getBttnGuardarRemito()) && isNuevoRemito) {
            fechaRemito = remitoGui.getFechaRemito();
            señaRemito = Double.parseDouble(remitoGui.getjTextSeñaRemito().getText());
            totalRemito = Double.parseDouble(remitoGui.getjTextTotalRemito().getText());
            
            //REVISAR QUE EL NUMERO DE REMITO NO SE UN STRING, SINO NUMEROS
            
            
            numeroRemito = Integer.parseInt(remitoGui.getjTextNumeroRemito().getText());
            /*si es null, puede que no haya sido cargado de la tabla aun, asi que lo verifico
            * ( es posible que el remito haya sido creado a traves de una reserva, por lo tanto
            * el idCliente no fue obtenido de remitoGui aún).
            */
            DefaultTableModel modeloClientes = ((DefaultTableModel) remitoGui.getTablaClienteRemito().getModel());
            if(idCliente == null && modeloClientes.getRowCount() != 0){
                idCliente = (Integer) modeloClientes.getValueAt(remitoGui.getTablaClienteRemito().getSelectedRow(), 0);
            }
            DefaultTableModel modeloArticulos = (DefaultTableModel) remitoGui.getTablaArticulosRemito().getModel();
            if (idCliente != null && fechaRemito != null && señaRemito != null && numeroRemito != null
                    && totalRemito != null && modeloArticulos.getRowCount() != 0) {
                remito.set("fecha_de_remito", fechaRemito);
                remito.set("total", totalRemito);
                remito.set("senia", señaRemito);
                remito.set("numero", numeroRemito);
                remito.set("cliente_id", idCliente);
                /*Si el pago fue realizado en su totalidad (pago confirmado - checkbox seleccionado),
                * cierro el remito asignandole (con un 1 en la base de datos)
                */
                System.out.println("Cerrado: "+remitoGui.getjCheckConfirmarPago().isSelected());
                if(remitoGui.getjCheckConfirmarPago().isSelected()){
                    remito.set("cerrado", true);
                }else{//si el pago no fue realizado, el remito sigue abierto (con 0 en la base de datos)
                    remito.set("cerrado", false);
                }
                try {
                    if (abmRemito.alta(remito)) {//si el remito pudo ser creado, procedo a guardar la demas informacion
                        remito.set("id", abmRemito.getUltimoId());
                        //Saco los articulos del remito, de la tabla correspondiente y los guardo en la BD
                        BaseDatos.abrirBase();
                        BaseDatos.openTransaction();
                        Articulo artAux = null;
                        Ambo amboAux = null;
                        for (int i = 0; i < modeloArticulos.getRowCount(); i++) {
                            //si es un ambo lo busco por su id en la base
                            if (modeloArticulos.getValueAt(i, 3).equals("ambo")) {
                                amboAux = Ambo.findById(modeloArticulos.getValueAt(i, 0));
                                this.remito.add(amboAux);
                                //si no es un ambo, es un articulo
                            } else {
                                artAux = Articulo.findById(modeloArticulos.getValueAt(i, 0));
                                this.remito.add(artAux);

                            }
                        }
                        BaseDatos.commitTransaction();
                        BaseDatos.cerrarBase();

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(remitoGui, "El Remito ha sido creado con éxito!.");
                this.remitoGui.limpiarComponentes();
            } else {
                JOptionPane.showMessageDialog(remitoGui, "La informacion es insuficiente o erronea, por favor complete todos los campos.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        /*Si presiono el boton de agregar un remito, y no es una remito nuevo(remito a modifcar),
         * lo modifico en la base de datos.
         */
        if (ae.getSource().equals(remitoGui.getBttnGuardarRemito()) && !isNuevoRemito) {
            fechaRemito = remitoGui.getFechaRemito();
            señaRemito = Double.parseDouble(remitoGui.getjTextSeñaRemito().getText());
            totalRemito = Double.parseDouble(remitoGui.getjTextTotalRemito().getText());
            numeroRemito = Integer.parseInt(remitoGui.getjTextNumeroRemito().getText());
            boolean remitoCerrado; //1 si el remito esta cerrado, 0 si esta abierto
            if(remitoGui.getjCheckConfirmarPago().isSelected()){
                remitoCerrado = true;
            }else{
                remitoCerrado = false;
            }
            DefaultTableModel modeloArticulos = (DefaultTableModel) remitoGui.getTablaArticulosRemito().getModel();
            if (idCliente != null && fechaRemito != null && señaRemito != null && numeroRemito != null
                    && totalRemito != null && modeloArticulos.getRowCount() != 0) {
                remito.set("fecha_de_remito", fechaRemito);
                remito.set("total", totalRemito);
                remito.set("senia", señaRemito);
                remito.set("numero", numeroRemito);
                remito.set("cliente_id", idCliente);
                remito.set("cerrado", remitoCerrado);
                try {
                    if (abmRemito.modificar(remito)) {
                        BaseDatos.abrirBase();
                        BaseDatos.openTransaction();
                        //Elimino los Ambos y articulos del remito, en la base de datos
                        if (listaAmbos != null || listaArticulos != null) {
                            if (listaArticulos != null) {
                                Articulo ar;
                                Iterator<Articulo> itrArticulo = listaArticulos.iterator();
                                while (itrArticulo.hasNext()) {
                                    ar = itrArticulo.next();
                                    this.remito.remove(ar);

                                }
                            }
                            if (listaAmbos != null) {
                                Ambo am;
                                Iterator<Ambo> itrAmbo = listaAmbos.iterator();
                                while (itrAmbo.hasNext()) {
                                    am = itrAmbo.next();
                                    this.remito.remove(am);
                                }
                            }
                            //Saco los articulos nuevos del remito, de la tabla correspondiente y los guardo en la BD
                            Articulo artAux = null;
                            Ambo amboAux = null;
                            for (int i = 0; i < modeloArticulos.getRowCount(); i++) {
                                //si es un ambo lo busco por su id en la base
                                if (modeloArticulos.getValueAt(i, 3).equals("ambo")) {
                                    amboAux = Ambo.findById(modeloArticulos.getValueAt(i, 0));
                                    this.remito.add(amboAux);
                                    //si no es un ambo, es un articulo
                                } else {
                                    artAux = Articulo.findById(modeloArticulos.getValueAt(i, 0));
                                    this.remito.add(artAux);

                                }
                            }
                            BaseDatos.commitTransaction();
                            BaseDatos.cerrarBase();
                        } else {
                            JOptionPane.showMessageDialog(remitoGui, "No se encontraron Artículos ni Ambos en este Remito.", "Error!", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(remitoGui, "La Remito ha sido modificado con éxito!.");
                this.remitoGui.limpiarComponentes();
            } else {
                JOptionPane.showMessageDialog(remitoGui, "La informacion es insuficiente o erronea, por favor complete todos los campos.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        /* Si presiona el boton Cancelar, se cierra la ventana
        */
        if (ae.getSource().equals(remitoGui.getBttnCancelarRemito())) {
            this.remitoGui.hide();
        }
        /* Si presiona el boton Eliminar Remito y este todavia no fue creado
        * en la base de datos, solamente cierro la ventana.
        */
        if (ae.getSource().equals(remitoGui.getBttnEliminarRemito()) && isNuevoRemito) {
            this.remitoGui.hide();
        }
        /* Si presiona el boton Eliminar Remito y este habia sido creado
        * previamente, el remito se borra de la base de datos.
        */
        if (ae.getSource().equals(remitoGui.getBttnEliminarRemito()) && !isNuevoRemito) {
            try {
                this.abmRemito.baja(this.remito);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.remitoGui.hide();
        }
    }

}
