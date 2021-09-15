/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmariShop.Dashboard;

import AmariShop.FXMain;
import AmariShop.Models.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.sql.*;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author fahim
 */
public class AddUserLayoutController implements Initializable {

    @FXML
    private Button closeBtn, addBtn;
    @FXML
    private TextField nameField, emailField;
    @FXML
    private PasswordField passField;

    @FXML
    private ComboBox branchComboBox, userRoleComboBox;

    private Connection connection;
    HashMap<String, Integer> branches = new HashMap<String, Integer>();
    HashMap<String, Integer> userRoles = new HashMap<String, Integer>();

    private User user;
    private ObservableList<User> userList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setOnClickListeners();
    }

    private void setOnClickListeners() {

        closeBtn.setOnMouseClicked(e -> {
            FXMain.closePanel(e);
        });
        addBtn.setOnMouseClicked(e -> {
            String selectedUserRole = userRoleComboBox.getValue().toString();
            String selectedBranch = branchComboBox.getValue().toString();
            String name = nameField.getText();
            String email = emailField.getText();
            String pass = passField.getText();
            addNewUser(userRoles.get(selectedUserRole), branches.get(selectedBranch), name, email, pass,selectedBranch,selectedUserRole);
        });
    }

    private void addNewUser(int userRoleId, int branchId, String name, String email, String password,String branchName,String userRoleTitle) {
        String sql = "insert into users(UserRoleID,BranchID,name,email,password) values \n"
                + "(?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userRoleId);
            ps.setInt(2, branchId);
            ps.setString(3, name);
            ps.setString(4, email);
            ps.setString(5, password);
            int res = ps.executeUpdate();
            if (res == 1) {
                ResultSet rs=connection.prepareStatement(String.format("select UserID from users where email='%s'",email)).executeQuery();
                int uid=0;
                while(rs.next()){
                    uid=rs.getInt("UserID");
                }
                System.out.println(uid);
                userList.add(new User(email,name,"","",branchName,userRoleTitle,uid,branchId,userRoleId));
                nameField.setText("");
                emailField.setText("");
                passField.setText("");
                FXMain.showNotification("New User Created", "Successfully created new user", "success");
            } else {
                FXMain.showNotification("Failed to create new user", "Something went worng.", "warning");
            }
        } catch (SQLException ex) {
            FXMain.showNotification("SQL Error", "Can't create new user.", "error");
        }
    }

    public ObservableList<User> getUserList() {
        return userList;
    }

    public void setUserList(ObservableList<User> userList) {
        this.userList = userList;
    }
    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setUserRoleComboBoxItems(Connection connection) {
        String sql = "select * from UserRole";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userRoles.put(rs.getString("user_role_title"), rs.getInt("UserRoleID"));
                userRoleComboBox.getItems().add(rs.getString("user_role_title"));
            }
        } catch (SQLException ex) {
            System.out.println("Can't get user roles");
        }
    }

    public void setBranchComboBoxItems(Connection connection) {
        String sql = "select * from Branch";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String branchName = rs.getString("branch_name");
                branches.put(branchName, rs.getInt("BranchID"));
                branchComboBox.getItems().add(branchName);
            }
        } catch (SQLException ex) {
            System.out.println("Sql exception");
            System.out.println(ex);
        }
    }

}
