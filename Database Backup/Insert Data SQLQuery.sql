
--> Creating Shop 
insert into Shop(shop_name,shop_address,shop_contact,shop_email)
values ('AmariShop','Dhaka','01615990017','amarishop@support.com')

select * from shop

--> Creating Branch 
insert into Branch(ShopID,branch_name,branch_address,branch_contact)
values (1,'Dhanmondi Branch','7/2a, Dhanmondi, Dhaka','01615990017')

select * from Branch

--> Creating UserRole
insert into UserRole(user_role_title)
values ('Shop Admin'),('Branch Admin'),('Operator')

select * from UserRole

--> Creating User
insert into Users(BranchID,UserRoleID,name,email,password,address,contact)
values (1,2,'Fahim Rahman','fahimpranto002@gmail.com','123456','2/a Modhubagh, Dhaka','01615990017')

--> Creating User 2
insert into Users(BranchID,UserRoleID,name,email,password,address)
values (1,3,'Ifrat Jahan Chowdhury','180204003@aust.edu','123456','AUST, Dhaka')

select * from Users
--> 

