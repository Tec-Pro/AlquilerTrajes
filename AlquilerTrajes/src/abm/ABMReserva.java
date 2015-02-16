/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import modelos.Reserva;
import org.javalite.activejdbc.Base;

/**
 *
 * @author eze
 */
public class ABMReserva {
    
    //True si la reserva dada existe ( se busca por fecha de entrega de la reserva)
    public boolean existReserva(Reserva r){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "root", "root");
        //Base.openTransaction();
        boolean result = (Reserva.first("fecha_entrega_reserva = ?", r.get("fecha_entrega_reserva")) != null);
        //Base.commitTransaction();
        Base.close();
        return result;
    }
    
    //Da de alta una reserva en la BD
    public boolean alta(Reserva r) {
        //if (!existReserva(r)) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "root", "root");
            //Base.openTransaction();
            Reserva nuevo = Reserva.create("fecha_reserva", r.get("fecha_reserva"), "fecha_entrega_reserva",
                    r.get("fecha_entrega_reserva"), "id_cliente", r.get("id_cliente"));
            nuevo.saveIt();
            //Base.commitTransaction();
            Base.close();
            return true;
        //} else {
          //  return false;
        //}
    }

    //Revisar el delete que no rompa toda la base (deleteOnCascade deberia ser)
    //Da de baja una reserva en la BD (lo elimina)
    public boolean baja(Reserva r) {
        Reserva viejo = Reserva.findById(r.getId());
        if (viejo != null) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "root", "root");
            //Base.openTransaction();
            viejo.delete();
            //Base.commitTransaction();
            Base.close();
            return true;
        }
        return false;

    }

    //Modifica los datos de una reserva especifica (reserva identificada por su id)
    public boolean modificar(Reserva r) {
        Reserva viejo = Reserva.findById(r.getId());
        if (viejo != null) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "root", "root");
            //Base.openTransaction();
            viejo.set("fecha_reserva", r.get("fecha_reserva"), "fecha_entrega_reserva",
                    r.get("fecha_entrega_reserva"), "id_cliente", r.get("id_cliente")).saveIt();
            //Base.commitTransaction();
            Base.close();
            return true;
        }
        return false;
    }
    
}
