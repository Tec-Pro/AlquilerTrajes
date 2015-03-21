/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import BD.BaseDatos;
import java.sql.SQLException;
import modelos.Cliente;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ABMCliente {

    public Cliente getCliente(Cliente c) throws SQLException {
        Cliente c2 = Cliente.first("nombre = ?", c.get("nombre"));
        return c2;
    }

    public boolean findCliente(Cliente c) throws SQLException {
        boolean ret = false;
        ret = (Cliente.first("nombre = ?", c.get("nombre")) != null);
        return ret;
    }

    public boolean alta(Cliente c) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Cliente nuevo = Cliente.create("nombre", c.get("nombre"), "telefono",
                c.get("telefono"), "celular", c.get("celular"),
                "direccion", c.get("direccion"), "dni", c.get("dni"));
        ret = nuevo.saveIt();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }

    public boolean baja(Cliente c) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Cliente viejo = Cliente.findById(c.getId());
        if (viejo != null) {
            ret = viejo.delete();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;

    }

    public boolean modificar(Cliente c) throws SQLException {
        boolean ret = false;
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Cliente viejo = Cliente.findById(c.getId());
        if (viejo != null) {
            viejo.set("nombre", c.get("nombre"), "telefono", c.get("telefono"), "celular", c.get("celular"), "direccion", c.get("direccion"), "dni", c.get("dni"));
            ret = viejo.saveIt();
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return ret;
    }
}
