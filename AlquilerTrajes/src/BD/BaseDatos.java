/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.SQLException;
import org.javalite.activejdbc.Base;

/**
 *
 * @author eze
 */
public class BaseDatos extends Base{
    
    public static void abrirBase() throws SQLException{
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/alquilerTraje", "root", "root");
        Base.connection().setAutoCommit(false);
    }
    
    public static void cerrarBase(){
        Base.close();
    }
}
