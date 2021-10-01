CREATE DATABASE amarishopdb
USE amarishopdb

--> Shop Table
CREATE table Shop
(
ShopID INT IDENTITY(1,1) PRIMARY KEY,
shop_name VARCHAR(50) NOT NULL,
shop_logo VARCHAR(150) NULL,
shop_address VARCHAR(200) NULL,
shop_contact VARCHAR(20) NULL,
shop_email VARCHAR(30) NULL
)

--> Branch Table
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

--> UserRole Table
CREATE TABLE UserRole(
UserRoleID INT IDENTITY(1,1) PRIMARY KEY,
user_role_title VARCHAR(30) NOT NULL unique,
)
--> Adding a column for user role description
alter table UserRole 
add user_role_description text

--> Users Table
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
--> Adding email as a unique key
ALTER TABLE Users
ADD UNIQUE (email);


--> EmployeePosition Table
CREATE TABLE EmployeePosition(
EmployeePositionID INT IDENTITY(1,1) PRIMARY KEY,
position_title VARCHAR(30) NOT NULL,
)

--> Employee Table
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


--> Category Table
CREATE TABLE Category(
CategoryID INT IDENTITY(1,1) PRIMARY KEY,
category_title VARCHAR(30) NOT NULL
)

--> Subcategory Table
CREATE TABLE Subcategory(
SubcategoryID INT IDENTITY(1,1) PRIMARY KEY,
subcategory_title VARCHAR(30) NOT NULL,
CategoryID INT NOT NULL FOREIGN KEY REFERENCES Category(CategoryID),
)

--> Product Table
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
--> Needed to increase varchar size of product_name
ALTER TABLE Product
ALTER COLUMN product_name VARCHAR(100) NOT NULL

--> Needed to change dtype of product_description
ALTER TABLE Product
ALTER COLUMN product_description text NULL

--> Forcefully delete a column if it's a foreign key
alter table Product drop constraint FK__Product__Categor__21B6055D
go
alter table Product drop column CategoryID

--> Add Subcategory column
alter table Product add SubcategoryID INT FOREIGN KEY REFERENCES Subcategory(SubcategoryID)
--> Add created_at
alter table Product add created_at datetime default CURRENT_TIMESTAMP


--> AvailabelProduct Table
CREATE TABLE AvailabeProduct(
AvailabeProductID INT IDENTITY(1,1) PRIMARY KEY,
ProductID INT NOT NULL FOREIGN key REFERENCES Product(ProductID),
BranchID INT NOT NULL FOREIGN key REFERENCES Branch(BranchID)
)

--> Customer Table
CREATE TABLE Customer(
CustomerID INT IDENTITY(1,1) PRIMARY KEY,
customer_name VARCHAR(30) NULL,
customer_phone VARCHAR(20) NULL,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP
)

--> Sales Table
CREATE TABLE Sales(
SalesID INT IDENTITY(1,1) PRIMARY KEY,
CustomerID INT NOT NULL FOREIGN key REFERENCES Customer(CustomerID) on delete cascade,
BranchID INT NOT NULL FOREIGN key REFERENCES Branch(BranchID) on delete cascade,
sales_date DATETIME DEFAULT CURRENT_TIMESTAMP
)


--> SoldProduct Table
CREATE TABLE SoldProduct(
SoldProductID INT IDENTITY(1,1) PRIMARY KEY,
ProductID INT NOT NULL FOREIGN key REFERENCES Product(ProductID) on delete no action,
SalesID INT NOT NULL FOREIGN key REFERENCES Sales(SalesID) on delete cascade,
quantity INT NOT NULL CHECK(quantity>0),
total_amount INT NOT NULL
)