-- get User Profile
select u.userid,u.name,u.email,u.password,u.address,u.contact,
u.created_at,b.branchid,b.branch_name,r.UserRoleID,r.user_role_title from 
Users u 
inner join Branch b on u.BranchID= b.BranchID 
inner join UserRole r on u.UserRoleID=r.UserRoleID 
where u.email='fahimpranto002@gmail.com' and u.password='123456' 

--change user profile
update users set name='Fahim Rahman', email='fahimpranto002@gmail.com',contact='01615990017',address='2/a Modhubagh, Dhaka' where userid=1 

--update password
update users set password='123456' where UserID=1 and password='1234567'

--add new user
insert into users(UserRoleID,BranchID,name,email,password) values 
(1,1,'fahim','fahim2@gmail.com','123456')


select * from Branch

select UserID from users where email=''
--check
select * from users where userid=1
select * from UserRole
select * from Branch
