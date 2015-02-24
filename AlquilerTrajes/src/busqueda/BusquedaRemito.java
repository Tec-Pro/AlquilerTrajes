/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import BD.BaseDatos;
import java.sql.SQLException;
import java.util.List;
import modelos.Remito;

/**
 *
 * @author eze
 */
public class BusquedaRemito {

    /*
    * Busqueda de remitos por Numero o Id de cliente
    */
    public List<Remito> buscarRemitoPorFechaONumero(String textBusquedaRemito) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        List<Remito> result;
        result = Remito.where("numero like ? or fecha_de_remito like ?", "%" + textBusquedaRemito + "%","%" + textBusquedaRemito + "%");
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }

    //busca un remito por su id
    public Remito buscarRemito(Object id) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Remito result = Remito.findById(id);
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    
}
