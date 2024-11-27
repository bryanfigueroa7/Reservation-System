Reservation-System
Bryan Figueroa bryan.figueroa7@upr.edu
Karla Ruiz karla.ruiz11@upr.edu
Kaysha Pagan kaysha.pagan@upr.edu
Implementamos un sistema de reservación de asientos para un estadio de baseball.
Client
In this class we store the information of the client we are attending at the moment.
The information it takes in is the clients name, their email and their phone number. We obtain this information through user input when we add a new client to our system.
This class also has getter and setter methods for these variables so we can access and manipulate them with ease.

Reservation
The method reservation in this class only runs when a new client is added to our system. The method takes in all the necessary information to populate the Client class such as their name, email and phone number.

Seats
In this class we store all the information regarding individual seats in our stadium. Stored in this class is the section, row, seat number and cost of the individual seat. We also have getter and setter methods for these variables so we can access and manipulate them with ease.

Stadium
In the Stadium class is where most of the heavy lifting happens for our ticketing system. We declare our the data structures used like our Queues for storing waitlisted clients by level they desire. Maps that hold the client information either when that client has successfully reserved a seat or if they have been added to the waitlist option. 

Main
Our Main class handles the scanner printing and obtaining the user input as well as assigning them to specific variables that will be used in other classes.