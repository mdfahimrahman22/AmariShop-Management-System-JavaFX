/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package AmariShop.Dashboard;

import AmariShop.Database.ConnectDB;
import AmariShop.Database.UserAccount;
import AmariShop.FXMain;
import AmariShop.Models.Branch;
import AmariShop.Models.Employee;
import AmariShop.Models.User;
import AmariShop.Models.UserRole;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;

public class DashboardLayoutController implements Initializable {

    @FXML
    private Circle profileImg1, profileImg2;

    @FXML
    private ImageView Menu, MenuClose, Logout;

    @FXML
    private AnchorPane slider;

    @FXML
    private HBox salesBtn, customersBtn, employeesBtn, dashboardBtn, userProfileBtn, usersBtn, branchesBtn, userRolesBtn, productsBtn;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label copyrightLabel, userName, profileNameLabel, userRoleLabel, branchNameLabel, acIdLabel;

    @FXML
    private TextField emailTextField, contactTextField, nameTextField;

    @FXML
    private TextArea addressTextField;

    @FXML
    private ImageView editBtn;

    @FXML
    private Button updateBranchBtn, updateUserBtn, updateProfileBtn, addUserBtn, addBranchBtn, deleteBranchBtn;

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableView<UserRole> userRoleTable;

    @FXML
    private TableColumn<UserRole, Integer> userRoleIdCol;
    @FXML
    private TableColumn<UserRole, String> userRoleTitleCol, userRoleDescCol;

    @FXML
    private TableColumn<User, Integer> tableUserId;

    @FXML
    private TableColumn<User, String> tableUserName, tableUserRole, tableUserContact, tableUserEmail, tableUserBranch, tableUserAddress;

    @FXML
    private TableView<Branch> branchTable;
    @FXML
    private TableColumn<Branch, Integer> branchIdCol;
    @FXML
    private TableColumn<Branch, String> branchNameCol, branchAddressCol, branchContactCol, branchEmailCol;

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> emIdCol, emSalaryCol;
    @FXML
    private TableColumn<Employee, String> emNameCol, emEmailCol, emBranchCol, emPositionCol, emContactCol, emAddressCol;

    @FXML
    private Button updatePassBtn, deleteUserBtn;

    @FXML
    private PasswordField oldPassField, newPassField, confirmPassField;

    private User user;
    private Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    public DashboardLayoutController() {
    }

    public DashboardLayoutController(User user, Connection connection) {
        this.user = user;
        this.connection = connection;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCopyrightLabelText();
        setOnClickListeners();
        setProfileSettingsEditable(false);
        setProfileImage();
        initializeEmployeeTable();
        initializeBranchTable();
        initializeUserRoleTable();
        initializeUserTable();
    }

    public void initializeUserTable() {
        tableUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableUserName.setCellValueFactory(cellData -> cellData.getValue().getName());
        tableUserBranch.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        tableUserEmail.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        tableUserContact.setCellValueFactory(cellData -> cellData.getValue().getContact());
        tableUserAddress.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        tableUserRole.setCellValueFactory(new PropertyValueFactory<>("userRoleTitle"));
    }

    public void initializeUserRoleTable() {
        userRoleIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userRoleTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        userRoleDescCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
    }

