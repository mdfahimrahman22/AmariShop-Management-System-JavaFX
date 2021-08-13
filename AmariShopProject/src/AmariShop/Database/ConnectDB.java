package AmariShop.Database;

import java.sql.*;

public class ConnectDB {

    public Connection connection;

    public ConnectDB() {
        try {
            String dbuser="amarishopdb_user";
            String dbuser_pass="123456";
            String dbname="amarishopdb";
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName="+dbname+";selectMethod=cursor", dbuser,dbuser_pass);
            System.out.println("Database Connection Successful.");
        } catch (Exception e) {
            System.out.println("Database Connection Failed.");
//            e.printStackTrace();
        }
    }

}
