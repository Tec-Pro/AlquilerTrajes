/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import org.javalite.activejdbc.Model;

/**
 *
 * @author nico
 */
public class Articulo extends Model {
    
    
        private String nombreProveedor;


    public String getNombreProv() {
        return nombreProveedor;
    }


    public void setNombreProv(String nombreProv) {
        this.nombreProveedor =nombreProv;
    }
    
}
