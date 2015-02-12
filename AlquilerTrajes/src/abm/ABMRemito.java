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

    public Remito getRemito(Remito r) {
        return Remito.first("numero = ?", r.get("numero"));
    }

    public boolean existRemitoByNum(Remito r) {
        return (Remito.first("numero = ?", r.get("numero")) != null);
    }
    
    public boolean existRemitoByIdCliente(Remito r) {
        return (Remito.first("id_cliente = ?", r.get("is_cliente")) != null);
    }

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

    public static void main(String[] args) {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "root", "root");
        Remito r = new Remito();
        
        r.set("numero", 1);
        r.set("fecha_de_remito", "2015-02-12");
        r.set("id_cliente", 1);
        r.set("total",99.99);
        r.set("senia", 27);
        ABMRemito abm = new ABMRemito();
        abm.alta(r);

        Base.close();
    }

}
