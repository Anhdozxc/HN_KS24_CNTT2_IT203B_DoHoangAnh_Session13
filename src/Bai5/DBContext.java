package Bai5;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/Hospital_Bai5";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    }
}