package AmariShop.Database;

import AmariShop.FXMain;
import java.sql.*;

public class ConnectDB {

    public static Connection getConnection() {
        
        try {
            String dbuser="amarishopdb_user";
            String dbuser_pass="123456";
            String dbname="amarishopdb";
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName="+dbname+";selectMethod=cursor", dbuser,dbuser_pass);
            System.out.println("Database Connection Successful.");
            return connection;
        } catch (Exception e) {
            FXMain.showNotification("Connection Failed", "Can't connect to database.", "error");
            return null;
//            e.printStackTrace();
        }
    }
 
}
