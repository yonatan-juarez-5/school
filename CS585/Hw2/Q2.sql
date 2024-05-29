/*Yonatan Juarez - Fall 2022
I wrote this query in Live SQL (livesql.oracle.com) 
For simplicity I assumed the name of the table for this question is ClassTable */

/* In this query I count the classname occurences from ClasTable
and group them by descending order */
SELECT ClassName, COUNT(*) AS popular
FROM ClassTable
GROUP BY ClassName ORDER BY popular DESC;