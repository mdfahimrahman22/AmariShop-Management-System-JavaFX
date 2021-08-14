/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmariShop.Database;

import AmariShop.Models.User;
import java.awt.HeadlessException;
import java.sql.*;

public class UserAccount {
    Connection conn;
    
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Statement st;
    
    
    
    public UserAccount(Connection connection) {
        this.conn=connection;
    }
    
    public User getUserProfile(String email,String pass){
        String sql = "select * from " + "Users" + " where email=\'" + email + "\' and password=\'" + pass + "\'";
        try{
            st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(sql);
            if (rs.next()) {
                int uid=rs.getInt("UserID");
                int branchId=rs.getInt("BranchID");
                String name=rs.getString("name");
                String userEmail=rs.getString("email");
                String userPass=rs.getString("password");
                String address=rs.getString("address");
                String contact=rs.getString("contact");
                return new User(uid,branchId,name,userEmail,userPass,address,contact);
            }else{
                return null;
            }
        }catch(HeadlessException | SQLException e){
            return null;
        }
    }
    
    
    
}
