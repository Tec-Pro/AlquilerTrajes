/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import BD.BaseDatos;
import java.sql.SQLException;
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
     * @throws java.sql.SQLException
     */
    public Cliente buscarCliente(Object id) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Cliente result = Cliente.findById(id);
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }

    public List<Cliente> buscarCliente(String nombre) throws SQLException {
        BaseDatos.abrirBase();
        Base.openTransaction();
        List<Cliente> result;
        result = Cliente.where("nombre like ?", "%" + nombre + "%");
        Base.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    /*
    *Busqueda de clientes
    *Retorna una lista con los clientes que contengan en alguna parte de su id
    *el numero pasado por parametro
    */
    public List<Cliente> buscarClientes(Object id) throws SQLException {
        BaseDatos.abrirBase();
        Base.openTransaction();
        List<Cliente> result;
        result = Cliente.where("id like ?", "%" + id + "%");
        Base.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    
    /*
    *Busqueda de clientes
    *Retorna una lista con los clientes que contengan en alguna parte de su dni
    *o de su id, el numero pasado por parametro
    */
    public List<Cliente> buscarClientesPorIDyDni(Object num) throws SQLException {
        BaseDatos.abrirBase();
        Base.openTransaction();
        List<Cliente> result;
        result = Cliente.where("id like ? or dni like ?", "%" + num + "%","%" + num + "%");
        Base.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }

    /**
     * @param nombre,
     * @param codigo
     * @return lista filtrada de clientes.
     * @throws java.sql.SQLException
     */
    public List<Cliente> filtroCliente(String nombre, String codigo) throws SQLException {
        List<Cliente> result;
        BaseDatos.abrirBase();
        Base.openTransaction();
        result = Cliente.where("nombre like ? and id like ?", "%" + nombre + "%", codigo + "%");
        Base.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }

    /**
     * @param codigo,
     * @param fram Filtra aquellos que empiecen con el código pasado y que
     * contengan el equivalente en fram y marca.
     * @return lista filtrada de productos
     * @throws java.sql.SQLException
     */
    public List<Articulo> filtroProducto(String codigo, String fram) throws SQLException {
        List<Articulo> result;
        BaseDatos.abrirBase();
        Base.openTransaction();
        result = Articulo.where("es_articulo=1 and codigo like ? and equivalencia_fram like?", "%" + codigo + "%", "%" + fram + "%");
        Base.commitTransaction();
        BaseDatos.cerrarBase();
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
