import java.util.*;
/**
 * Represent a seat in the stadium.
 */
public class Seats {
    private String section;  // Level the seat is at
    private int seatNum;     // Seat number
    private double cost;     // Cost of the seat

    /**
     * Constructor  for a new Seat with the specified section, seat number and cost.
     * 
     * @param section           Section or level where the seat is located in the stadium/
     * @param seatNumThe        Number of Seats
     * @param cost              The cost of the seat 
     */

    // Constructor
    public Seats(String section, int seatNum, double cost) {
        this.section = section;
        this.seatNum = seatNum;
        this.cost = cost;
    }

    // Getters
    public String getSection() {
        return this.section;
    }

  public double getSeatNum(){
        return this.seatNum;
    }

    public double getCost() {
        return this.cost;
    }

  

    // Setters
    public void setSection(String section) {
        this.section = section;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // Print seat details
    @Override
    public String toString() {
        return "Seat Details:\n" +
               "Section: " + this.section + "\n" +
               
               "Seat Number: " + this.seatNum + "\n" +
               "Cost: $" + this.cost;
    }

     /**
     * Returns a string representation of the seat's details.
     *
     * @return a formatted string with the section, seat number, and cost
     */

    // Equals and HashCode 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Seats seat = (Seats) obj;
        return  seatNum == seat.seatNum && section.equals(seat.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section,  seatNum);
    }
}