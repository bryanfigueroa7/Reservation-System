import java.util.*;

public class Stadium {
    private Set<Seats> seatsAvailable;
    private Map<Client, List<Seats>> reservation;
    //private Queue<Client> waitList;
    private LinkedList<String> transactionHistory;
    private Stack<String> undoStack;
    private Queue<Client> waitlistedInfo;

    public Stadium() {
        seatsAvailable = new HashSet<>();
        reservation = new HashMap<>();
        //waitList = new LinkedList<>();
        transactionHistory = new LinkedList<>();
        undoStack = new Stack<>();
        waitlistedInfo = new LinkedList<>();
        initializeSeats();
    }

    private void initializeSeats() {
        addSeatsForLevel("Field Level", 1, 500, 300.0);
        addSeatsForLevel("Main Level", 1, 1000, 120.0);
        addSeatsForLevel("Grandstand Level", 1, 5, 45.0);
    }
    
    //

    private void addSeatsForLevel(String level, int startRow, int capacity, double cost) {
    	int seatsInRow = 25; //Placed this number 25 because I searched an estimate of how many seats are usually in rows at stadiums.
    	int numInRow = 1;
    	int rowCurrent = startRow;
        for (int i = 1; i <= capacity; i++) {
            Seats seat = new Seats(level, rowCurrent, numInRow, cost);
            numInRow =numInRow +1;
            seatsAvailable.add(seat);
            
            if(numInRow > seatsInRow) {
            	rowCurrent= rowCurrent +1;
            	numInRow =1;
            	
            }
        }
        
    }

    public void reserveSeats(Client client, String level, int amount) { 
        List<Seats> reservedSeats = new ArrayList<>();
        
        // Collect seats for the specified level
        for (Seats seat : seatsAvailable) {
            if (seat.getSection().equals(level) && reservedSeats.size() < amount) {
                reservedSeats.add(seat);
            }
        }
    
        // Check if there's enough seats
        if (reservedSeats.size() == amount) {
            seatsAvailable.removeAll(reservedSeats);
            
            // Add reservations
            List<Seats> clientReservations = reservation.getOrDefault(client, new ArrayList<>());
            clientReservations.addAll(reservedSeats);
            reservation.put(client, clientReservations);
    
            // Record transaction
            transactionHistory.add("Reserved " + amount + " tickets for " + client.getName());
            undoStack.push("Reservation: " + client.getName() + ", " + amount + " tickets");
            
            System.out.println("Reservation successful for " + amount + " tickets at " + level + ".");
        } else {
            System.out.println("Not enough seats available in " + level + ". Adding client to the waitlist.");
            waitlistedInfo.add(client);
        }
    }
    
    public double getTotalCostForClient(Client client) {
        List<Seats> clientSeats = reservation.get(client);
        if (clientSeats == null || clientSeats.isEmpty()) {
            return 0.0; // No tickets reserved 
        }
        double totalCost = 0.0;
        for (Seats seat : clientSeats) {
            totalCost += seat.getCost();
        }
        return totalCost;
    }

    public int getAvailableTicketsForLevel(String level) {
        int count = 0;
        for (Seats seat : seatsAvailable) {
            if (seat.getSection().equals(level)) {
                count++;
            }
        }
        return count;
    }
    
    //Waitlisted clients
    
    public void addWaitlisted(Client client, String level, int amount) {
    	waitlistedInfo.add(client);
    	System.out.println("Client (" + client.getName() + ")" + " has been added to the waitlist for Section: " + level + " wanting " + amount + " seat(s).");
    	
    }
    
    public void upgradeWaitlisted(String level, int amount, int levelChoice) {
    	if(waitlistedInfo.isEmpty()) {
    		System.out.println("Error! Can't upgrade waitlisted client because the waitlist is empty.");
    		return;
    	}
    	Client clientWL = waitlistedInfo.peek();
    	
    	if(levelChoice >= amount ) {
    		reserveSeats(clientWL, level, amount); // can they get more than one seat reserved at a time?
    		System.out.println("Upgraded waitlisted client (" + clientWL.getName() + ") to section: " + level + "with " + amount + "seat(s)");
    		waitlistedInfo.poll();
    	}
    	else {
    		System.out.println("There isn't enough seats to reserve client (" + clientWL.getName() + ") to section: " + level + " with " + amount + " seat(s)");
    		
    	}
    }
    

    public void showReservations() {
        System.out.println("\nReservations:");
        for (Map.Entry<Client, List<Seats>> entry : reservation.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue().size() + " tickets reserved.");
        }
    }

    public void showAvailability() {
        System.out.println("\nSeat Availability by Level:");
        System.out.println("Field Level: " + getAvailableTicketsForLevel("Field Level") + " seats available");
        System.out.println("Main Level: " + getAvailableTicketsForLevel("Main Level") + " seats available");
        System.out.println("Grandstand Level: " + getAvailableTicketsForLevel("Grandstand Level") + " seats available");
    }
}
