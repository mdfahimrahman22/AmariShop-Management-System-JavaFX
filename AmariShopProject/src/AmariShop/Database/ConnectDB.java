package AmariShop.Database;

import java.sql.*;

public class ConnectDB {

    public Connection connection;

    public ConnectDB() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=amarishopdb;selectMethod=cursor", "amarishopdb_user", "123456");
            System.out.println("Database Connection Successful.");
        } catch (Exception e) {
            System.out.println("Database Connection Failed.");
//            e.printStackTrace();
        }
    }

}
