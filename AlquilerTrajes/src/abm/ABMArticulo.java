/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import BD.BaseDatos;
import java.sql.SQLException;
import modelos.Articulo;

/**
 *
 * @author nico
 */
public class ABMArticulo {

    //existe  el articulo?
    public boolean findArticulo(Articulo p) throws SQLException {
        boolean ret = (Articulo.first("id = ?", p.get("id")) != null);
        return ret;
    }

    public boolean alta(Articulo art) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        if (!findArticulo(art)) {
            Articulo nuevo = Articulo.create(
                    "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "precio_compra", art.get("precio_compra"),
                    "precio_alquiler", art.get("precio_alquiler"),
                    "descripcion", art.get("descripcion"),
                    "talle", art.get("talle"),
                    "tipo", art.get("tipo"));
            ret = nuevo.saveIt();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }

    public boolean baja(Articulo art) throws SQLException {
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

    public boolean modificar(Articulo art) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Articulo viejo = Articulo.findFirst("id = ?", art.get("id"));
        if (viejo != null) {
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
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }

    public boolean restarStock(int id) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Articulo viejo = Articulo.findFirst("id = ?", id);
        if (viejo != null) {
            if (viejo.getInteger("stock") > 0) {
                viejo.set("stock", viejo.getInteger("stock") - 1);
            } else {
                viejo.set("stock", 0);
            }
            ret = viejo.saveIt();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }
}
