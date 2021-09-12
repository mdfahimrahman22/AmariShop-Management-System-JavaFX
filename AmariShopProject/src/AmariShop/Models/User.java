/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmariShop.Models;

/**
 *
 * @author fahim
 */
public class User {
    private String email,name,address,contact,branchName,userRoleTitle;
    private int id,branchId,userRoleId;

    public User() {
    }

    public User(String email, String name, String address, String contact, String branchName, String userRoleTitle, int id, int branchId, int userRoleId) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.contact = contact;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    
    
    
    
}
