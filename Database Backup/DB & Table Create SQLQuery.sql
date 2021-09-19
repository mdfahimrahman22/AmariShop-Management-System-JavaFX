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

--> Altering default value of ShopID in Branch table
ALTER TABLE Branch
ADD CONSTRAINT ShopID
DEFAULT 1 FOR ShopID;

CREATE TABLE UserRole(
UserRoleID INT IDENTITY(1,1) PRIMARY KEY,
user_role_title VARCHAR(30) NOT NULL unique,
)

alter table UserRole 
add user_role_description text

CREATE TABLE Users(
UserID INT IDENTITY(1,1) PRIMARY KEY,
BranchID INT NOT NULL FOREIGN key REFERENCES Branch(BranchID) on delete cascade on update cascade,
UserRoleID INT NULL FOREIGN key REFERENCES UserRole(UserRoleID) on delete set null on update cascade,
name VARCHAR(30) NOT NULL,
email VARCHAR(30) NOT NULL,
password VARCHAR(30) NOT NULL,
address VARCHAR(200) NULL,
contact VARCHAR(20) NULL,
created_at datetime default current_timestamp
)
ALTER TABLE Users
ADD UNIQUE (email);

CREATE TABLE EmployeePosition(
EmployeePositionID INT IDENTITY(1,1) PRIMARY KEY,
position_title VARCHAR(30) NOT NULL,
)


CREATE TABLE Employee(
EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
BranchID INT NOT NULL FOREIGN key REFERENCES Branch(BranchID),
EmployeePositionID INT NOT NULL  FOREIGN key REFERENCES EmployeePosition(EmployeePositionID),
employee_name VARCHAR(30) NOT NULL,
employee_address VARCHAR(200) NULL,
employee_contact VARCHAR(20) NULL,
employee_email VARCHAR(30) NULL,
employee_salary INT NULL
)


CREATE TABLE Category(
CategoryID INT IDENTITY(1,1) PRIMARY KEY,
category_title VARCHAR(30) NOT NULL
)

CREATE TABLE Subcategory(
SubcategoryID INT IDENTITY(1,1) PRIMARY KEY,
subcategory_title VARCHAR(30) NOT NULL,
CategoryID INT NOT NULL FOREIGN KEY REFERENCES Category(CategoryID),
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
CategoryID INT NOT NULL FOREIGN KEY REFERENCES Category(CategoryID),
BranchID INT NOT NULL FOREIGN KEY REFERENCES Branch(BranchID)
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