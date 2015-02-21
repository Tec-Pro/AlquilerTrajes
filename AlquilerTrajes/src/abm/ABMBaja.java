/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import BD.BaseDatos;
import java.sql.SQLException;
import modelos.Baja;

/**
 *
 * @author jacinto
 */
public class ABMBaja {

    //existe  el articulo?
    public boolean findBaja(Baja p) throws SQLException {
        boolean ret = (Baja.first("id = ?", p.get("id")) != null);
        return ret;
    }

    public boolean alta(Baja art) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        if (!findBaja(art)) {
            Baja nuevo = Baja.create(
                    "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "fecha", art.getDate("fecha"),
                    "cobro", art.getFloat("cobro"),
                    "descripcion", art.getString("descripcion"),
                    "talle", art.get("talle"),
                    "tipo", art.get("tipo"));
                ret = nuevo.saveIt();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }

    public boolean baja(Baja art) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        if (findBaja(art)) {
            ret = art.delete();
            art.defrost();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }

    public boolean modificar(Baja art) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Baja viejo = Baja.findFirst("id = ?", art.get("id"));
        if (viejo != null) {
            viejo.set(
                    "modelo", art.get("modelo"),
                    "marca", art.get("marca"),
                    "fecha", art.getDate("fecha"),
                    "cobro", art.getFloat("cobro"),
                    "descripcion", art.get("descripcion"),
                    "talle", art.get("talle"),
                    "tipo", art.get("tipo"));
            ret = viejo.saveIt();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }
}
