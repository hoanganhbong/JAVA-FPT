create database EmployeeDB
use EmployeeDB
create table Employee
(
	Name varchar(50),
	Phone varchar(50),
	Age int
)

create proc sp_danhsach
(@name varchar(50))
as
begin
	select name, phone, age from Employee where name=@name
end
