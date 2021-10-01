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
public class Product {
    private String name,model,brand,description,categoryTitle,subcategoryTitle,branchName;
    private int id,purchaseRate,salesRate,discount,quantity;

    public Product() {
    }

    public Product(int id, String name, String model, String brand, String description, String categoryTitle, String subcategoryTitle, int purchaseRate, int salesRate, int discount, int quantity,String branchName) {
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.description = description;
        this.categoryTitle = categoryTitle;
        this.subcategoryTitle = subcategoryTitle;
        this.id = id;
        this.purchaseRate = purchaseRate;
        this.salesRate = salesRate;
        this.discount = discount;
        this.quantity = quantity;
        this.branchName=branchName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getSubcategoryTitle() {
        return subcategoryTitle;
    }

    public void setSubcategoryTitle(String subcategoryTitle) {
        this.subcategoryTitle = subcategoryTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(int purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public int getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(int salesRate) {
        this.salesRate = salesRate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
    
}
