package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class MySQLConnect {

    public static Connection Connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/projectqtdlieu", "root", "");
            System.out.println("Connect database successful");

        } catch (Exception ex) {
            System.out.println("Connect database unsuccessful");
            ex.printStackTrace();
        }
        return conn;
    }
}
