CREATE DATABASE amarishopdb
USE amarishopdb

CREATE table Shop
(
ShopID INT IDENTITY(1,1) PRIMARY KEY,
shop_name VARCHAR(50) NOT NULL,
shop_logo VARCHAR(150) NULL,
shop_address VARCHAR(200) NULL,
shop_contact VARCHAR(20) NULL,
shop_email VARCHAR(30) NULL
)

CREATE TABLE Branch(
BranchID INT IDENTITY(1,1) PRIMARY KEY,
ShopID INT NOT NULL FOREIGN key REFERENCES Shop(ShopID),
branch_name VARCHAR(30) NOT NULL,
branch_address VARCHAR(200) NULL,
branch_contact VARCHAR(20) NULL,
branch_email VARCHAR(30) NULL
)

CREATE TABLE EmployeeRole(
EmployeeRoleID INT IDENTITY(1,1) PRIMARY KEY,
role_title VARCHAR(30) NOT NULL,
)

CREATE TABLE Employee(
EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
BranchID INT NOT NULL FOREIGN key REFERENCES Branch(BranchID),
EmployeeRoleID INT NOT NULL  FOREIGN key REFERENCES EmployeeRole(EmployeeRoleID),
employee_name VARCHAR(30) NOT NULL,
employee_address VARCHAR(200) NULL,
employee_contact VARCHAR(20) NULL,
employee_email VARCHAR(30) NULL,
employee_pass VARCHAR(30) NULL CHECK(employee_pass>6),
employee_salary INT NULL
)


CREATE TABLE Category(
CategoryID INT IDENTITY(1,1) PRIMARY KEY,
category_title VARCHAR(30) NOT NULL
)

CREATE TABLE Product(
ProductID INT IDENTITY(1,1) PRIMARY KEY,
product_name VARCHAR(30) NOT NULL,
product_description VARCHAR(200) NULL,
product_model VARCHAR(30) NULL,
product_brand VARCHAR(30) NULL,
product_purchase_rate INT NOT NULL,
product_sales_rate INT NOT NULL,
product_discount INT DEFAULT 0,
total_quantity INT DEFAULT 0,
product_type VARCHAR(10) NOT NULL,
CategoryID INT NOT NULL FOREIGN key REFERENCES Category(CategoryID),
)

CREATE TABLE AvailabeProduct(
AvailabeProductID INT IDENTITY(1,1) PRIMARY KEY,
ProductID INT NOT NULL FOREIGN key REFERENCES Product(ProductID),
BranchID INT NOT NULL FOREIGN key REFERENCES Branch(BranchID)
)


CREATE TABLE Customer(
CustomerID INT IDENTITY(1,1) PRIMARY KEY,
customer_name VARCHAR(30) NULL,
customer_phone VARCHAR(20) NULL,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP
)

CREATE TABLE Sales(
SalesID INT IDENTITY(1,1) PRIMARY KEY,
CustomerID INT NOT NULL FOREIGN key REFERENCES Customer(CustomerID) on delete cascade,
BranchID INT NOT NULL FOREIGN key REFERENCES Branch(BranchID) on delete cascade,
sales_date DATETIME DEFAULT CURRENT_TIMESTAMP
)

CREATE TABLE Wholesaler(
WholesalerID INT IDENTITY(1,1) PRIMARY KEY,
wholesaler_name VARCHAR(30) NOT NULL,
wholesaler_contact VARCHAR(20) NOT NULL,
)

CREATE TABLE Purchase(
PurchaseID INT IDENTITY(1,1) PRIMARY KEY,
ProductID INT NOT NULL FOREIGN key REFERENCES Product(ProductID) on delete cascade,
WholesalerID INT NOT NULL FOREIGN key REFERENCES Wholesaler(WholesalerID) on delete cascade,
purchase_date DATETIME DEFAULT CURRENT_TIMESTAMP
)

CREATE TABLE SoldProduct(
SoldProductID INT IDENTITY(1,1) PRIMARY KEY,
ProductID INT NOT NULL FOREIGN key REFERENCES Product(ProductID) on delete no action,
SalesID INT NOT NULL FOREIGN key REFERENCES Sales(SalesID) on delete cascade,
quantity INT NOT NULL CHECK(quantity>0),
total_amount INT NOT NULL
)