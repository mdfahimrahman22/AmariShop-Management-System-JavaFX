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


--> Check
select * from users where userid=1
select * from UserRole
select * from Branch
select * from EmployeePosition
select * from Employee
select * from Product