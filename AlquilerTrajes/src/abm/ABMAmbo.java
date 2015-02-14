/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import modelos.Ambo;
import modelos.Articulo;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ABMAmbo {
    
    
     //existe  el articulo?
    public boolean findArticulo(Ambo p) {
        return (Articulo.first("id = ?", p.get("id")) != null);
    }

    public boolean alta(Ambo art) {
//        Base.openTransaction();
        if (!findArticulo(art)) {
            Ambo nuevo = Ambo.create(
                    "nombre", art.get("nombre"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio_alquiler", art.get("precio_alquiler"),
                    "descripcion", art.get("descripcion"),
                    "talle", art.get("talle"));
            nuevo.saveIt();
          //  Base.commitTransaction();
            return true;
        } else {
           // Base.commitTransaction();
            return false;
        }
    }

    public boolean baja(Ambo art) {
        boolean ret = false;        
        Base.openTransaction();
        if (findArticulo(art)) {
            ret = art.delete();
            art.defrost();
        }
        Base.commitTransaction();
        return ret;
    }

    public boolean modificar(Ambo art) {
        boolean ret = false;
        Base.openTransaction();
        Articulo viejo = Articulo.findFirst("id = ?", art.get("id"));
        if (viejo != null) {
            viejo.set(
                    "nombre", art.get("nombre"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio_alquiler", art.get("precio_alquiler"),
                    "descripcion", art.get("descripcion"),
                    "talle", art.get("talle"));
            ret = viejo.saveIt();
        }
        Base.commitTransaction();
        return ret;
    }
   
   
}
