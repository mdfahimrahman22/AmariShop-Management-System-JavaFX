/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmariShop.Database;

import AmariShop.FXMain;
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
    
    public int updatePassword(int id, String oldPass, String newPass) {
        String sql = String.format("update users set password='%s' where UserID=%d and password='%s'", newPass, id, oldPass);
        try {
            st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return st.executeUpdate(sql);
        } catch (SQLException sqlex) {
            return 0;
        }catch(Exception ex){
            System.out.println(ex);
            return 0;
        }
    }

    public int updateProfile(int id, String email, String name, String contact, String address) {
        String sql = String.format("update users set name='%s', email='%s',"
                + "contact='%s',address='%s' where userid=%d ", name, email, contact, address, id);

        try {
            st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return st.executeUpdate(sql);

        } catch (SQLException sqlex) {
            FXMain.showNotification("SQL Exception", sqlex.toString(), "error");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public User getUserProfile(String email, String pass) {
        String sql = String.format("select "
                + "u.userid,u.name,u.email,u.password,u.address,u.contact,u.created_at,"
                + "b.branchid,b.branch_name,r.UserRoleID,r.user_role_title from \n"
                + "Users u \n"
                + "inner join Branch b on u.BranchID= b.BranchID \n"
                + "inner join UserRole r on u.UserRoleID=r.UserRoleID \n"
                + "where u.email='%s' and u.password='%s' ", email, pass);
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

                return new User(userEmail, name, address, contact,
                        branchName, userRoleTitle, uid, branchId, userRoleId);
            } else {
                return null;
            }
        } catch (HeadlessException | SQLException e) {
            return null;
        }
    }

}