    public void initializeBranchTable() {
        branchIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        branchNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        branchAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        branchContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        branchEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void initializeEmployeeTable() {
        emIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        emNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        emAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emSalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        emBranchCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        emPositionCol.setCellValueFactory(new PropertyValueFactory<>("positionTitle"));
    }

    public void initializeDashboardLayout(Connection connection, User user) {
        this.user = user;
        this.connection = connection;
        setUserInfo(user);
        setUserRoleTableData(connection);
    }

    ObservableList<UserRole> userRoleList = FXCollections.observableArrayList();

    public void setUserRoleTableData(Connection connection) {
        userRoleTable.getItems().clear();
        String sql = "select * from UserRole";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                userRoleList.add(new UserRole(rs.getInt("UserRoleID"), rs.getString("user_role_title"), rs.getString("user_role_description")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        userRoleTable.setItems(userRoleList);

    }

    public void setUserInfo(User user) {
        this.user = user;
        userName.textProperty().bind(user.getName());
        acIdLabel.setText("Account ID: " + Integer.toString(user.getId()));
        profileNameLabel.textProperty().bind(user.getName());
        nameTextField.setText(user.getName().get());
        emailTextField.setText(user.getEmail().get());
        contactTextField.setText(user.getContact().get());
        addressTextField.setText(user.getAddress().get());
        branchNameLabel.setText(user.getBranchName());
        userRoleLabel.setText(user.getUserRoleTitle());

    }

    ObservableList<Branch> branchList = FXCollections.observableArrayList();

    private void setBranchTableData() {
        branchTable.getItems().clear();
        String sql = "select * from Branch";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("BranchID");
                String name = rs.getString("branch_name");
                String address = rs.getString("branch_address");
                String contact = rs.getString("branch_contact");
                String email = rs.getString("branch_email");
                branchList.add(new Branch(id, name, address, contact, email));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        branchTable.setItems(branchList);

    }

    ObservableList<User> userList = FXCollections.observableArrayList();

    public void setUsersTableData() {
        usersTable.getItems().clear();
        String sql = "select u.userid,u.name,u.email,u.address,u.contact,\n"
                + "u.created_at,b.branchid,b.branch_name,r.UserRoleID,r.user_role_title from \n"
                + "Users u \n"
                + "inner join Branch b on u.BranchID= b.BranchID \n"
                + "inner join UserRole r on u.UserRoleID=r.UserRoleID";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String branch = rs.getString("branch_name");
                String role = rs.getString("user_role_title");
                int roleId = rs.getInt("UserRoleID");
                int id = rs.getInt("userid");
                int branchId = rs.getInt("branchid");

                userList.add(new User(email, name, address, contact, branch, role, id, branchId, roleId));
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception:" + ex);
        }

        usersTable.setItems(userList);

    }

    ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    public void setEmployeeTableData() {
        employeeTable.getItems().clear();
        String sql = "select em.EmployeeID,em.branchid,em.employee_name,em.employee_email"
                + ",em.employee_address,em.employee_contact,\n"
                + "em.employee_salary,b.branchid,b.branch_name,p.position_title,p.EmployeePositionID from \n"
                + "Employee em\n"
                + "inner join Branch b on em.BranchID= b.BranchID \n"
                + "inner join EmployeePosition p on em.EmployeePositionID=p.EmployeePositionID ";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("EmployeeID");
                int bId = rs.getInt("branchid");
                int pId = rs.getInt("EmployeePositionID");
                int salary = rs.getInt("employee_salary");
                String name = rs.getString("employee_name");
                String email = rs.getString("employee_email");
                String address = rs.getString("employee_address");
                String contact = rs.getString("employee_contact");
                String bName = rs.getString("branch_name");
                String pTitle = rs.getString("position_title");
                employeeList.add(new Employee(id, bId, bName, pId, pTitle, salary, name, email, contact, address));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        employeeTable.setItems(employeeList);

    }

    private void setProfileSettingsEditable(boolean editable) {
        nameTextField.setEditable(editable);
        emailTextField.setEditable(editable);
        contactTextField.setEditable(editable);
        addressTextField.setEditable(editable);
    }

    public void animateOnWidthChange(final AnchorPane pane, double width) {
        Duration cycleDuration = Duration.millis(350);
        Timeline timeline = new Timeline(
                new KeyFrame(cycleDuration,
                        new KeyValue(pane.maxWidthProperty(), width, Interpolator.EASE_BOTH))
        );
        timeline.play();
        timeline.setOnFinished(event -> {

        });
    }

    private void setOnClickListeners() {
        Menu.setOnMouseClicked(event -> {
            animateOnWidthChange(slider, 237);
            Menu.setVisible(false);
            MenuClose.setVisible(true);
        });

        MenuClose.setOnMouseClicked(event -> {
            animateOnWidthChange(slider, 80);
            Menu.setVisible(true);
            MenuClose.setVisible(false);
        });
        dashboardBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(0);
        });
        userProfileBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(1);
        });
        userName.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(1);
        });
        usersBtn.setOnMouseClicked(event -> {
            setUsersTableData();
            tabPane.getSelectionModel().select(2);
        });
        branchesBtn.setOnMouseClicked(event -> {
            setBranchTableData();
            tabPane.getSelectionModel().select(3);
        });

        userRolesBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(4);
        });
        customersBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(7);
        });
        employeesBtn.setOnMouseClicked(event -> {
            setEmployeeTableData();
            tabPane.getSelectionModel().select(6);
        });
        productsBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(5);
        });
        salesBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(8);
        });
        deleteBranchBtn.setOnMouseClicked(event -> {
            Branch selectedBranch = branchTable.getSelectionModel().getSelectedItem();
            if (selectedBranch == null) {
                FXMain.showNotification("No Selected Row", "Please select a row to delete.", "warning");
            } else {
                if (user.getUserRoleId() == 1) {
                    String sql = "delete from Branch where BranchID=?";
                    try {
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, selectedBranch.getId());
                        int res = ps.executeUpdate();
                        if (res == 1) {
                            branchTable.getItems().removeAll(selectedBranch);
                            FXMain.showNotification("Deleted Successfully", "Branch is deleted successfully.", "success");
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }

                } else {
                    FXMain.showNotification("Permission Denyed", "You are not allowed to delete branch.", "warning");
                }
            }

        });

        deleteUserBtn.setOnMouseClicked(event -> {
            User selectedUser = usersTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                FXMain.showNotification("No Selected Row", "Please select a row to delete.", "warning");
            } else {
                if (user.getUserRoleId() == 1) {
                    String sql = "delete from users where UserID=?";
                    try {
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, selectedUser.getId());
                        int res = ps.executeUpdate();
                        if (res == 1) {
                            usersTable.getItems().removeAll(selectedUser);
                            FXMain.showNotification("User Deleted Successfully", "User is deleted successfully.", "success");
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }

                } else {
                    FXMain.showNotification("Permission Denyed", "You are not allowed to add user.", "warning");
                }
            }

        });
        updateBranchBtn.setOnMouseClicked(event -> {
            Branch selectedBranch = branchTable.getSelectionModel().getSelectedItem();
            if (selectedBranch == null) {
                FXMain.showNotification("No Selected Row", "Please select a row to update branch.", "warning");
                return;
            }

            if (user.getUserRoleId() == 1) {
                new FXMain().openUpdateBranch(connection, branchTable, selectedBranch);
            } else if (user.getUserRoleId() == 2 && user.getBranchId() == selectedBranch.getId()) {
                new FXMain().openUpdateBranch(connection, branchTable, selectedBranch);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to update this branch.", "warning");
            }
        });

        updateUserBtn.setOnMouseClicked(event -> {
            if (user.getUserRoleId() == 1) {
                User selectedUser = usersTable.getSelectionModel().getSelectedItem();
                if (selectedUser == null) {
                    FXMain.showNotification("No Selected Row", "Please select a row to update user.", "warning");
                } else {
                    new FXMain().openUpdateUser(connection, usersTable, selectedUser);
                }
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to update user.", "warning");
            }
        });

        addBranchBtn.setOnMouseClicked(event -> {
            if (user.getUserRoleId() == 1) {
                new FXMain().openAddBranch(connection, branchList);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to delete user.", "warning");
            }
        });
        addUserBtn.setOnMouseClicked(event -> {
            if (user.getUserRoleId() == 1) {
                new FXMain().openAddUser(connection, userList);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to add user.", "warning");
            }
        });
        addUserBtn.setOnMouseClicked(event -> {
            if (user.getUserRoleId() == 1) {
                new FXMain().openAddUser(connection, userList);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to add user.", "warning");
            }
        });
        Logout.setOnMouseClicked(event -> {
            new FXMain().openLogin(event, connection);
        });
        editBtn.setOnMouseClicked(event -> {
            setProfileSettingsEditable(true);
        });
        updatePassBtn.setOnMouseClicked(event -> {
            String oldPass = oldPassField.getText();
            String newPass = newPassField.getText();
            String confirmPass = confirmPassField.getText();
            if (newPass.equals(confirmPass)) {
                UserAccount ua = new UserAccount(connection);
                int res = ua.updatePassword(user.getId(), oldPass, newPass);
                if (res == 1) {
                    FXMain.showNotification("Update Successful", user.getName().get() + ", Your password has been updated successfully", "confirm");
                    oldPassField.setText("");
                    newPassField.setText("");
                    confirmPassField.setText("");
                } else {
                    FXMain.showNotification("Invalid Credentials", "Old Password is wrong.", "warning");
                }

            } else {
                FXMain.showNotification("Invalid Credentials", "Confirm Password Not Matching", "warning");
            }

        });

        updateProfileBtn.setOnMouseClicked(event -> {
            setProfileSettingsEditable(false);
            UserAccount userAccount = new UserAccount(connection);
            String newEmail = emailTextField.getText();
            String newName = nameTextField.getText();
            String newContact = contactTextField.getText();
            String newAddress = addressTextField.getText();
            int res = userAccount.updateProfile(user.getId(), newEmail, newName, newContact, newAddress);
            if (res == 1) {
                user.setName(newName);
                user.setAddress(newAddress);
                user.setEmail(newEmail);
                user.setContact(newContact);
                FXMain.showNotification("Update Successful", user.getName().get() + ", Your profile has been updated successfully", "confirm");
            } else {
                FXMain.showNotification("Update Failed", "Sorry " + user.getName().get() + ", something went wrong.", "error");
            }
        });

    }

    private void setCopyrightLabelText() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        copyrightLabel.setText("Copyright \u00a9 " + yearInString + ", AmariShop. All rights are reserved.");
    }

    private void setProfileImage() {
        Image profilePic = new Image("https://www.w3schools.com/howto/img_avatar.png");
        profileImg1.setFill(new ImagePattern(profilePic));
        profileImg2.setFill(new ImagePattern(profilePic));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
