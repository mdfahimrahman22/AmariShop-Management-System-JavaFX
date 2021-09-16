/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmariShop.Dashboard;

import AmariShop.FXMain;
import AmariShop.Models.Branch;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author fahim
 */
public class AddBranchController implements Initializable {

    private Connection connection;
    private ObservableList<Branch> branchList;

    @FXML
    private ImageView cancelBtn;
    @FXML
    private Button addBranchBtn, updateBranchBtn;
    @FXML
    private TextField nameField, emailField, contactField;
    @FXML
    private TextArea addressField;

    private TableView<Branch> branchTable;
    private Branch branch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setOnClickListeners();
    }

    public void initializeAddBranch(Connection connection, ObservableList<Branch> branchList) {
        updateBranchBtn.setVisible(false);
        addBranchBtn.setVisible(true);
        this.connection = connection;
        this.branchList = branchList;

    }

    public void initializeUpdateBranch(Connection connection, TableView<Branch> branchTable, Branch branch) {
        this.branch = branch;
        this.branchTable = branchTable;
        this.connection=connection;
        setBranchDetails(branch);
        updateBranchBtn.setVisible(true);
        addBranchBtn.setVisible(false);
    }

    private void setBranchDetails(Branch branch) {
        nameField.setText(branch.getName());
        emailField.setText(branch.getEmail());
        addressField.setText(branch.getAddress());
        contactField.setText(branch.getContact());
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        addressField.setText("");
        contactField.setText("");
    }

    private void addBranch() {
        String sql = "insert into Branch(branch_name,branch_address,branch_email,branch_contact)\n"
                + "values (?,?,?,?)";
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String contact = contactField.getText();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, email);
            ps.setString(4, contact);
            int res = ps.executeUpdate();
            if (res == 1) {
                ResultSet rs = connection.prepareStatement(String.format("select BranchID from Branch where branch_name='%s' and branch_email='%s'", name, email)).executeQuery();
                int bid = 0;
                while (rs.next()) {
                    bid = rs.getInt("BranchID");
                }
                FXMain.showNotification("Insert Successful", "New Branch inserted successfully", "successs");
                clearFields();
                branchList.add(new Branch(bid, name, address, contact, email));

            } else {
                FXMain.showNotification("Insertion Failed", "Failed to insert new branch.", "warning");
            }
        } catch (SQLException ex) {
            System.out.println(ex);

        }
    }

    private void updateBranch(int id, String name, String email, String address, String contact) {
        String sql = "update Branch set branch_name=?,branch_email=?,branch_address=?,branch_contact=? where BranchID=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, contact);
            ps.setInt(5, id);

            int rs = ps.executeUpdate();
            if (rs == 1) {
                branchTable.getItems().remove(branch);
                branch = new Branch(id, name, address, contact, email);
                branchTable.getItems().add(branch);
                FXMain.showNotification("Branch Info Updated", "Successfully updated branch information.", "success");
            } else {
                FXMain.showNotification("Failed to update the branch", "Something went worng.", "warning");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    private void setOnClickListeners() {
        addBranchBtn.setOnMouseClicked(event -> {
            addBranch();
        });

        updateBranchBtn.setOnMouseClicked(event -> {
            updateBranch(branch.getId(), nameField.getText(), emailField.getText(), addressField.getText(), contactField.getText());
        });
        cancelBtn.setOnMouseClicked(event -> {
            FXMain.closePanel(event);
        });

    }

}
