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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.sql.*;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author fahim
 */
public class AddUserLayoutController implements Initializable {

    @FXML
    private Button addBtn, updateBtn;

    @FXML
    private ImageView closeBtn;
    @FXML
    private TextField nameField, emailField, contactField;
    @FXML
    private PasswordField passField;

    @FXML
    private TextArea addressField;

    @FXML
    private ComboBox branchComboBox, userRoleComboBox;

    private Connection connection;
    HashMap<String, Integer> branches = new HashMap<String, Integer>();
    HashMap<String, Integer> userRoles = new HashMap<String, Integer>();

    private ObservableList<User> userList;
    private User user;
    private TableView<User> usersTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setOnClickListeners();
    }

    public void initializeAddUserLayout(Connection connection, ObservableList<User> userList) {
        updateBtn.setVisible(false);
        addBtn.setVisible(true);
        this.connection = connection;
        this.userList = userList;
        setBranchComboBoxItems(connection);
        setUserRoleComboBoxItems(connection);
    }

    public void initializeUpdateUserLayout(Connection connection, TableView<User> usersTable, User user) {
        updateBtn.setVisible(true);
        addBtn.setVisible(false);
        passField.setVisible(false);
        this.connection=connection;
        this.usersTable = usersTable;
        setBranchComboBoxItems(connection);
        setUserRoleComboBoxItems(connection);
        setUserInfo(user);
    }

    private void setOnClickListeners() {
        closeBtn.setOnMouseClicked(e -> {
            FXMain.closePanel(e);
        });
        addBtn.setOnMouseClicked(e -> {
            try {
                String selectedUserRole = userRoleComboBox.getValue().toString();
                String selectedBranch = branchComboBox.getValue().toString();
                String name = nameField.getText();
                String email = emailField.getText();
                String pass = passField.getText();
                String contact = contactField.getText();
                String address = addressField.getText();
                if (email.trim().isEmpty() || pass.trim().isEmpty()) {
                    throw new IllegalArgumentException("Illegal Arguments");
                } else {
                    addNewUser(userRoles.get(selectedUserRole), branches.get(selectedBranch),
                            name, email, pass, selectedBranch, selectedUserRole,
                            contact, address);
                }

            } catch (NullPointerException np) {
                FXMain.showNotification("Invalid Credentials", "Please fill up the form properly. Branch and User Role must be selected.", "warning");
            } catch (IllegalArgumentException iae) {
                FXMain.showNotification("Invalid Credentials", "Please fill up the form properly. Email and Password must be there.", "warning");
            }
        });
        updateBtn.setOnMouseClicked(e -> {
            try {
                String selectedUserRole = userRoleComboBox.getValue().toString();
                String selectedBranch = branchComboBox.getValue().toString();
                String name = nameField.getText();
                String email = emailField.getText();
                String contact = contactField.getText();
                String address = addressField.getText();
                
                if (email.trim().isEmpty()) {
                    throw new IllegalArgumentException("Illegal Arguments");
                } else {
                    updateUser(user.getId(), userRoles.get(selectedUserRole), branches.get(selectedBranch),
                            name, email, contact, address, selectedBranch, selectedUserRole
                    );
                }
            } catch (NullPointerException np) {
                FXMain.showNotification("Invalid Credentials", "Please fill up the form properly. Branch and User Role must be selected.", "warning");
            } catch (IllegalArgumentException iae) {
                FXMain.showNotification("Invalid Credentials", "Please fill up the form properly. Email and Password must be there.", "warning");
            }
        });
    }
    private void updateUser(int userId, int userRoleId, int branchId, String name, String email, String contact, String address, String branchName, String userRoleTitle) {
        String sql = "update users set BranchID=?,UserRoleID=?,name=?, email=?,"
                + "contact=?,address=? where userid=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, branchId);
            ps.setInt(2, userRoleId);
            ps.setString(3, name);
            ps.setString(4, email);
            ps.setString(5, contact);
            ps.setString(6, address);
            ps.setInt(7, userId);
            int res = ps.executeUpdate();
            if (res == 1) {
                usersTable.getItems().removeAll(user);
                user=new User(email, name, address, contact, branchName, userRoleTitle, userId, branchId, userRoleId);
                usersTable.getItems().add(user);
                FXMain.showNotification("User Info Updated", "Successfully updated user information.", "success");
            } else {
                FXMain.showNotification("Failed to update the user", "Something went worng.", "warning");
            }
        } catch (SQLException ex) {
            FXMain.showNotification("SQL Error", "Can't create new user.", "error");
        }

    }


    public void setUserInfo(User user) {
        this.user = user;
        nameField.setText(user.getName().get());
        emailField.setText(user.getEmail().get());
        contactField.setText(user.getContact().get());
        addressField.setText(user.getAddress().get());
        branchComboBox.getSelectionModel().select(user.getBranchName());
        userRoleComboBox.getSelectionModel().select(user.getUserRoleTitle());
    }

    private void addNewUser(int userRoleId, int branchId, String name, String email, String password, String branchName, String userRoleTitle, String contact, String address) {
        String sql = "insert into users(UserRoleID,BranchID,name,email,password,contact,address) values \n"
                + "(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userRoleId);
            ps.setInt(2, branchId);
            ps.setString(3, name);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, contact);
            ps.setString(7, address);
            int res = ps.executeUpdate();
            if (res == 1) {
                ResultSet rs = connection.prepareStatement(String.format("select UserID from users where email='%s'", email)).executeQuery();
                int uid = 0;
                while (rs.next()) {
                    uid = rs.getInt("UserID");
                }
                userList.add(new User(email, name, address, contact, branchName, userRoleTitle, uid, branchId, userRoleId));
                nameField.setText("");
                emailField.setText("");
                passField.setText("");
                contactField.setText("");
                addressField.setText("");
                FXMain.showNotification("New User Created", "Successfully created new user", "success");
            } else {
                FXMain.showNotification("Failed to create new user", "Something went worng.", "warning");
            }
        } catch (SQLException ex) {
            FXMain.showNotification("SQL Error", "Can't create new user.", "error");
        }
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
