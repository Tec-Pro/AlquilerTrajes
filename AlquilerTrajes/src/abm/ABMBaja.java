/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import modelos.Baja;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ABMBaja {
    
   
    //existe  el articulo?
    public boolean findBaja(Baja p) {
        return (Baja.first("id = ?", p.get("id")) != null);
    }

    
    public boolean alta(Baja art) {
        Base.openTransaction();
        if (!findBaja(art)) { 
            Baja nuevo = Baja.create(
                    "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "fecha", art.get("fecha"),
                    "cobro", art.get("cobro"),
                    "descripcion", art.get("descripcion"),
                    "talle", art.get("talle"), 
                    "tipo", art.get("tipo"));
            nuevo.saveIt();
            Base.commitTransaction();
            return true;
        } else {
            Base.commitTransaction();
            return false;
        }
    }

    public boolean baja(Baja art) {
        boolean ret = false;
        Base.openTransaction();
        if (findBaja(art)) {            
            ret = art.delete();
            art.defrost();            
        }
        Base.commitTransaction();
        return ret;
    }

    public boolean modificar(Baja art) {
        boolean ret = false;
        Base.openTransaction(); 
        Baja viejo = Baja.findFirst("id = ?", art.get("id"));
        if (viejo != null) {                       
            viejo.set(
                   "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "fecha", art.get("fecha"),
                    "cobro", art.get("cobro"),
                    "descripcion", art.get("descripcion"),
                    "talle", art.get("talle"), 
                    "tipo", art.get("tipo"));
            viejo.saveIt();            
        }
        Base.commitTransaction();
        return ret;
    }
}

