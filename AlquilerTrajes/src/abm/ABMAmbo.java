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
        boolean ret = (Ambo.first("id = ?", p.get("id")) != null);
        return ret;

    }
    
    public boolean findNombre(String nombre) throws SQLException {
        boolean ret = (Ambo.first("nombre = ?", nombre) != null);
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
            ultimoId = nuevo.getInteger("id");
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
        Ambo viejo = Ambo.findFirst("id = ?", art.get("id"));
        if (viejo != null) {
            viejo.set(
                    "nombre", art.get("nombre"),
                    "marca", art.get("marca"),
                    "stock", art.get("stock"),
                    "descripcion", art.get("descripcion"),
                    "precio_alquiler", art.get("precio_alquiler"),
                    "talle", art.get("talle"));
            ret = viejo.saveIt();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }
}
