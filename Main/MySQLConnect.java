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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/projectqtdlieu?"
                    + "user=root");
           System.out.println("Noi ket thanh cong");

        } catch (Exception ex) { //xử lý ngoại lệ nếu có
            System.out.println("Noi ket khong thanh cong");
            ex.printStackTrace();
        }
        return conn;
    }
}
