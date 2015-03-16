/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import BD.BaseDatos;
import java.sql.SQLException;
import modelos.Remito;

/**
 *
 * @author eze
 */
public class ABMRemito {

    private Integer ultimoId; //id del ultimo remito creado

    public Integer getUltimoId() {
        return ultimoId;
    }

    //Da de alta un remito en la BD
    public boolean alta(Remito r) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        //Si el remito no existe, lo creo
        if (Remito.first("numero = ?", r.get("numero")) == null) {
            Remito nuevo = Remito.create("numero", r.get("numero"), "fecha_de_remito",
                    r.get("fecha_de_remito"), "cliente_id", r.get("cliente_id"),
                    "total", r.get("total"), "senia", r.get("senia"),"cerrado", r.get("cerrado"));
            nuevo.saveIt();
            ultimoId = nuevo.getInteger("id");
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return true;
        } else {
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return false;
        }
    }

    //Da de baja un remito en la BD (lo elimina), junto con todos sus articulos relacionados
    public boolean baja(Remito r) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Remito viejo = Remito.findById(r.getId());
        if (viejo != null) {
            viejo.deleteCascadeShallow();
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return true;
        }else{
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return false;
        }
    }

    //Modifica los datos de un remito especifico (remito identificado por su id)
    public boolean modificar(Remito r) throws SQLException {
        BaseDatos.abrirBase();
        BaseDatos.openTransaction();
        Remito viejo = Remito.findById(r.getId());
        if (viejo != null) {

            viejo.set("numero", r.get("numero"), "fecha_de_remito",
                    r.get("fecha_de_remito"), "cliente_id", r.get("cliente_id"),
                    "total", r.get("total"), "senia", r.get("senia"),"cerrado",r.get("cerrado")).saveIt();
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return true;
        } else {
            BaseDatos.commitTransaction();
            BaseDatos.cerrarBase();
            return false;
        }
    }

}
