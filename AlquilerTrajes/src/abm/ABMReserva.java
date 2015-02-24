/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import BD.BaseDatos;
import java.sql.SQLException;
import modelos.Reserva;
import org.javalite.activejdbc.Base;

/**
 *
 * @author eze
 */
public class ABMReserva {

    private Integer ultimoId; //id de la ultima reserva creada

    public Integer getUltimoId() {
        return ultimoId;
    }
    
    
    
    //True si la reserva dada existe ( se busca por fecha de entrega de la reserva)
    public boolean existReserva(Reserva r) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        boolean result = (Reserva.first("fecha_entrega_reserva = ?", r.get("fecha_entrega_reserva")) != null);
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }

    //Da de alta una reserva en la BD
    public boolean alta(Reserva r) throws SQLException {
        //if (!existReserva(r)) {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Reserva nuevo = Reserva.create("fecha_reserva", r.get("fecha_reserva"), "fecha_entrega_reserva",
                r.get("fecha_entrega_reserva"), "id_cliente", r.get("id_cliente"));
        nuevo.saveIt();
        ultimoId = nuevo.getInteger("id");
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return true;
        //} else {
        //  return false;
        //}
    }

    //Revisar el delete que no rompa toda la base (deleteOnCascade deberia ser)
    //Da de baja una reserva en la BD (lo elimina)
    public boolean baja(Reserva r) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Reserva viejo = Reserva.findById(r.getId());
        if (viejo != null) {
            viejo.delete();
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return true;
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return false;

    }

    //Modifica los datos de una reserva especifica (reserva identificada por su id)
    public boolean modificar(Reserva r) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Reserva viejo = Reserva.findById(r.getId());
        if (viejo != null) {
            viejo.set("fecha_reserva", r.get("fecha_reserva"), "fecha_entrega_reserva",
                    r.get("fecha_entrega_reserva"), "id_cliente", r.get("id_cliente")).saveIt();
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return true;
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return false;
    }

}
