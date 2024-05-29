/*Yonatan Juarez - Fall 2022
I wrote this query in Live SQL (livesql.oracle.com) 
For simplicity I assumed the name of the table for this question is Courses
 */

/*I am selecting the distinct instructors from 
courses table who teach 'EDM synthesis'*/
SELECT DISTINCT Instructor
FROM Courses
WHERE
Subject = 'EDM synthesis'

/*Here I will intersect the distinct instructors from the above select subquery
 and distict instructors courses table who teach 'Electronic Music Fundamentals*/
INTERSECT 

SELECT DISTINCT Instructor
FROM Courses
WHERE
Subject = 'Electronic Music Fundamentals'

/*Lastly, I will intersect a final time with the result from the above intersection
and the distinct instructors who teach 'MIDI controllers' which will result 
in the table containing Dat, Emscr */
INTERSECT 

SELECT DISTINCT Instructor
FROM Courses
WHERE
Subject = 'MIDI controllers';

/* Using interesect will allow me to find the overlapping instructors who
teach the required courses *?