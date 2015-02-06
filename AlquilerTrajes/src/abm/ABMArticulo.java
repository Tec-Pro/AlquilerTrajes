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

    //busca por el codigo, no por el id
    public Articulo getArticulo(Articulo p) {
        return Articulo.first("codigo =?", p.get("codigo"));
    }

    //existe  el articulo?
    public boolean findArticulo(Articulo p) {
        return (Articulo.first("codigo = ?", p.get("codigo")) != null);
    }

    public boolean alta(Articulo art) {
        if (!findArticulo(art)) {
   
            Base.openTransaction();
            Articulo nuevo = Articulo.create(
                    "id", art.get("id"),
                    "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio", art.get("precio_compra"),
                    "precio_alquiler", art.get("precio_venta"),
                    "descripcion", art.get("descripcion") 
                    );         

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
        Articulo viejo = Articulo.findFirst("codigo = ?", art.get("codigo"));
        if (viejo != null) {
            Base.openTransaction();

            
            viejo.set(
                   "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio", art.get("precio_compra"),
                    "precio_alquiler", art.get("precio_venta"),
                    "descripcion", art.get("descripcion"));
            viejo.saveIt();
            Base.commitTransaction();
        }
        return ret;
    }
}
