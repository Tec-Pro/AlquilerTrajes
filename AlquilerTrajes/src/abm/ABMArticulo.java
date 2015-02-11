/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import modelos.Articulo;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ABMArticulo {

    
    //existe  el articulo?
    public boolean findArticulo(Articulo p) {
        return (Articulo.first("id = ?", p.get("id")) != null);
    }

    public boolean alta(Articulo art) {
        if (!findArticulo(art)) {   
            Base.openTransaction();
            Articulo nuevo = Articulo.create(
                    "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio_compra", art.get("precio_compra"),
                    "precio_alquiler", art.get("precio_alquiler"),
                    "descripcion", art.get("descripcion"),
                    "talle", art.get("talle"), 
                    "tipo", art.get("tipo"));
            nuevo.saveIt();
            Base.commitTransaction();
            return true;
        } else {
            System.out.println("Existe articulo");
            return false;
        }
    }

    public boolean baja(Articulo art) {
        boolean ret = false;
        if (findArticulo(art)) {
            Base.openTransaction();
            ret = art.delete();
            art.defrost();
            Base.commitTransaction();
        }
        return ret;
    }

    public boolean modificar(Articulo art) {
        boolean ret = false;
        Articulo viejo = Articulo.findFirst("id = ?", art.get("id"));
        if (viejo != null) {
            Base.openTransaction();            
            viejo.set(
                   "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio_compra", art.get("precio_compra"),
                    "precio_alquiler", art.get("precio_alquiler"),
                    "descripcion", art.get("descripcion"),
                    "talle", art.get("talle"), 
                    "tipo", art.get("tipo"));
            ret = viejo.saveIt();
            Base.commitTransaction();
        }
        return ret;
    }
    
    public boolean restarStock(int id){
        boolean ret = false;
        Articulo viejo = Articulo.findFirst("id = ?", id);
        if (viejo != null) {
            Base.openTransaction();
            if (viejo.getInteger("stock") > 0){
            viejo.set("stock", viejo.getInteger("stock")-1);
            } else {
                viejo.set("stock", 0);
            }
            ret = viejo.saveIt();
            Base.commitTransaction();
        }
        return ret;
    }
}
