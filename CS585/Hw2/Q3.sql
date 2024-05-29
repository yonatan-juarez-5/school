/*Yonatan Juarez - Fall 2022
I wrote this query in Live SQL (livesql.oracle.com) 
For simplicity I assumed the name of the table for this question is Project */

/*Here I will select PID where the step is 0 and status is completed
and intersect it with the PID where step is 1 and status is waiting
resulting in the P100 PID */

SELECT PID
FROM Project 
WHERE Step = 0 and Status = 'C' 

INTERSECT

SELECT PID
FROM Project 
WHERE Step = 1 and Status != 'C' ;