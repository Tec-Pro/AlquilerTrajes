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
        Base.openTransaction();
        boolean result = (Reserva.first("fecha_entrega_reserva = ?", r.get("fecha_entrega_reserva")) != null);
        Base.commitTransaction();
        return result;
    }
    
    //Da de alta una reserva en la BD
    public boolean alta(Reserva r) {
        if (!existReserva(r)) {
            Base.openTransaction();
            Reserva nuevo = Reserva.create("fecha_reserva", r.get("fecha_reserva"), "fecha_entrega_reserva",
                    r.get("fecha_entrega_reserva"), "id_cliente", r.get("id_cliente"));
            nuevo.saveIt();
            Base.commitTransaction();
            return true;
        } else {
            return false;
        }
    }

    //Revisar el delete que no rompa toda la base (deleteOnCascade deberia ser)
    //Da de baja una reserva en la BD (lo elimina)
    public boolean baja(Reserva r) {
        Reserva viejo = Reserva.findById(r.getId());
        if (viejo != null) {
            Base.openTransaction();
            viejo.delete();
            Base.commitTransaction();
            return true;
        }
        return false;

    }

    //Modifica los datos de una reserva especifica (reserva identificada por su id)
    public boolean modificar(Reserva r) {
        Reserva viejo = Reserva.findById(r.getId());
        if (viejo != null) {
            Base.openTransaction();
            viejo.set("fecha_reserva", r.get("fecha_reserva"), "fecha_entrega_reserva",
                    r.get("fecha_entrega_reserva"), "id_cliente", r.get("id_cliente")).saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
    }
    
    //para probar el ABM
    public static void main(String[] args) {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "root", "root");
        Reserva r = new Reserva();
        r.set("id",1);
        r.set("fecha_reserva", "2015-02-12");
        r.set("fecha_entrega_reserva", "2015-03-01");
        r.set("id_cliente", 1);
        ABMReserva abm = new ABMReserva();
        abm.baja(r);

        Base.close();
    }
    
    
}
