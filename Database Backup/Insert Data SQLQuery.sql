
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

