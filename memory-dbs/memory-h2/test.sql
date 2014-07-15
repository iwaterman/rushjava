DROP TABLE IF EXISTS TEST;
CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));
INSERT INTO TEST VALUES(1, 'Hello');
INSERT INTO TEST VALUES(2, 'World');


DROP TABLE IF EXISTS STUDENTS;
CREATE TABLE STUDENTS
(
stud_id int(11) NOT NULL AUTO_INCREMENT,
name varchar(50) NOT NULL,
email varchar(50) NOT NULL,
dob date DEFAULT NULL,
PRIMARY KEY (stud_id)
);

insert into students(name,email,dob)
values ('Student1','student1@gmail.com','1983-06-25');
insert into students(name,email,dob)
values ('Student2','student2@gmail.com','1993-06-25');

