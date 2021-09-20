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
import AmariShop.Models.Product;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.ComboBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

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
    private TextField branchSearchByField, usersSearchByField, emSearchByTextField, emNameField, emContactField, emEmailField, emSalaryField, usersNameField, usersEmailField, usersContactField, branchNameField, branchEmailField, branchContactField, emailTextField, contactTextField, nameTextField;

    @FXML
    private TextField productSearchByField, productNameField, productModelField, productBrandField, productPurchaseRateField, productSalesRateField, productDiscountField, productQuantityField;

    @FXML
    private PasswordField oldPassField, newPassField, confirmPassField, usersPassField;

    @FXML
    private ComboBox productCompareComboBox, emCompareComboBox, productSearchByComboBox, productOrderByComboBox, productSubcategoryComboBox, productCategoryComboBox, emPositionComboBox, emBranchComboBox, usersBranchComboBox, usersRoleComboBox, usersSearchByComboBox, usersOrderByComboBox, branchSearchByComboBox, branchOrderByComboBox, employeeOrderByComboBox, employeeSearchByComboBox, employeeBranchComboBox, employeePositionComboBox;

    @FXML
    private TextArea productDescriptionField, emAddressField, addressTextField, usersAddressField, branchAddressField;

    @FXML
    private ImageView searchProductBtn, refreshProductTableBtn, clearProductTableBtn, addProductBtn, deleteProductBtn, updateProductBtn, searchBranchBtn, refreshBranchTableBtn, searchUsersBtn, refreshUsersTableBtn, searchEmployeeBtn, refreshEmTableBtn, clearEmployeeTableBtn, addEmployeeBtn, deleteEmployeeBtn, updateEmployeeBtn, clearBranchTableBtn, clearUserTableBtn, editBtn, updateUserBtn, updateBranchBtn, addBranchBtn, deleteBranchBtn, addUserBtn, deleteUserBtn;

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
    private TableColumn<User, Integer> usersIdCol;
    @FXML
    private TableColumn<User, String> usersNameCol, usersRoleCol, usersContactCol, usersEmailCol, usersBranchCol, usersAddressCol;

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
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol, productPurchaseRateCol, productSalesRateCol, productDiscountCol, productQuantityCol;
    @FXML
    private TableColumn<Product, String> productBranchCol, productNameCol, productModelCol, productBrandCol, productDescriptionCol, productCategoryCol, productSubcategoryCol;

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
        initializeProductTable();
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
        setUsersTableData();
        setBranchComboBoxItems(usersBranchComboBox);
        setUserRoleComboBoxItems(usersRoleComboBox);
        String searchBy[] = {"User ID", "Name", "Email", "Contact", "Address", "Branch", "User Role"};
        setSearchByComboBoxItems(usersSearchByComboBox, searchBy);
        String orderBy[] = {"User ID", "Name", "Email", "Contact", "Address", "Branch", "User Role"};
        setSearchByComboBoxItems(usersOrderByComboBox, orderBy);
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
        updateUserBtn.setOnMouseClicked(event -> {
            if (verifyShopAdmin()) {
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
        refreshUsersTableBtn.setOnMouseClicked(e -> {
            usersSearchByField.setText("");
            setUsersTableData();
        });
        clearUserTableBtn.setOnMouseClicked(event -> {
            clearUsersForm();
            usersTable.getSelectionModel().clearSelection();
            usersTable.getSortOrder().clear();
        });
        usersSearchByField.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case ENTER:
                    setUsersTableSearchBy();
            }
        });
        searchUsersBtn.setOnMouseClicked(e -> {
            setUsersTableSearchBy();
        });
        usersOrderByComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                String ob = newValue.toString().toLowerCase();
                if (ob.equals("name")) {
                    usersTable.getSortOrder().add(usersNameCol);
                } else if (ob.equals("email")) {
                    usersTable.getSortOrder().add(usersEmailCol);
                } else if (ob.equals("contact")) {
                    usersTable.getSortOrder().add(usersContactCol);
                } else if (ob.equals("user id")) {
                    usersTable.getSortOrder().add(usersIdCol);
                } else if (ob.equals("branch")) {
                    usersTable.getSortOrder().add(usersBranchCol);
                } else if (ob.equals("user role")) {
                    usersTable.getSortOrder().add(usersRoleCol);
                } else if (ob.equals("address")) {
                    usersTable.getSortOrder().add(usersAddressCol);
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
                    FXMain.showNotification("Permission Denyed", "You are not allowed to delete user.", "warning");
                }
            }
        });
        clearUserTableBtn.setOnMouseClicked(event -> {
            clearUsersForm();
            usersTable.getSelectionModel().clearSelection();
        });
        addUserBtn.setOnMouseClicked(event -> {
            if (verifyShopAdmin()) {
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
        usersSearchByComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            setUsersSearchByField((String) newValue);
        });

    }

    public String getSqlQueryForUsersSearch(String col, String search) {
        String sql = "select u.userid,u.name,u.email,u.address,u.contact,\n"
                + "u.created_at,b.branchid,b.branch_name,r.UserRoleID,r.user_role_title from \n"
                + "Users u \n"
                + "inner join Branch b on u.BranchID= b.BranchID \n"
                + "inner join UserRole r on u.UserRoleID=r.UserRoleID ";
        if (col.equals("name") || col.equals("email") || col.equals("contact") || col.equals("address")) {
            sql += String.format("where u.%s like '%s'", col, search);
        } else if (col.equals("user id")) {
            sql += String.format("where u.userid=%d", Integer.parseInt(search));
        } else if (col.equals("branch")) {
            sql += String.format("where b.branch_name like '%s'", search);
        } else if (col.equals("user role")) {
            sql += String.format("where r.user_role_title like '%s'", search);
        }

        return sql;
    }

    private void setUsersTableSearchBy() {
        String col = usersSearchByComboBox.getValue().toString().toLowerCase();
        String search = usersSearchByField.getText();
        if (col.trim().isEmpty() || search.trim().isEmpty()) {
            FXMain.showNotification("Invalid Input", "Search Field can not be emply and you must select Search By", "warning");
            return;
        }
        userList.clear();
        String sql = getSqlQueryForUsersSearch(col, search);

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
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
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public String getSqlQueryForBranchSearch(String col, String search) {
        String sql = "select * from Branch ";
        if (col.equals("name") || col.equals("email") || col.equals("contact") || col.equals("address")) {
            sql += String.format("where branch_%s like '%s'", col, search);
        } else if (col.equals("branch id")) {
            sql += String.format("where BranchID=%d", Integer.parseInt(search));
        }

        return sql;
    }

    private void setBranchTableSearchBy() {
        String col = branchSearchByComboBox.getValue().toString().toLowerCase();
        String search = branchSearchByField.getText();
        if (col.trim().isEmpty() || search.trim().isEmpty()) {
            FXMain.showNotification("Invalid Input", "Search Field can not be emply and you must select Search By", "warning");
            return;
        }
        branchList.clear();
        String sql = getSqlQueryForBranchSearch(col, search);

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

    }

    private void initBranchTab() {
        String searchBy[] = {"Branch ID", "Name", "Contact", "Email", "Address"};
        setSearchByComboBoxItems(branchSearchByComboBox, searchBy);
        String orderBy[] = {"Branch ID", "Name", "Contact", "Email", "Address"};
        setSearchByComboBoxItems(branchOrderByComboBox, orderBy);
        setBranchTableData();
        branchTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Branch selectedBranch = newSelection;
                branchNameField.setText(selectedBranch.getName());
                branchEmailField.setText(selectedBranch.getEmail());
                branchContactField.setText(selectedBranch.getContact());
                branchAddressField.setText(selectedBranch.getAddress());
            }
        });
        refreshBranchTableBtn.setOnMouseClicked(e -> {
            branchSearchByField.setText("");
            setBranchTableData();
        });
        branchSearchByField.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case ENTER:
                    setBranchTableSearchBy();
            }
        });
        searchBranchBtn.setOnMouseClicked(event -> {
            setBranchTableSearchBy();
        });
        branchOrderByComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                String ob = newValue.toString().toLowerCase();
                if (ob.equals("name")) {
                    branchTable.getSortOrder().add(branchNameCol);
                } else if (ob.equals("email")) {
                    branchTable.getSortOrder().add(branchEmailCol);
                } else if (ob.equals("contact")) {
                    branchTable.getSortOrder().add(branchContactCol);
                } else if (ob.equals("address")) {
                    branchTable.getSortOrder().add(branchAddressCol);
                } else if (ob.equals("branch id")) {
                    branchTable.getSortOrder().add(branchIdCol);
                }
            }
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
            if (verifyShopAdmin()) {
                updateBranch(selectedBranch, id, name, email, address, contact);
            } else if (verifyBranchAdminPermission(selectedBranch.getId())) {
                updateBranch(selectedBranch, id, name, email, address, contact);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to update this branch.", "warning");
            }
        });
        addBranchBtn.setOnMouseClicked(event -> {
            if (verifyShopAdmin()) {
                String name = branchNameField.getText();
                String contact = branchContactField.getText();
                String address = branchAddressField.getText();
                String email = branchEmailField.getText();
                addBranch(name, email, contact, address);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to add branch.", "warning");
            }
        });
        clearBranchTableBtn.setOnMouseClicked(event -> {
            clearBranchForm();
            branchTable.getSelectionModel().clearSelection();
            branchTable.getSortOrder().clear();
        });
        branchSearchByComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            setBranchSearchByField((String) newValue);
        });
    }

    HashMap<String, Integer> categories = new HashMap<String, Integer>();
    HashMap<String, Integer> subcategories = new HashMap<String, Integer>();
    Map<String, List<String>> subcategoryMap = new HashMap<>();

    private void setProductCategoryComboBoxItems() {
        String sql = "select * from Category c inner join Subcategory sc on c.CategoryID=sc.CategoryID";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String categoryTitle = rs.getString("category_title");
                String subcategoryTitle = rs.getString("subcategory_title");
                int categoryId = rs.getInt("CategoryID");
                int subcategoryId = rs.getInt("SubcategoryID");
                categories.putIfAbsent(categoryTitle, categoryId);
                subcategories.putIfAbsent(subcategoryTitle, subcategoryId);
                subcategoryMap.computeIfAbsent(categoryTitle, k -> new ArrayList<>()).add(subcategoryTitle);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        productCategoryComboBox.getItems().addAll(categories.keySet());

    }

    private void setSubcategoryComboBoxItems() {
        productCategoryComboBox
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {
                    if (newValue != null) {
                        productSubcategoryComboBox.getItems().clear();
                        productSubcategoryComboBox.getItems().addAll(subcategoryMap.get(newValue));
                    }

                });
    }

    ObservableList<Product> productList = FXCollections.observableArrayList();

    private void setProductTableData() {
        productList.clear();
        String sql = "select p.productid, p.product_name,p.product_model,p.product_brand,\n"
                + "p.product_description,p.product_purchase_rate,p.product_sales_rate,\n"
                + "p.product_discount, p.total_quantity, c.category_title,"
                + "b.branch_name,s.subcategory_title from Product p \n"
                + "inner join Subcategory s on s.SubcategoryID=p.SubcategoryID\n"
                + "inner join Category c on s.CategoryID=c.CategoryID "
                + "inner join Branch b on b.BranchID=p.BranchID";

        if (!verifyShopAdmin()) {
            sql += " where b.BranchID=" + Integer.toString(user.getBranchId());
        }
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("productid");
                String name = rs.getString("product_name");
                String model = rs.getString("product_model");
                String brand = rs.getString("product_brand");
                String categoryTitle = rs.getString("category_title");
                String subcategoryTitle = rs.getString("subcategory_title");
                String description = rs.getString("product_description");
                int purchaseRate = rs.getInt("product_purchase_rate");
                int salesRate = rs.getInt("product_sales_rate");
                int discount = rs.getInt("product_discount");
                int quantity = rs.getInt("total_quantity");
                String branchName = rs.getString("branch_name");
                productList.add(new Product(id, name, model, brand, description, categoryTitle, subcategoryTitle, purchaseRate, salesRate, discount, quantity, branchName));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        productTable.setItems(productList);
    }

    private void initializeProductTable() {
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        productBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        productCategoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryTitle"));
        productSubcategoryCol.setCellValueFactory(new PropertyValueFactory<>("subcategoryTitle"));
        productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        productPurchaseRateCol.setCellValueFactory(new PropertyValueFactory<>("purchaseRate"));
        productSalesRateCol.setCellValueFactory(new PropertyValueFactory<>("salesRate"));
        productDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productBranchCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));
    }

    private void clearProductForm() {
        productNameField.setText("");
        productModelField.setText("");
        productBrandField.setText("");
        productDescriptionField.setText("");
        productCategoryComboBox.getSelectionModel().clearSelection();
        productSubcategoryComboBox.getSelectionModel().clearSelection();
        productPurchaseRateField.setText("");
        productSalesRateField.setText("");
        productQuantityField.setText("");
        productDiscountField.setText("");
    }

    private void initProductsTab() {
        setProductTableData();
        String searchBy[] = {"ID", "Name", "Model", "Brand", "Purchase Rate", "Sales Rate", "Discount", "Category", "Subcategory", "Branch"};
        setSearchByComboBoxItems(productSearchByComboBox, searchBy);
        String orderBy[] = {"ID", "Name", "Model", "Brand", "Quantity", "Purchase Rate", "Sales Rate", "Discount", "Category", "Subcategory", "Branch"};
        setSearchByComboBoxItems(productOrderByComboBox, orderBy);
        String compare[] = {"=", ">", "<", ">=", "<=", "LIKE"};
        setSearchByComboBoxItems(productCompareComboBox, compare);
        productCompareComboBox.setValue("=");

        setProductCategoryComboBoxItems();
        setSubcategoryComboBoxItems();
        productOrderByComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                String ob = newValue.toString().toLowerCase();
                if (ob.equals("id")) {
                    productTable.getSortOrder().add(productIdCol);
                } else if (ob.equals("name")) {
                    productTable.getSortOrder().add(productNameCol);
                } else if (ob.equals("model")) {
                    productTable.getSortOrder().add(productModelCol);
                } else if (ob.equals("brand")) {
                    productTable.getSortOrder().add(productBrandCol);
                } else if (ob.equals("quantity")) {
                    productTable.getSortOrder().add(productQuantityCol);
                } else if (ob.equals("purchase rate")) {
                    productTable.getSortOrder().add(productPurchaseRateCol);
                } else if (ob.equals("sales rate")) {
                    productTable.getSortOrder().add(productSalesRateCol);
                } else if (ob.equals("discount")) {
                    productTable.getSortOrder().add(productDiscountCol);
                } else if (ob.equals("category")) {
                    productTable.getSortOrder().add(productCategoryCol);
                } else if (ob.equals("subcategory")) {
                    productTable.getSortOrder().add(productSubcategoryCol);
                } else if (ob.equals("branch")) {
                    productTable.getSortOrder().add(productBranchCol);
                }
            }
        });
        refreshProductTableBtn.setOnMouseClicked(event -> {
            productSearchByField.setText("");
            setProductTableData();
        });
        deleteProductBtn.setOnMouseClicked(event -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                FXMain.showNotification("No Selected Row", "Please select a row to delete.", "warning");
            } else {
                String sql = "delete from Product where ProductID=?";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, selectedProduct.getId());
                    int res = ps.executeUpdate();
                    if (res == 1) {
                        productTable.getItems().remove(selectedProduct);
                        clearProductForm();
                        productTable.getSelectionModel().clearSelection();
                        FXMain.showNotification("Product Deleted Successfully", "Product is deleted successfully.", "success");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        });
        addProductBtn.setOnMouseClicked(event -> {
            try {
                String name = productNameField.getText();
                String model = productModelField.getText();
                String brand = productBrandField.getText();
                String categoryTitle = productCategoryComboBox.getSelectionModel().getSelectedItem().toString();
                String subcategoryTitle = productSubcategoryComboBox.getSelectionModel().getSelectedItem().toString();
                int subcategoryId = subcategories.get(subcategoryTitle);
                String description = productDescriptionField.getText();
                int purchaseRate = Integer.parseInt(productPurchaseRateField.getText());
                int salesRate = Integer.parseInt(productSalesRateField.getText());
                int discount = Integer.parseInt(productDiscountField.getText());
                int quantity = Integer.parseInt(productQuantityField.getText());
                addProduct(name, model, brand, description, purchaseRate, salesRate, discount, quantity, categoryTitle, subcategoryTitle, subcategoryId);
            } catch (Exception ex) {
                FXMain.showNotification("Invalid Input", "Please fill up the form properly. Make sure you have choosen category and subcategory", "warning");
            }
        });
        updateProductBtn.setOnMouseClicked(event -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                FXMain.showNotification("No Selected Row", "Please select a row to update.", "warning");
                return;
            }
            try {
                String name = productNameField.getText();
                String model = productModelField.getText();
                String brand = productBrandField.getText();
                String categoryTitle = productCategoryComboBox.getSelectionModel().getSelectedItem().toString();
                String subcategoryTitle = productSubcategoryComboBox.getSelectionModel().getSelectedItem().toString();
                int subcategoryId = subcategories.get(subcategoryTitle);
                String description = productDescriptionField.getText();
                int purchaseRate = Integer.parseInt(productPurchaseRateField.getText());
                int salesRate = Integer.parseInt(productSalesRateField.getText());
                int discount = Integer.parseInt(productDiscountField.getText());
                int quantity = Integer.parseInt(productQuantityField.getText());
                updateProduct(selectedProduct, name, model, brand, description, purchaseRate, salesRate, discount, quantity, categoryTitle, subcategoryTitle, subcategoryId);
            } catch (Exception ex) {
                FXMain.showNotification("Invalid Input", "Please fill up the form properly. Make sure you have choosen category and subcategory", "warning");
            }

        });
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Product selectedProduct = newSelection;
                productNameField.setText(selectedProduct.getName());
                productModelField.setText(selectedProduct.getModel());
                productBrandField.setText(selectedProduct.getBrand());
                productDescriptionField.setText(selectedProduct.getDescription());
                productCategoryComboBox.getSelectionModel().select(selectedProduct.getCategoryTitle());
                productSubcategoryComboBox.getSelectionModel().select(selectedProduct.getSubcategoryTitle());
                productPurchaseRateField.setText(Integer.toString(selectedProduct.getPurchaseRate()));
                productSalesRateField.setText(Integer.toString(selectedProduct.getSalesRate()));
                productQuantityField.setText(Integer.toString(selectedProduct.getQuantity()));
                productDiscountField.setText(Integer.toString(selectedProduct.getDiscount()));
            }
        });
        clearProductTableBtn.setOnMouseClicked(event -> {
            clearProductForm();
            productTable.getSelectionModel().clearSelection();
            productTable.getSortOrder().clear();
        });
        productSearchByComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            setProductSearchByField((String) newValue);
        });
        productSearchByField.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case ENTER:
                    setProductTableSearchBy();
            }
        });
        searchProductBtn.setOnMouseClicked(event -> {
            setProductTableSearchBy();
        });
    }

    public String getSqlQueryForProductSearch(String col, String search, String compare) {
        String sql = "select p.productid, p.product_name,p.product_model,p.product_brand,p.product_discount,\n"
                + "p.product_description,p.product_purchase_rate,p.product_sales_rate,c.category_title,\n"
                + "s.subcategory_title, p.total_quantity,b.branch_name from Product p \n"
                + "inner join Subcategory s on s.SubcategoryID=p.SubcategoryID\n"
                + "inner join Category c on s.CategoryID=c.CategoryID\n"
                + "inner join Branch b on b.BranchID=p.BranchID ";

        if (col.equals("name") || col.equals("model") || col.equals("brand")) {
            sql += String.format("where p.product_%s %s '%s'", col, compare, search);
        } else if (col.equals("id")) {
            sql += String.format("where p.ProductID %s %d", compare, Integer.parseInt(search));
        } else if (col.equals("discount")) {
            sql += String.format("where p.discount %s %d", compare, Integer.parseInt(search));
        } else if (col.equals("purchase rate")) {
            sql += String.format("where p.product_purchase_rate %s '%s'", compare, search);
        } else if (col.equals("sales rate")) {
            sql += String.format("where p.product_sales_rate %s '%s'", compare, search);
        } else if (col.equals("category")) {
            sql += String.format("where c.category_title %s '%s'", compare, search);
        } else if (col.equals("subcategory")) {
            sql += String.format("where s.subcategory_title %s '%s'", compare, search);
        } else if (col.equals("branch")) {
            sql += String.format("where b.branch_name %s '%s'", compare, search);
        }

        return sql;
    }

    private void setProductTableSearchBy() {
        String col = "";
        String compare = "";
        String search = productSearchByField.getText();
        try {
            col = productSearchByComboBox.getValue().toString().toLowerCase();
            compare = productCompareComboBox.getValue().toString();
            if (col.trim().isEmpty() || search.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid Input");
            }
        } catch (Exception e) {
            FXMain.showNotification("Invalid Input", "Search Field can not be emply and you must select Search By", "warning");
            return;
        }

        productList.clear();
        String sql = getSqlQueryForProductSearch(col, search, compare);

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("productid");
                String name = rs.getString("product_name");
                String model = rs.getString("product_model");
                String brand = rs.getString("product_brand");
                String categoryTitle = rs.getString("category_title");
                String subcategoryTitle = rs.getString("subcategory_title");
                String description = rs.getString("product_description");
                int purchaseRate = rs.getInt("product_purchase_rate");
                int salesRate = rs.getInt("product_sales_rate");
                int discount = rs.getInt("product_discount");
                int quantity = rs.getInt("total_quantity");
                String branchName = rs.getString("branch_name");
                productList.add(new Product(id, name, model, brand, description, categoryTitle, subcategoryTitle, purchaseRate, salesRate, discount, quantity, branchName));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void setProductSearchByField(String searchBy) {
        int n = productList.size();
        Set<String> possibleSet = new HashSet<>();
        Set<Integer> possibleSet2 = new HashSet<>();
        if (autoCompletionBindingString != null) {
            autoCompletionBindingString.dispose();
        }
        if (autoCompletionBindingInt != null) {
            autoCompletionBindingInt.dispose();
        }
        if (searchBy == "ID") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(productList.get(i).getId());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(productSearchByField, possibleSet2);
        } else if (searchBy == "Name") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(productList.get(i).getName());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(productSearchByField, possibleSet);
        } else if (searchBy == "Model") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(productList.get(i).getModel());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(productSearchByField, possibleSet);
        } else if (searchBy == "Brand") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(productList.get(i).getBrand());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(productSearchByField, possibleSet);
        } else if (searchBy == "Quantity") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(productList.get(i).getQuantity());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(productSearchByField, possibleSet2);
        } else if (searchBy == "Purchase Rate") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(productList.get(i).getPurchaseRate());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(productSearchByField, possibleSet2);
        } else if (searchBy == "Sales Rate") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(productList.get(i).getSalesRate());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(productSearchByField, possibleSet2);
        } else if (searchBy == "Discount") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(productList.get(i).getDiscount());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(productSearchByField, possibleSet2);
        } else if (searchBy == "Category") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(productList.get(i).getCategoryTitle());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(productSearchByField, possibleSet);
        } else if (searchBy == "Subcategory") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(productList.get(i).getSubcategoryTitle());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(productSearchByField, possibleSet);
        } else if (searchBy == "Branch") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(productList.get(i).getBranchName());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(productSearchByField, possibleSet);
        }

    }

    private void updateProduct(Product selectedProduct, String name, String model, String brand, String description, int purchaseRate, int salesRate, int discount, int quantity, String categoryTitle, String subcategoryTitle, int subcategoryId) {
        String sql = "update Product set product_name=?,product_model=?,product_brand=?,product_description=?,\n"
                + "product_purchase_rate=?,product_sales_rate=?,product_discount=?, total_quantity=?,SubcategoryID=?\n"
                + "where ProductID=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, model);
            ps.setString(3, brand);
            ps.setString(4, description);
            ps.setInt(5, purchaseRate);
            ps.setInt(6, salesRate);
            ps.setInt(7, discount);
            ps.setInt(8, quantity);
            ps.setInt(9, subcategoryId);
            ps.setInt(10, selectedProduct.getId());
            int res = ps.executeUpdate();
            if (res == 1) {
                productTable.getItems().remove(selectedProduct);
                String branchName = selectedProduct.getBranchName();
                selectedProduct = new Product(selectedProduct.getId(), name, model, brand, description, categoryTitle, subcategoryTitle, purchaseRate, salesRate, discount, quantity, branchName);
                productTable.getItems().add(selectedProduct);
                productTable.getSelectionModel().select(selectedProduct);
                FXMain.showNotification("Product Info Updated", "Successfully updated product information.", "success");
            } else {
                FXMain.showNotification("Failed to update the user", "Something went worng.", "warning");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    private void addProduct(String name, String model, String brand, String description, int purchaseRate, int salesRate, int discount, int quantity, String categoryTitle, String subcategoryTitle, int subcategoryId) {
        String sql = "SET ANSI_WARNINGS OFF;insert into Product(product_name,product_model,product_brand,product_description,\n"
                + "product_purchase_rate,product_sales_rate,product_discount,total_quantity,\n"
                + "SubcategoryID,BranchID) output Inserted.ProductID\n"
                + "values(?,?,?,?,?,?,?,?,?,?);SET ANSI_WARNINGS ON;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, model);
            ps.setString(3, brand);
            ps.setString(4, description);
            ps.setInt(5, purchaseRate);
            ps.setInt(6, salesRate);
            ps.setInt(7, discount);
            ps.setInt(8, quantity);
            ps.setInt(9, subcategoryId);
            ps.setInt(10, user.getBranchId());
            rs = ps.executeQuery();
            while (rs.next()) {
                String branchName = user.getBranchName();
                clearProductForm();
                productList.add(new Product(rs.getInt("ProductID"), name, model, brand, description, categoryTitle, subcategoryTitle, purchaseRate, salesRate, discount, quantity, branchName));
                FXMain.showNotification("Insertion Successful", "Product inserted successfully", "success");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    private void initEmployeeTab() {
        setEmployeeTableData();
        setBranchComboBoxItems(emBranchComboBox);
        setPositionComboBoxItems(emPositionComboBox);
        String searchBy[] = {"ID", "Name", "Contact", "Email", "Address", "Salary", "Branch", "Position"};
        setSearchByComboBoxItems(employeeSearchByComboBox, searchBy);
        String orderBy[] = {"ID", "Name", "Contact", "Email", "Salary", "Branch", "Position"};
        setSearchByComboBoxItems(employeeOrderByComboBox, orderBy);
        String compare[] = {"=", ">", "<", ">=", "<=", "LIKE"};
        setSearchByComboBoxItems(emCompareComboBox, compare);
        emCompareComboBox.setValue("=");

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
        refreshEmTableBtn.setOnMouseClicked(e -> {
            emSearchByTextField.setText("");
            setEmployeeTableData();
        });
        emSearchByTextField.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case ENTER:
                    setEmTableSearchBy();
            }
        });
        searchEmployeeBtn.setOnMouseClicked(event -> {
            setEmTableSearchBy();
        });
        employeeOrderByComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                String ob = newValue.toString().toLowerCase();
                if (ob.equals("name")) {
                    employeeTable.getSortOrder().add(emNameCol);
                } else if (ob.equals("email")) {
                    employeeTable.getSortOrder().add(emEmailCol);
                } else if (ob.equals("contact")) {
                    employeeTable.getSortOrder().add(emContactCol);
                } else if (ob.equals("id")) {
                    employeeTable.getSortOrder().add(emIdCol);
                } else if (ob.equals("branch")) {
                    employeeTable.getSortOrder().add(emBranchCol);
                } else if (ob.equals("position")) {
                    employeeTable.getSortOrder().add(emPositionCol);
                } else if (ob.equals("salary")) {
                    employeeTable.getSortOrder().add(emSalaryCol);
                }
            }
        });
        deleteEmployeeBtn.setOnMouseClicked(event -> {
            Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                FXMain.showNotification("No Selected Row", "Please select a row to delete.", "warning");
            } else {
                if (verifyShopAdmin() || verifyBranchAdminPermission(selectedEmployee.getBranchId())) {
                    String sql = "delete from Employee where EmployeeID=?";
                    try {
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, selectedEmployee.getId());
                        int res = ps.executeUpdate();
                        if (res == 1) {
                            employeeTable.getItems().removeAll(selectedEmployee);
                            clearEmployeeForm();
                            employeeTable.getSelectionModel().clearSelection();
                            FXMain.showNotification("Deleted Successfully", "Employee is deleted successfully.", "success");
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }

                } else {
                    FXMain.showNotification("Permission Denyed", "You are not allowed to delete employee.", "warning");
                }
            }

        });
        updateEmployeeBtn.setOnMouseClicked(event -> {
            int salary = 0, branchId = 0, positionId = 0;
            String positionTitle = "", branchName = "";
            String name = emNameField.getText();
            String contact = emContactField.getText();
            String address = emAddressField.getText();
            String email = emEmailField.getText();
            try {
                salary = Integer.parseInt(emSalaryField.getText());
                branchId = branches.get(emBranchComboBox.getValue().toString());
                positionId = positions.get(emPositionComboBox.getValue().toString());
                positionTitle = emPositionComboBox.getValue().toString();
                branchName = emBranchComboBox.getValue().toString();
            } catch (Exception ex) {
                FXMain.showNotification("Input Error", "Please fill up the form properly", "warning");
                System.out.println(ex);
            }
            Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                FXMain.showNotification("No Selected Row", "Please select a row to update employee.", "warning");
                return;
            }
            if (verifyBranchAdminPermission(selectedEmployee.getBranchId()) || verifyShopAdmin()) {
                updateEmployee(selectedEmployee, selectedEmployee.getId(), name, email, contact, address, salary, branchId, branchName, positionId, positionTitle);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to update this employee.", "warning");
            }
        });

        addEmployeeBtn.setOnMouseClicked(event -> {
            int salary = 0, branchId = 0, positionId = 0;
            String positionTitle = "", branchName = "";
            String name = emNameField.getText();
            String contact = emContactField.getText();
            String address = emAddressField.getText();
            String email = emEmailField.getText();
            try {
                salary = Integer.parseInt(emSalaryField.getText());
                branchId = branches.get(emBranchComboBox.getValue().toString());
                positionId = positions.get(emPositionComboBox.getValue().toString());
                positionTitle = emPositionComboBox.getValue().toString();
                branchName = emBranchComboBox.getValue().toString();
            } catch (Exception ex) {
                FXMain.showNotification("Input Error", "Please fill up the form properly", "warning");
                System.out.println(ex);
            }
            if (verifyBranchAdminPermission(branchId) || verifyShopAdmin()) {
                addEmployee(name, email, contact, address, salary, branchId, branchName, positionId, positionTitle);
            } else {
                FXMain.showNotification("Permission Denyed", "You are not allowed to add this employee.", "warning");
            }

        });
        employeeSearchByComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            setEmSearchByField((String) newValue);
        });
        clearEmployeeTableBtn.setOnMouseClicked(event -> {
            clearEmployeeForm();
            employeeTable.getSelectionModel().clearSelection();
            employeeTable.getSortOrder().clear();
        });
    }

    public String getSqlQueryForEmSearch(String col, String search, String compare) {
        String sql = "select em.EmployeeID,em.branchid,em.employee_name,em.employee_email,em.employee_address,em.employee_contact,\n"
                + "em.employee_salary,b.branchid,b.branch_name,p.position_title,p.EmployeePositionID from Employee em \n"
                + "inner join Branch b on em.BranchID= b.BranchID \n"
                + "inner join EmployeePosition p on em.EmployeePositionID=p.EmployeePositionID ";

        if (col.equals("name") || col.equals("email") || col.equals("contact") || col.equals("address")) {
            sql += String.format("where em.employee_%s %s '%s'", col, compare, search);
        } else if (col.equals("id")) {
            sql += String.format("where em.EmployeeID %s %d", compare, Integer.parseInt(search));
        } else if (col.equals("salary")) {
            sql += String.format("where em.employee_salary %s %d", compare, Integer.parseInt(search));
        } else if (col.equals("branch")) {
            sql += String.format("where b.branch_name %s '%s'", compare, search);
        } else if (col.equals("position")) {
            sql += String.format("where p.position_title %s '%s'", compare, search);
        }

        return sql;
    }

    public void setEmTableSearchBy() {
        String col = "";
        String compare = "";
        String search = emSearchByTextField.getText();
        try {
            col = employeeSearchByComboBox.getValue().toString().toLowerCase();
            compare = emCompareComboBox.getValue().toString();
            if (col.trim().isEmpty() || search.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid Input");
            }
        } catch (Exception e) {
            FXMain.showNotification("Invalid Input", "Search Field can not be emply and you must select Search By", "warning");
            return;
        }

        employeeList.clear();
        String sql = getSqlQueryForEmSearch(col, search, compare);

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
    }

    private void setSearchByComboBoxItems(ComboBox comboBox, String searchBy[]) {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(searchBy);
    }

    private void initializeUserTable() {
        usersIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usersNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        usersBranchCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        usersEmailCol.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        usersContactCol.setCellValueFactory(cellData -> cellData.getValue().getContact());
        usersAddressCol.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        usersRoleCol.setCellValueFactory(new PropertyValueFactory<>("userRoleTitle"));
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

    private AutoCompletionBinding<String> autoCompletionBindingString;
    private AutoCompletionBinding<Integer> autoCompletionBindingInt;

    private void setBranchSearchByField(String searchBy) {
        int n = branchList.size();
        Set<String> possibleSet = new HashSet<>();
        Set<Integer> possibleSet2 = new HashSet<>();
        if (autoCompletionBindingString != null) {
            autoCompletionBindingString.dispose();
        }
        if (autoCompletionBindingInt != null) {
            autoCompletionBindingInt.dispose();
        }
        if (searchBy == "Name") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(branchList.get(i).getName());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(branchSearchByField, possibleSet);
        } else if (searchBy == "Email") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(branchList.get(i).getEmail());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(branchSearchByField, possibleSet);
        } else if (searchBy == "Contact") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(branchList.get(i).getContact());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(branchSearchByField, possibleSet);
        } else if (searchBy == "Address") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(branchList.get(i).getAddress());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(branchSearchByField, possibleSet);
        } else if (searchBy == "Branch ID") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(branchList.get(i).getId());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(branchSearchByField, possibleSet2);
        }
    }

    private void setUsersSearchByField(String searchBy) {
        int n = userList.size();
        Set<String> possibleSet = new HashSet<>();
        Set<Integer> possibleSet2 = new HashSet<>();
        if (autoCompletionBindingString != null) {
            autoCompletionBindingString.dispose();
        }
        if (autoCompletionBindingInt != null) {
            autoCompletionBindingInt.dispose();
        }
        if (searchBy == "Name") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(userList.get(i).getName().get());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(usersSearchByField, possibleSet);
        } else if (searchBy == "Email") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(userList.get(i).getEmail().get());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(usersSearchByField, possibleSet);
        } else if (searchBy == "Contact") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(userList.get(i).getContact().get());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(usersSearchByField, possibleSet);
        } else if (searchBy == "Address") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(userList.get(i).getAddress().get());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(usersSearchByField, possibleSet);
        } else if (searchBy == "User Role") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(userList.get(i).getUserRoleTitle());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(usersSearchByField, possibleSet);
        } else if (searchBy == "Branch") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(userList.get(i).getBranchName());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(usersSearchByField, possibleSet);
        } else if (searchBy == "User ID") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(userList.get(i).getId());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(usersSearchByField, possibleSet2);
        }
    }

    private void setEmSearchByField(String searchBy) {
        int n = employeeList.size();
        Set<String> possibleSet = new HashSet<>();
        Set<Integer> possibleSet2 = new HashSet<>();
        if (autoCompletionBindingString != null) {
            autoCompletionBindingString.dispose();
        }
        if (autoCompletionBindingInt != null) {
            autoCompletionBindingInt.dispose();
        }
        if (searchBy == "Name") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(employeeList.get(i).getName());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(emSearchByTextField, possibleSet);
        } else if (searchBy == "Email") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(employeeList.get(i).getEmail());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(emSearchByTextField, possibleSet);
        } else if (searchBy == "Contact") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(employeeList.get(i).getContact());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(emSearchByTextField, possibleSet);
        } else if (searchBy == "Address") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(employeeList.get(i).getAddress());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(emSearchByTextField, possibleSet);
        } else if (searchBy == "Position") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(employeeList.get(i).getPositionTitle());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(emSearchByTextField, possibleSet);
        } else if (searchBy == "Branch") {
            for (int i = 0; i < n; i++) {
                possibleSet.add(employeeList.get(i).getBranchName());
            }
            autoCompletionBindingString = TextFields.bindAutoCompletion(emSearchByTextField, possibleSet);
        } else if (searchBy == "ID") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(employeeList.get(i).getId());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(emSearchByTextField, possibleSet2);
        } else if (searchBy == "Salary") {
            for (int i = 0; i < n; i++) {
                possibleSet2.add(employeeList.get(i).getSalary());
            }
            autoCompletionBindingInt = TextFields.bindAutoCompletion(emSearchByTextField, possibleSet2);
        }
    }

    private boolean verifyBranchAdminPermission(int branchId) {
        if (user.getBranchId() == branchId) {
            return true;
        } else {
            return false;
        }
    }

    private void updateEmployee(Employee selectedEmployee, int emId, String name, String email, String contact, String address, int salary, int branchId, String branchName, int positionId, String positionTitle) {
        String sql = "update Employee set employee_name=?,employee_email=?,\n"
                + "employee_contact=?,employee_address=?,employee_salary=?\n"
                + ",BranchId=?,EmployeePositionID=? where EmployeeID=?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, contact);
            ps.setString(4, address);
            ps.setInt(5, salary);
            ps.setInt(6, branchId);
            ps.setInt(7, positionId);
            ps.setInt(8, emId);

            int rs = ps.executeUpdate();
            if (rs == 1) {
                employeeTable.getItems().remove(selectedEmployee);
                selectedEmployee = new Employee(emId, branchId, branchName, positionId, positionTitle, salary, name, email, contact, address);
                employeeTable.getItems().add(selectedEmployee);
                employeeTable.getSelectionModel().select(selectedEmployee);
                FXMain.showNotification("Employee Info Updated", "Successfully updated employee information.", "success");
            } else {
                FXMain.showNotification("Failed to update the branch", "Something went worng.", "warning");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    private boolean verifyShopAdmin() {
        if (user.getUserRoleId() == 1) {
            return true;
        } else {
            return false;
        }
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

    private void addEmployee(String name, String email, String contact, String address, int salary, int branchId, String branchName, int positionId, String positionTitle) {
        String sql = "insert into Employee(employee_name,employee_email,employee_contact,employee_address,employee_salary,BranchId,EmployeePositionID) "
                + "OUTPUT Inserted.EmployeeID values (?,?,?,?,?,?,?)";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, contact);
            ps.setString(4, address);
            ps.setInt(5, salary);
            ps.setInt(6, branchId);
            ps.setInt(7, positionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int emId = rs.getInt("EmployeeID");
                clearEmployeeForm();
                employeeList.add(new Employee(emId, branchId, branchName, positionId, positionTitle, salary, name, email, contact, address));
                FXMain.showNotification("Insert Successful", "New Branch inserted successfully", "successs");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            FXMain.showNotification("Insertion Failed", "Failed to insert new branch.", "warning");
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
                + "output Inserted.BranchID values (?,?,?,?)";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, email);
            ps.setString(4, contact);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bid = rs.getInt("BranchID");
                FXMain.showNotification("Insert Successful", "New Branch inserted successfully", "successs");
                clearBranchForm();
                branchList.add(new Branch(bid, name, address, contact, email));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            FXMain.showNotification("Insertion Failed", "Failed to insert new branch.", "warning");

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
        String sql = "insert into users(UserRoleID,BranchID,name,email,password,contact,address)"
                + "output Inserted.UserID values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userRoleId);
            ps.setInt(2, branchId);
            ps.setString(3, name);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, contact);
            ps.setString(7, address);
            rs = ps.executeQuery();
            while (rs.next()) {
                int uid = rs.getInt("UserID");
                FXMain.showNotification("New User Created", "Successfully created new user", "success");
                userList.add(new User(email, name, address, contact, branchName, userRoleTitle, uid, branchId, userRoleId));
                clearUsersForm();
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            FXMain.showNotification("SQL Error", "Can't create new user.", "error");
        }
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
        userRolesBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(4);
        });
        customersBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(7);
        });
        employeesBtn.setOnMouseClicked(event -> {
            initEmployeeTab();
            tabPane.getSelectionModel().select(6);
        });
        usersBtn.setOnMouseClicked(event -> {
            initUsersTab();
            tabPane.getSelectionModel().select(2);
        });
        branchesBtn.setOnMouseClicked(event -> {
            initBranchTab();
            tabPane.getSelectionModel().select(3);
        });
        productsBtn.setOnMouseClicked(event -> {
            initProductsTab();
            tabPane.getSelectionModel().select(5);
        });
        salesBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(8);
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
