package GIAODIENQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCCONNECTION {
    public static Connection getConnection(){
        String url = "jdbc:sqlserver://DESKTOP-P36CIA4;databaseName=QLSANBONG";
        String user = "sa";
        String pass = "123";
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            System.out.println("Loi ket noi JDBC");
        }
        return null;
    }      
}


