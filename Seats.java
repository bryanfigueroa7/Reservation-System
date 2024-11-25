import java.util.Objects;

public class Seats {
    private String section;  // Assumming the sections are the Levels
    private int row;         // Row number, we'll assume it's 10 seats per row
    private int seatNum;     // Seat number
    private double cost;     // Cost of the seat

    // Constructor
    public Seats(String section, int row, int seatNum, double cost) {
        this.section = section;
        this.row = row;
        this.seatNum = seatNum;
        this.cost = cost;
    }

    // Getters
    public String getSection() {
        return this.section;
    }

    public int getRow() {
        return this.row;
    }

    public int getSeatNum() {
        return this.seatNum;
    }

    public double getCost() {
        return this.cost;
    }

    // Setters
    public void setSection(String section) {
        this.section = section;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // Print seat details
    @Override
    public String toString() {
        return "Seat Details:\n" +
               "Section: " + this.section + "\n" +
               "Row: " + this.row + "\n" +
               "Seat Number: " + this.seatNum + "\n" +
               "Cost: $" + this.cost;
    }

    // Equals and HashCode 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Seats seat = (Seats) obj;
        return row == seat.row && seatNum == seat.seatNum && section.equals(seat.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, row, seatNum);
    }
}
