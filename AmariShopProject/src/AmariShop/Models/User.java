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
    private String email,name,userRole,address,contact,branchName;
    private int id,branchId;

    public User() {
    }

    public User(int id,String name,String email, String contact, String address,  String branchName) {
        this.email = email;
        this.name = name;
        this.userRole = userRole;
        this.address = address;
        this.contact = contact;
        this.branchName = branchName;
        this.id = id;
    }

    public User(int id,int branchId,String name,String email, String address, String contact) {
        this.id=id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.branchId = branchId;
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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
