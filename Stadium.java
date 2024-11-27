import java.util.*;

public class Stadium {
    private Set<Seats> seatsAvailable;
    private Map<Client, List<Seats>> reservation;
    private Queue<Client> waitList;
    private LinkedList<String> transactionHistory;
    private Stack<String> undoStack;

    public Stadium() {
        seatsAvailable = new HashSet<>();
        reservation = new HashMap<>();
        waitList = new LinkedList<>();
        transactionHistory = new LinkedList<>();
        undoStack = new Stack<>();
        initializeSeats();
    }

    public LinkedList<String> getTransactionHistory(){
        return this.transactionHistory;
    }

    private void initializeSeats() {
        addSeatsForLevel("Field Level", 1, 500, 300.0);
        addSeatsForLevel("Main Level", 1, 1000, 120.0);
        addSeatsForLevel("Grandstand Level", 1, 2000, 45.0);
    }

    private void addSeatsForLevel(String level, int startRow, int capacity, double cost) {
        for (int i = 1; i <= capacity; i++) {
            Seats seat = new Seats(level, startRow, i, cost);
            seatsAvailable.add(seat);
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
            waitList.add(client);
        }
    }

    public boolean cancelReservation(String email) {
        Client clientToCancel = null;

        for (Client client : reservation.keySet()) {
            if (client.getEmail().equals(email)) {
                clientToCancel = client;
                break;
            }
        }

        if (clientToCancel == null) {
            return false; // No matching client found
        }

        List<Seats> canceledSeats = reservation.remove(clientToCancel);
        if (canceledSeats != null) {
            seatsAvailable.addAll(canceledSeats);
            processWaitlist(canceledSeats);

            transactionHistory.add("Canceled reservation for " + clientToCancel.getName());
            undoStack.push("Cancellation: " + clientToCancel.getName() + ", " + canceledSeats.size() + " tickets");

            return true;
        }

        return false;
    }

    private void processWaitlist(List<Seats> newlyAvailableSeats) {
        Iterator<Client> waitlistIterator = waitList.iterator();

        while (waitlistIterator.hasNext() && !newlyAvailableSeats.isEmpty()) {
            Client waitlistedClient = waitlistIterator.next();
            List<Seats> clientSeats = new ArrayList<>();

            for (Iterator<Seats> seatIterator = newlyAvailableSeats.iterator(); seatIterator.hasNext(); ) {
                Seats seat = seatIterator.next();
                if (clientSeats.size() < 1) { // Adjust with waitlist
                    clientSeats.add(seat);
                    seatIterator.remove();
                }
            }

            if (!clientSeats.isEmpty()) {
                reservation.put(waitlistedClient, clientSeats);
                waitlistIterator.remove();

                System.out.println("Assigned " + clientSeats.size() + " seats to " + waitlistedClient.getName() + " from the waitlist.");
            }
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

    public void showReservations() {
        System.out.println("\nReservations:");
        for (Map.Entry<Client, List<Seats>> entry : reservation.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue().size() + " tickets reserved.");
        }
    }

    public void showAvailability() {
        System.out.println("\nAvailable Tickets:");
        Map<String, Integer> levels = new HashMap<>();
        for (Seats seat : seatsAvailable) {
            levels.put(seat.getSection(), levels.getOrDefault(seat.getSection(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : levels.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " tickets available.");
        }
    }
}