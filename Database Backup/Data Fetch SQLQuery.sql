--> Get User Profile
select u.userid,u.name,u.email,u.password,u.address,u.contact,
u.created_at,b.branchid,b.branch_name,r.UserRoleID,r.user_role_title from 
Users u 
inner join Branch b on u.BranchID= b.BranchID 
inner join UserRole r on u.UserRoleID=r.UserRoleID 
where u.email='fahimpranto002@gmail.com' and u.password='123456' 

--> Get all user data
select u.userid,u.name,u.email,u.address,u.contact,
u.created_at,b.branchid,b.branch_name,r.UserRoleID,r.user_role_title from 
Users u 
inner join Branch b on u.BranchID= b.BranchID 
inner join UserRole r on u.UserRoleID=r.UserRoleID 

--> Delete User
delete from users where UserID=3

--> Update user profile
update users set name='Fahim Rahman', email='fahimpranto002@gmail.com',contact='01615990017',address='2/a Modhubagh, Dhaka' where userid=1 

--> Update password
update users set password='123456' where UserID=1 and password='1234567'

--> Add new user
insert into users(UserRoleID,BranchID,name,email,password,contact,address) values 
(1,1,'fahim','fahim2@gmail.com','123456','01928304','Dhaka')

--> Update User Details
update users set BranchID=1,UserRoleID=2,name='Fahim Rahman', email='fahimpranto002@gmail.com',contact='01615990017',address='2/a Modhubagh, Dhaka' where userid=1 


--> Branch id
select BranchID from Branch where branch_name='Dhanmondi Branch' and branch_email='b1@gmail.com'

select * from Branch

--> Update branch
update Branch set branch_name='b1',branch_email='b1@gmail.com',branch_contact='0871243',branch_address='mirpur' where BranchID=1

--> Get all Employee data
select em.EmployeeID,em.branchid,em.employee_name,em.employee_email,em.employee_address,em.employee_contact,
em.employee_salary,b.branchid,b.branch_name,p.position_title,p.EmployeePositionID from 
Employee em
inner join Branch b on em.BranchID= b.BranchID 
inner join EmployeePosition p on em.EmployeePositionID=p.EmployeePositionID 


--> Insert into Employee
insert into Employee(employee_name,employee_email,employee_contact,employee_address,employee_salary,BranchId,EmployeePositionID) values 
('fahim','fahim2@gmail.com','01928304','Dhaka',12000,1,2)

--> Update Employee Info
update Employee set employee_name='',employee_email='',
employee_contact='',employee_address='',employee_salary=0
,BranchId=0,EmployeePositionID=0 where EmployeeID=1

--> Employee search by name
select em.EmployeeID,em.branchid,em.employee_name,em.employee_email,em.employee_address,em.employee_contact,
em.employee_salary,b.branchid,b.branch_name,p.position_title,p.EmployeePositionID from Employee em 
inner join Branch b on em.BranchID= b.BranchID 
inner join EmployeePosition p on em.EmployeePositionID=p.EmployeePositionID 
where em.employee_name like 'Fahim 2'
 
--> Employee Search by branch name
select em.EmployeeID,em.branchid,em.employee_name,em.employee_email,em.employee_address,em.employee_contact,
em.employee_salary,b.branchid,b.branch_name,p.position_title,p.EmployeePositionID from Employee em 
inner join Branch b on em.BranchID= b.BranchID 
inner join EmployeePosition p on em.EmployeePositionID=p.EmployeePositionID 
where b.branch_name like 'Dhanmondi Branch'

--> Employee Search by Position Title
select em.EmployeeID,em.branchid,em.employee_name,em.employee_email,em.employee_address,em.employee_contact,
em.employee_salary,b.branchid,b.branch_name,p.position_title,p.EmployeePositionID from Employee em 
inner join Branch b on em.BranchID= b.BranchID 
inner join EmployeePosition p on em.EmployeePositionID=p.EmployeePositionID 
where p.position_title like 'Manager'

--> Employee Search by Salary
select em.EmployeeID,em.branchid,em.employee_name,em.employee_email,em.employee_address,em.employee_contact,
em.employee_salary,b.branchid,b.branch_name,p.position_title,p.EmployeePositionID from Employee em 
inner join Branch b on em.BranchID= b.BranchID 
inner join EmployeePosition p on em.EmployeePositionID=p.EmployeePositionID 
where em.employee_salary=12000

--> User Search by name/contact/address/email
select u.userid,u.name,u.email,u.address,u.contact,
u.created_at,b.branchid,b.branch_name,r.UserRoleID,r.user_role_title from Users u 
inner join Branch b on u.BranchID= b.BranchID 
inner join UserRole r on u.UserRoleID=r.UserRoleID
where u.name='Fahim 2' 

--> Branch Search by name/contact/address/email
select * from Branch where branch_name like ''

--> Category & Subcategory
select * from Category c inner join Subcategory sc on c.CategoryID=sc.CategoryID

--> Product Details
select p.productid, p.product_name,p.product_model,p.product_brand,p.product_discount,
p.product_description,p.product_purchase_rate,p.product_sales_rate,c.category_title,
s.subcategory_title, p.total_quantity,b.branch_name from Product p 
inner join Subcategory s on s.SubcategoryID=p.SubcategoryID
inner join Category c on s.CategoryID=c.CategoryID
inner join Branch b on b.BranchID=p.BranchID where b.BranchID=1

--> Update product
update Product set product_name='',product_model='',product_brand='',product_description='',
product_purchase_rate=1,product_sales_rate=1,product_discount=1, total_quantity=9,SubcategoryID=1
where ProductID=1

--> Product purchase summary
select sum((product_sales_rate-product_purchase_rate-product_discount)*total_quantity) as total_profit, 
avg((product_sales_rate-product_purchase_rate-product_discount)*total_quantity) as avg_profit, 
max(product_purchase_rate*total_quantity) as max_purchase, 
sum(product_purchase_rate*total_quantity) as total_purchase from Product
where month(created_at) = month(getdate()) and year(created_at) = year(getdate())

--> Product purchase summary Filter by Date
select sum((product_sales_rate-product_purchase_rate-product_discount)*total_quantity) as total_profit, 
avg((product_sales_rate-product_purchase_rate-product_discount)*total_quantity) as avg_profit, 
max(product_purchase_rate*total_quantity) as max_purchase, 
sum(product_purchase_rate*total_quantity) as total_purchase from Product
where cast(created_at as date) between '2021-09-27' and '2021-10-02'


--> Check
select * from users where userid=1
select * from UserRole
select * from Branch
select * from EmployeePosition
select * from Employee
select * from Product