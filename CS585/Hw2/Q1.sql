/*Yonatan Juarez - Fall 2022
I wrote this query in Live SQL (livesql.oracle.com) 
CREATE TABLE ProjectRoomBookings
(roomNum INTEGER NOT NULL,
startTime INTEGER NOT NULL,
endTime INTEGER NOT NULL,
groupName CHAR(10) NOT NULL,
PRIMARY KEY (roomNum, startTime));*/

/* Textual Explanation */

/*At first glance, a simple way to solve the first issue is to check prior to booking, that
the start time is at least 1 hour less than the end time (for simplicity, since not dealing
with minutes and only integers). To solve the second issue we can add another column named
'Booked' that will hold a char 'Y' or 'N' to help keep track whether the room is booked. 
When a new booking is made, we can check the 'Booked' column for the room number to 
determine if it is booked, if it is, no need to proceed and let user know to try a 
time slot. If no, then we can check that the start_time < end_time. */ 