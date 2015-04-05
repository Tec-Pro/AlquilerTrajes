/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import BD.BaseDatos;
import java.sql.SQLException;
import org.javalite.activejdbc.Model;
import static org.javalite.activejdbc.Model.findById;

/**
 *
 * @author jacinto
 */
public class Contador extends Model {

    public void sumar() throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();   
        Contador c = findById(1);
        c.setInteger("numero", c.getInteger("numero") + 1);
        c.saveIt();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    public boolean mayor30() throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();   
        Contador c = findById(1);
        if (c.getInteger("numero") >= 30 && c.getInteger("clave1") == 0) {
            BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
            return true;
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return false;
    }

    public boolean mayor60() throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();   
        Contador c = findById(1);
        if (c.getInteger("numero") >= 60 && c.getInteger("clave2") == 0) {
            BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
            return true;
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return false;
    }

    public void validClave1() throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();   
        Contador c = findById(1);
        c.setInteger("clave1", 1);
        c.saveIt();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }

    public void validClave2() throws SQLException {
        
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();   
        Contador c = findById(1);
        c.setInteger("clave2", 1);
        c.saveIt();
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
    }
}
