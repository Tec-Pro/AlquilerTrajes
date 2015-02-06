/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.math.BigDecimal;
import java.util.LinkedList;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Model;

/**
 *
 * @author jacinto
 */
public class Venta extends Model{
    
      public Venta() {
        this.productos = null;
        this.preciosFinales = null;
    }
   
    //Lista de pares <Producto,cantidad>
    private LinkedList<Pair> productos;
    private LinkedList<BigDecimal> preciosFinales;

    public LinkedList<Pair> getProductos() {
        return productos;
    }
        

    public void setProductos(LinkedList<Pair> productos) {
        this.productos = productos;
    }

    public LinkedList<BigDecimal> getPreciosFinales() {
        return preciosFinales;
    }

    public void setPreciosFinales(LinkedList<BigDecimal> preciosFinales) {
        this.preciosFinales = preciosFinales;
    }
    
    
}
