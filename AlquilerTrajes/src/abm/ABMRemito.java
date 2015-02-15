/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import modelos.Remito;
import org.javalite.activejdbc.Base;

/**
 *
 * @author eze
 */
public class ABMRemito {

    //Obtengo un remito por su numero
    public Remito getRemito(Remito r) {
        Base.openTransaction();
        Remito result = Remito.first("numero = ?", r.get("numero"));
        Base.commitTransaction();
        return result;
    }

    //Es True si existe el remito buscado por su numero
    public boolean existRemitoByNum(Remito r) {
        Base.openTransaction();
        boolean result =  (Remito.first("numero = ?", r.get("numero")) != null);
        Base.commitTransaction();
        return result;
    }

    //Da de alta un remito en la BD
    public boolean alta(Remito r) {
        if (!existRemitoByNum(r)) {
            Base.openTransaction();
            Remito nuevo = Remito.create("numero", r.get("numero"), "fecha_de_remito",
                    r.get("fecha_de_remito"), "id_cliente", r.get("id_cliente"),
                    "total", r.get("total"), "senia", r.get("senia"));
            nuevo.saveIt();
            Base.commitTransaction();
            return true;
        } else {
            return false;
        }
    }

    //Revisar el delete que no rompa toda la base (deleteOnCascade deberia ser)
    //Da de baja un remito en la BD (lo elimina)
    public boolean baja(Remito r) {
        Remito viejo = Remito.findById(r.getId());
        if (viejo != null) {
            Base.openTransaction();
            viejo.delete();
            Base.commitTransaction();
            return true;
        }
        return false;

    }

    //Modifica los datos de un remito especifico (remito identificado por su id)
    public boolean modificar(Remito r) {
        Remito viejo = Remito.findById(r.getId());
        if (viejo != null) {
            Base.openTransaction();
            viejo.set("numero", r.get("numero"), "fecha_de_remito",
                    r.get("fecha_de_remito"), "id_cliente", r.get("id_cliente"),
                    "total", r.get("total"), "senia", r.get("senia")).saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
    }

}
