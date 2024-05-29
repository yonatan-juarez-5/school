/*Yonatan Juarez - Fall 2022
I wrote this query in Live SQL (livesql.oracle.com) 
I created instructor, class, student, enrolled, and classtaught table to store necessary
information needed to perform claculation of highest bonues. */

/*CREATE TABLE Instructor (ID INTEGER NOT NULL, Hourly_Rate INTEGER NOT NULL, 
First_Name char(10) NOT NULL, Last_Name char(10) NOT NULL)

CREATE TABLE Class(ID INTEGER NOT NULL Class_Name char(30) NOT NULL)

CREATE TABLE Student(ID INTEGER NOT NULL, First_Name char(10) NOT NULL,
 Last_Name char(10) NOT NULL) 

CREATE TABLE Enrolled (Student_ID INTEGER NOT NULL, Class_ID INTEGER NOT NULL)

CREATE TABLE ClassTaught(Instructor_ID INTEGER NOT NULL,  Class_ID INTEGER NOT NULL) */

SELECT MAX(Bonus) as Highest_Bonus_Paid
FROM (
SELECT ID, First_Name, Last_Name,
	SUM(Num_Students) AS Sum_Class_Count,
      Hourly_Rate * SUM(Num_Students) * 0.1 AS Bonus
FROM Instructor

INNER JOIN ClassTaught ON Instructor.ID = ClassTaught.Instructor_ID

INNER JOIN (

SELECT ID, COUNT(Enrolled.Student_ID) as  Num_Students
FROM Class

INNER JOIN Enrolled ON Enrolled.Class_ID = Class.ID
GROUP BY Class.ID ) 
AS Total_1 on ClassTaught.Class_ID = Total_1.ID
GROUP BY Instructor.ID, Instructor.First_Name, Instructor.Last_Name
) AS Total_2;