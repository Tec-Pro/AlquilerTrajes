/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import java.util.List;
import modelos.Articulo;
import modelos.Cliente;
import modelos.Remito;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class Busqueda {

    /*
     * No hace falta distinguir entre mayúsculas y minúsculas.
     */
    /**
     *
     * @param id
     * @return Cliente asociado a esa id.
     */
    public Cliente buscarCliente(Object id) {
        Base.openTransaction();
        Cliente result = Cliente.findById(id);
        Base.commitTransaction();
        return result;
    }

    public List<Cliente> buscarCliente(String nombre) {
        Base.openTransaction();
        List<Cliente> result;
        result = Cliente.where("nombre like ?", "%" + nombre + "%");
        Base.commitTransaction();
        return result;
    }

    /**
     * @param nombre,
     * @param apellido e
     * @param id del cliente. Filtra los que tienen nombre a los pasados y
     * @return lista filtrada de clientes.
     */
    public List<Cliente> filtroCliente(String nombre, String codigo) {
        List<Cliente> result;
        Base.openTransaction();
        result = Cliente.where("nombre like ? and id like ?", "%" + nombre + "%", codigo + "%");
        Base.commitTransaction();
        return result;
    }

    /**
     * @param codigo,
     * @param equivalencia_farm Filtra aquellos que empiecen con el código
     * pasado y que contengan el equivalente en fram y marca.
     * @return lista filtrada de productos
     */
    public List<Articulo> filtroProducto(String codigo, String fram) {
        List<Articulo> result;
        Base.openTransaction();
        result = Articulo.where("es_articulo=1 and codigo like ? and equivalencia_fram like?", "%" + codigo + "%", "%" + fram + "%");
        Base.commitTransaction();
        return result;
    }

    public int ganancia(int numeroMes) {
        List<Remito> result;
        switch (numeroMes) {
            case 0:               
                return 0;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            default:
                break;                
        }
        return 0;
    }
}