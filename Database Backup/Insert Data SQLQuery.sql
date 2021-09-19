
--> Creating Shop 
insert into Shop(shop_name,shop_address,shop_contact,shop_email)
values ('AmariShop','Dhaka','01615990017','amarishop@support.com')

select * from shop

--> Creating Branch 
insert into Branch(ShopID,branch_name,branch_address,branch_contact)
values (1,'Dhanmondi Branch','7/2a, Dhanmondi, Dhaka','01615990017')

select * from Branch

--> Creating UserRole
set identity_insert userrole on
insert into UserRole(UserRoleID,user_role_title)
values (1,'Shop Admin'),(2,'Branch Admin'),(3,'Operator')
set identity_insert UserRole off

select * from UserRole

--> Deleting all rows from UserRole Table
delete from UserRole

--> Creating User 1
insert into Users(BranchID,UserRoleID,name,email,password,address,contact)
values (1,2,'Fahim Rahman','fahimpranto002@gmail.com','123456','2/a Modhubagh, Dhaka','01615990017')

--> Creating User 2
insert into Users(BranchID,UserRoleID,name,email,password,address)
values (1,3,'Ifrat Jahan Chowdhury','180204003@aust.edu','123456','AUST, Dhaka')

--> Updating userroleid in users table
update users set UserRoleID=1 where userid=1
update users set UserRoleID=2 where userid=2

select * from Users
--> Updating user role descriptions
update UserRole set user_role_description='Allowed to perform all CRUD operation' where UserRoleID=1 
update UserRole set user_role_description='Allowed to perform all CRUD operation on specific branch' where UserRoleID=2
update UserRole set user_role_description='Allowed to perform all CRUD operation  on specific branch' where UserRoleID=3

select * from UserRole

--> Inserting Employee Positions
set identity_insert EmployeePosition on
insert into EmployeePosition(EmployeePositionID,position_title)
values (1,'Manager'),(2,'Accountant'),(3,'Cashier'),(4,'Cleaner')
set identity_insert EmployeePosition off

--> Insert Employee
insert into Employee(BranchID,EmployeePositionID,employee_name,employee_email,employee_contact,employee_address,employee_salary)
values(1,1,'Fahim','fahim@gmail.com','01615990017','AUST, Dhaka',12000)

--> Inserting Categories
set identity_insert Category on
insert into Category(CategoryID,category_title) values(1,'Desktop PC')
insert into Category(CategoryID,category_title) values(2,'Laptop')
insert into Category(CategoryID,category_title) values(3,'PC Component')
insert into Category(CategoryID,category_title) values(4,'Networking')
insert into Category(CategoryID,category_title) values(5,'Office Solution')
insert into Category(CategoryID,category_title) values(6,'Software')
insert into Category(CategoryID,category_title) values(7,'Home Security')
insert into Category(CategoryID,category_title) values(8,'Server')
insert into Category(CategoryID,category_title) values(9,'Camera')
insert into Category(CategoryID,category_title) values(10,'Gaming')
insert into Category(CategoryID,category_title) values(11,'TV')
insert into Category(CategoryID,category_title) values(12,'Accessories')			
set identity_insert Category off
select * from Category
--> Inserting Subcategories

DECLARE @CategoryID AS int=11
insert into Subcategory(CategoryID,subcategory_title) values(@CategoryID,'Sony')
insert into Subcategory(CategoryID,subcategory_title) values(@CategoryID,'Starex')
insert into Subcategory(CategoryID,subcategory_title) values(@CategoryID,'Sky View')
insert into Subcategory(CategoryID,subcategory_title) values(@CategoryID,'Xiaomi MI')
insert into Subcategory(CategoryID,subcategory_title) values(@CategoryID,'LG')
insert into Subcategory(CategoryID,subcategory_title) values(@CategoryID,'Panasonic')



select * from Subcategory