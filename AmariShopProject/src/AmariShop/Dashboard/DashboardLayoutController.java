/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package AmariShop.Dashboard;

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
import java.util.HashMap;
import javafx.scene.control.ComboBox;

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
    private TextField emNameField, emContactField, emEmailField, emSalaryField, usersNameField, usersEmailField, usersContactField, branchNameField, branchEmailField, branchContactField, emailTextField, contactTextField, nameTextField;

    @FXML
    private ComboBox emPositionComboBox, emBranchComboBox, usersBranchComboBox, usersRoleComboBox, usersSearchByComboBox, usersOrderByComboBox, branchSearchByComboBox, branchOrderByComboBox, employeeOrderByComboBox, employeeSearchByComboBox, employeeBranchComboBox, employeePositionComboBox;

    @FXML
    private TextArea emAddressField, addressTextField, usersAddressField, branchAddressField;

    @FXML
    private ImageView clearEmployeeTableBtn, addEmployeeBtn, deleteEmployeeBtn, updateEmployeeBtn, clearBranchTableBtn, clearUserTableBtn, editBtn, updateUserBtn, updateBranchBtn, addBranchBtn, deleteBranchBtn, addUserBtn, deleteUserBtn;

    @FXML
    private Button updatePassBtn, updateProfileBtn;
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
    private PasswordField oldPassField, newPassField, confirmPassField, usersPassField;

    private User user;
    private Connection connection;
    PreparedStatement ps;
    ResultSet rs;
    HashMap<String, Integer> branches = new HashMap<String, Integer>();
    HashMap<String, Integer> userRoles = new HashMap<String, Integer>();
    HashMap<String, Integer> positions = new HashMap<String, Integer>();

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

    public void setPositionComboBoxItems(ComboBox comboBox) {
        String sql = "select * from EmployeePosition";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            positions.clear();
            comboBox.getItems().clear();
            while (rs.next()) {
                String branchName = rs.getString("position_title");
                positions.put(branchName, rs.getInt("EmployeePositionID"));
                comboBox.getItems().add(branchName);
            }
        } catch (SQLException ex) {
            System.out.println("Sql exception");
            System.out.println(ex);
        }
    }

    public void setBranchComboBoxItems(ComboBox comboBox) {
        String sql = "select * from Branch";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            branches.clear();
            comboBox.getItems().clear();
            while (rs.next()) {
                String branchName = rs.getString("branch_name");
                branches.put(branchName, rs.getInt("BranchID"));
                comboBox.getItems().add(branchName);
            }
        } catch (SQLException ex) {
            System.out.println("Sql exception");
            System.out.println(ex);
        }
    }

    public void setUserRoleComboBoxItems(ComboBox comboBox) {
        String sql = "select * from UserRole";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            comboBox.getItems().clear();
            userRoles.clear();
            while (rs.next()) {
                userRoles.put(rs.getString("user_role_title"), rs.getInt("UserRoleID"));
                comboBox.getItems().add(rs.getString("user_role_title"));
            }
        } catch (SQLException ex) {
            System.out.println("Can't get user roles");
        }
    }

    private void initUsersTab() {
        setBranchComboBoxItems(usersBranchComboBox);
        setUserRoleComboBoxItems(usersRoleComboBox);
    }

    private void initEmployeeTab() {
        setBranchComboBoxItems(emBranchComboBox);
        setPositionComboBoxItems(emPositionComboBox);

    }

    private void initializeUserTable() {
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
            initUsersTab();
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
            initEmployeeTab();
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
                            clearBranchForm();
                            branchTable.getSelectionModel().clearSelection();
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
                            usersTable.getItems().remove(selectedUser);
                            clearUsersForm();
                            usersTable.getSelectionModel().clearSelection();
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
            int id = selectedBranch.getId();
            String name = branchNameField.getText();
            String email = branchEmailField.getText();
            String contact = branchContactField.getText();
            String address = branchAddressField.getText();
            if (user.getUserRoleId() == 1) {
                updateBranch(selectedBranch, id, name, email, address, contact);
            } else if (user.getUserRoleId() == 2 && user.getBranchId() == selectedBranch.getId()) {
                updateBranch(selectedBranch, id, name, email, address, contact);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to update this branch.", "warning");
            }
        });
        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Employee selectedEmployee = newSelection;
                emNameField.setText(selectedEmployee.getName());
                emEmailField.setText(selectedEmployee.getEmail());
                emContactField.setText(selectedEmployee.getContact());
                emAddressField.setText(selectedEmployee.getAddress());
                emSalaryField.setText(Integer.toString(selectedEmployee.getSalary()));
                emPositionComboBox.getSelectionModel().select(selectedEmployee.getPositionTitle());
                emBranchComboBox.getSelectionModel().select(selectedEmployee.getBranchName());
            }
        });

        usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                User selectedUser = newSelection;
                usersNameField.setText(selectedUser.getName().get());
                usersEmailField.setText(selectedUser.getEmail().get());
                usersContactField.setText(selectedUser.getContact().get());
                usersAddressField.setText(selectedUser.getAddress().get());
                usersBranchComboBox.getSelectionModel().select(selectedUser.getBranchName());
                usersRoleComboBox.getSelectionModel().select(selectedUser.getUserRoleTitle());
            }
        });
        branchTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Branch selectedBranch = newSelection;
                branchNameField.setText(selectedBranch.getName());
                branchEmailField.setText(selectedBranch.getEmail());
                branchContactField.setText(selectedBranch.getContact());
                branchAddressField.setText(selectedBranch.getAddress());
            }
        });
        updateUserBtn.setOnMouseClicked(event -> {
            if (user.getUserRoleId() == 1) {
                User selectedUser = usersTable.getSelectionModel().getSelectedItem();
                if (selectedUser == null) {
                    FXMain.showNotification("No Selected Row", "Please select a row to update user.", "warning");
                } else {
                    try {
                        String selectedUserRole = usersRoleComboBox.getValue().toString();
                        String selectedBranch = usersBranchComboBox.getValue().toString();
                        String name = usersNameField.getText();
                        String email = usersEmailField.getText();
                        String contact = usersContactField.getText();
                        String address = usersAddressField.getText();

                        if (email.trim().isEmpty()) {
                            throw new IllegalArgumentException("Illegal Arguments");
                        } else {
                            updateUser(selectedUser, selectedUser.getId(), userRoles.get(selectedUserRole), branches.get(selectedBranch),
                                    name, email, contact, address, selectedBranch, selectedUserRole
                            );
                        }
                    } catch (NullPointerException np) {
                        FXMain.showNotification("Invalid Credentials", "Please fill up the form properly. Branch and User Role must be selected.", "warning");
                    } catch (IllegalArgumentException iae) {
                        FXMain.showNotification("Invalid Credentials", "Please fill up the form properly. Email and Password must be there.", "warning");
                    }
                }
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to update user.", "warning");
            }
        });

        addBranchBtn.setOnMouseClicked(event -> {
            if (user.getUserRoleId() == 1) {
                String name = branchNameField.getText();
                String contact = branchContactField.getText();
                String address = branchAddressField.getText();
                String email = branchEmailField.getText();
                addBranch(name, email, contact, address);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to delete user.", "warning");
            }
        });
        clearUserTableBtn.setOnMouseClicked(event -> {
            clearUsersForm();
            usersTable.getSelectionModel().clearSelection();
        });
        clearEmployeeTableBtn.setOnMouseClicked(event -> {
            clearEmployeeForm();
            employeeTable.getSelectionModel().clearSelection();
        });
        clearBranchTableBtn.setOnMouseClicked(event -> {
            clearBranchForm();
            branchTable.getSelectionModel().clearSelection();
        });
        addUserBtn.setOnMouseClicked(event -> {
            if (user.getUserRoleId() == 1) {
                try {
                    String selectedUserRole = usersRoleComboBox.getValue().toString();
                    String selectedBranch = usersBranchComboBox.getValue().toString();
                    String name = usersNameField.getText();
                    String email = usersEmailField.getText();
                    String pass = usersPassField.getText();
                    String contact = usersContactField.getText();
                    String address = usersAddressField.getText();
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

    private void updateBranch(Branch selectedBranch, int id, String name, String email, String address, String contact) {
        String sql = "update Branch set branch_name=?,branch_email=?,branch_address=?,branch_contact=? where BranchID=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, contact);
            ps.setInt(5, id);

            int rs = ps.executeUpdate();
            if (rs == 1) {
                branchTable.getItems().remove(selectedBranch);
                selectedBranch = new Branch(id, name, address, contact, email);
                branchTable.getItems().add(selectedBranch);
                branchTable.getSelectionModel().select(selectedBranch);
                FXMain.showNotification("Branch Info Updated", "Successfully updated branch information.", "success");
            } else {
                FXMain.showNotification("Failed to update the branch", "Something went worng.", "warning");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    private void clearEmployeeForm() {
        emNameField.setText("");
        emEmailField.setText("");
        emContactField.setText("");
        emAddressField.setText("");
        emSalaryField.setText("");
        emPositionComboBox.getSelectionModel().clearSelection();
        emBranchComboBox.getSelectionModel().clearSelection();
    }

    private void clearUsersForm() {
        usersNameField.setText("");
        usersEmailField.setText("");
        usersPassField.setText("");
        usersContactField.setText("");
        usersAddressField.setText("");
        usersBranchComboBox.getSelectionModel().clearSelection();
        usersRoleComboBox.getSelectionModel().clearSelection();
    }

    private void clearBranchForm() {
        branchNameField.setText("");
        branchEmailField.setText("");
        branchContactField.setText("");
        branchAddressField.setText("");
    }

    private void addBranch(String name, String email, String contact, String address) {
        String sql = "insert into Branch(branch_name,branch_address,branch_email,branch_contact)\n"
                + "values (?,?,?,?)";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, email);
            ps.setString(4, contact);
            int res = ps.executeUpdate();
            if (res == 1) {
                rs = connection.prepareStatement(String.format("select BranchID from Branch where branch_name='%s' and branch_email='%s'", name, email)).executeQuery();
                int bid = 0;
                while (rs.next()) {
                    bid = rs.getInt("BranchID");
                }
                FXMain.showNotification("Insert Successful", "New Branch inserted successfully", "successs");
                clearBranchForm();
                branchList.add(new Branch(bid, name, address, contact, email));

            } else {
                FXMain.showNotification("Insertion Failed", "Failed to insert new branch.", "warning");
            }
        } catch (SQLException ex) {
            System.out.println(ex);

        }
    }

    private void updateUser(User selectedUser, int userId, int userRoleId, int branchId, String name, String email, String contact, String address, String branchName, String userRoleTitle) {
        String sql = "update users set BranchID=?,UserRoleID=?,name=?, email=?,"
                + "contact=?,address=? where userid=? ";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, branchId);
            ps.setInt(2, userRoleId);
            ps.setString(3, name);
            ps.setString(4, email);
            ps.setString(5, contact);
            ps.setString(6, address);
            ps.setInt(7, userId);
            int res = ps.executeUpdate();
            if (res == 1) {
                usersTable.getItems().remove(selectedUser);
                selectedUser = new User(email, name, address, contact, branchName, userRoleTitle, userId, branchId, userRoleId);
                usersTable.getItems().add(selectedUser);
                usersTable.getSelectionModel().select(selectedUser);
                FXMain.showNotification("User Info Updated", "Successfully updated user information.", "success");
            } else {
                FXMain.showNotification("Failed to update the user", "Something went worng.", "warning");
            }
        } catch (SQLException ex) {
            FXMain.showNotification("SQL Error", "Can't create new user.", "error");
        }

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
                clearUsersForm();
                FXMain.showNotification("New User Created", "Successfully created new user", "success");
            } else {
                FXMain.showNotification("Failed to create new user", "Something went worng.", "warning");
            }
        } catch (SQLException ex) {
            FXMain.showNotification("SQL Error", "Can't create new user.", "error");
        }
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
