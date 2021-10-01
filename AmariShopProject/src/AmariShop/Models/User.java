/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmariShop.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author fahim
 */
public class User {
    private String branchName,userRoleTitle;
    private int id,branchId,userRoleId;
    private SimpleStringProperty email,name,address,contact;
    

    public User() {
    }

    public User(String email, String name, String address, String contact, String branchName, String userRoleTitle, int id, int branchId, int userRoleId) {
        this.email=new SimpleStringProperty(email);
        this.name=new SimpleStringProperty(name);
        this.address=new SimpleStringProperty(address);
        this.contact=new SimpleStringProperty(contact);
        this.branchName = branchName;
        this.userRoleTitle = userRoleTitle;
        this.id = id;
        this.branchId = branchId;
        this.userRoleId = userRoleId;
    }
    

    public String getUserRoleTitle() {
        return userRoleTitle;
    }

    public void setUserRoleTitle(String userRoleTitle) {
        this.userRoleTitle = userRoleTitle;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    
    
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SimpleStringProperty getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public SimpleStringProperty getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    
    
    
    
}
