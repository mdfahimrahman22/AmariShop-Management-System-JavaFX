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
        this.conn = connection;
    }

    public User getUserProfile(String email, String pass) {
        String sql = String.format("select "
                + "u.userid,u.name,u.email,u.password,u.address,u.contact,u.created_at,"
                + "b.branchid,b.branch_name,r.UserRoleID,r.user_role_title from \n"
                + "Users u \n"
                + "inner join Branch b on u.BranchID= b.BranchID \n"
                + "inner join UserRole r on u.UserRoleID=r.UserRoleID \n"
                + "where u.email='%s' and u.password='%s' ",email,pass);
        try {
            st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String userEmail = rs.getString("email");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String branchName = rs.getString("branch_name");
                String userRoleTitle = rs.getString("user_role_title");
                int uid = rs.getInt("userid");
                int branchId = rs.getInt("branchid");
                int userRoleId = rs.getInt("UserRoleID");
                
                return new User(userEmail,name, address, contact
                        ,branchName,userRoleTitle,uid,branchId,userRoleId);
            } else {
                return null;
            }
        } catch (HeadlessException | SQLException e) {
            return null;
        }
    }

}
