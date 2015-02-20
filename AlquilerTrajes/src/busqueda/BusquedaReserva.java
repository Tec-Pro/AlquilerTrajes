/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import BD.BaseDatos;
import java.sql.SQLException;
import java.util.List;
import modelos.Reserva;

/**
 *
 * @author eze
 */
public class BusquedaReserva {

    //busca una reserva por su id
    public Reserva buscarReserva(Object id) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Reserva result = Reserva.findById(id);
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    
    //Busca reservas por el id del cliente
    public List<Reserva> buscarReservaPorCliente(Integer id) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        List<Reserva> result;
        result = Reserva.where("id_cliente like ?", "%" +id+ "%");
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    
    //Busca una reserva por la fecha de entrega
    public List<Reserva> buscarReservaPorFechaEntrega(String textBusquedaReserva) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        List<Reserva> result;
        result = Reserva.where("fecha_entrega_reserva like ?", "%" +textBusquedaReserva+ "%");
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    
}
