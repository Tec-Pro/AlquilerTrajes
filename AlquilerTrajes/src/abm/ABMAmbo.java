/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import BD.BaseDatos;
import java.sql.SQLException;
import modelos.Ambo;
import modelos.Articulo;

/**
 *
 * @author jacinto
 */
public class ABMAmbo {
 int ultimoId;
    //existe  el articulo?
    public boolean findArticulo(Ambo p) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        boolean ret = (Articulo.first("id = ?", p.get("id")) != null);
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;

    }

    public String getUltimoId() {
        return String.valueOf(ultimoId);
    }
    
    

    public boolean alta(Ambo art) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        if (!findArticulo(art)) {
            Ambo nuevo = Ambo.create(
                    "nombre", art.get("nombre"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio_alquiler", art.get("precio_alquiler"),
                    "talle", art.get("talle"));
            ret = nuevo.saveIt();
            ultimoId = (int) nuevo.getId();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }

    public boolean baja(Ambo art) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        if (findArticulo(art)) {
            ret = art.delete();
            art.defrost();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }

    public boolean modificar(Ambo art) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Articulo viejo = Articulo.findFirst("id = ?", art.get("id"));
        if (viejo != null) {
            viejo.set(
                    "nombre", art.get("nombre"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio_alquiler", art.get("precio_alquiler"),
                    "talle", art.get("talle"));
            ret = viejo.saveIt();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }
}
