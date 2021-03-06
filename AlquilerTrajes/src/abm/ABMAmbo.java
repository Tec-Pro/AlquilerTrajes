/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import BD.BaseDatos;
import java.sql.SQLException;
import java.util.Iterator;
import modelos.Ambo;
import modelos.Articulo;
import modelos.ArticulosAmbos;
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
                    "precio_alquiler", art.getString("precio_alquiler").replace(',', '.'),
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
        Ambo viejo = Ambo.findFirst("id = ?", art.get("id"));
        if (viejo != null) {
            Iterator it2 = ArticulosAmbos.find("ambo_id = ?", viejo.getString("id")).iterator();
            while (it2.hasNext()) {
                ArticulosAmbos artAmb = (ArticulosAmbos) it2.next();
                artAmb.delete();
            }
            ret = viejo.delete();
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
                    "precio_alquiler", art.getString("precio_alquiler").replace(',', '.'),
                    "talle", art.get("talle"));
            ret = viejo.saveIt();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }

    public boolean revisarStock(int id) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        int stock = Articulo.findById(id).getInteger("stock");
        Iterator it = ArticulosAmbos.find("articulo_id = ?", id).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                ArticulosAmbos artAmb = (ArticulosAmbos) it.next();
                Iterator it2 = ArticulosAmbos.find("id = ?", artAmb.get("ambo_id")).iterator();
                while (it2.hasNext()) {
                    ArticulosAmbos artAmb2 = (ArticulosAmbos) it2.next();
                    if (id != artAmb2.getInteger("articulo_id")) {
                        Articulo a = Articulo.findById(artAmb2.get("articulo_id"));
                        if (a != null) {
                            if (stock > a.getInteger("stock")) {
                                stock = a.getInteger("stock");
                            }
                        }
                    }
                }
                Ambo a = Ambo.findById(artAmb.get("ambo_id"));
                if (a != null) {
                    a.set("stock", stock);
                    ret = a.saveIt();
                } else {
                    ret = true;
                }
            }
        } else {
            ret = true;
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }
}
