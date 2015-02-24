/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import BD.BaseDatos;
import java.sql.SQLException;
import java.util.List;
import modelos.Ambo;
import modelos.Articulo;

/**
 *
 * @author eze
 */
public class BusquedaArticulo {
    
    /*
    * Dado un arreglo de id de articulos, retorno una lista con todos los
    * articulos correspondientes a dichos id.
    */
    public List<Articulo> buscarArticuloPorId(int[] ids) throws SQLException{
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        List<Articulo> result = null;
        for(int i=0;i<ids.length;i++){
            result.add((Articulo) Articulo.findById(ids[i]));
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    
    /*
    * Dado un arreglo de id de ambos, retorno una lista con todos los
    * ambos correspondientes a dichos id.
    */
    public List<Ambo> buscarAmboPorId(int[] ids) throws SQLException{
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        List<Ambo> result = null;
        for(int i=0;i<ids.length;i++){
            result.add((Ambo) Ambo.findById(ids[i]));
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    
    /*
    *Busqueda de articulos
    *Retorna una lista con los articulos que contengan en alguna parte de su modelo,
    *marca,talle o tipo, la consulta pasada por parametro.
    */
    public List<Articulo> buscarArticulos(Object consulta) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        List<Articulo> result;
        result = Articulo.where("modelo like ? or marca like ? or talle like ? or tipo like ?", "%" + consulta + "%","%" + consulta + "%", "%" + consulta + "%","%" + consulta + "%");
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
    
    /*
    *Busqueda de clientes
    *Retorna una lista con los clientes que contengan en alguna parte de su dni
    *o de su id, el numero pasado por parametro
    */
    public List<Ambo> buscarAmbos(Object consulta) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        List<Ambo> result;
        if(consulta.equals("ambo")){
            result = Ambo.findAll();
        }else{
            result = Ambo.where("nombre like ? or marca like ? or talle like ?", "%" + consulta + "%","%" + consulta + "%", "%" + consulta + "%");
        }
        BaseDatos.commitTransaction();
        BaseDatos.cerrarBase();
        return result;
    }
}
