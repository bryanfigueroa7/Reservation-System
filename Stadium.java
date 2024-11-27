import java.util.*;

public class Stadium {
    private Set<Seats> seatsAvailable;
    private Map<Client, List<Seats>> reservation;
    //private Queue<Client> waitList;
    private LinkedList<String> transactionHistory;
    private Stack<String> undoStack;
    private Queue<Client> fieldLevelQ;
    private Queue<Client> mainLevelQ;
    private Queue<Client> grandstandlevelQ;
    private Map<Client, List<Seats>> waitlistedInfo;




    public Stadium() {
        seatsAvailable = new HashSet<>();
        reservation = new HashMap<>();
        //waitList = new LinkedList<>();
        transactionHistory = new LinkedList<>();
        undoStack = new Stack<>();
        fieldLevelQ = new LinkedList<>();
        mainLevelQ = new LinkedList<>();
        grandstandlevelQ = new LinkedList<>();
        waitlistedInfo = new HashMap<>();
        initializeSeats();
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
            List<Seats> waitlistedReservations = waitlistedInfo.getOrDefault(client, new ArrayList<>());
            waitlistedReservations.addAll(reservedSeats);
            waitlistedInfo.put(client, waitlistedReservations); 

            if(level.equals("Field Level")){
                fieldLevelQ.add(client);

            }
            else if(level.equals("Main Level")){
                mainLevelQ.add(client);
            }
            else if(level.equals("Grandstand Level")){
                grandstandlevelQ.add(client);
            }
            System.out.println("Client (" + client.getName() + ") was added to the waitlist for level: " + level + "for the amount of " + amount + " seat(s).");
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

    public void upgradeWaitlisted(String level, int amount, Queue<Client> fieldLevelQ,Queue<Client> mainLevelQ,Queue<Client> grandstandlevelQ, Map<Client, List<Seats>> waitlistedInfo, Set<Seats> seatsAvailable){
        int seatCounter = 0;

        if(level.equals("Field Level")){
            Client clientNext = fieldLevelQ.peek();

            for(Map.Entry<Client, List<Seats>> entry : waitlistedInfo.entrySet()){
                Client clientFind = entry.getKey();

                List<Seats> requestedSeats = waitlistedInfo.get(clientFind);
                boolean availabilitySeats = true;


                if(clientFind.equals(clientNext)){
                    System.out.println(clientNext + " was found in waitlist.");

                    if(requestedSeats == null || requestedSeats.isEmpty() || requestedSeats.size() == 0){
                        System.out.println(clientNext + " has not reserved any seats.");
                        availabilitySeats = false;
                            break;
                    }

                    for(Seats seats : requestedSeats){
                        if(seatsAvailable.contains(seats)){
                            seatCounter=seatCounter+1;
                            continue;
                        }
                        else{
                            availabilitySeats = false;
                            System.out.println("Not enough seats to reserve for " + clientNext);
                            break;
                        }
                    }

                    while(availabilitySeats == true){
                        reserveSeats(clientNext,level,seatCounter);
                        fieldLevelQ.poll();
                        System.out.println("Reservation made for " + clientNext + " in level " + level + " for the amount of " + amount + " seat(s).");
                    }
                }
                else {
                    System.out.println(clientNext + " not found in waitlist.");
                }
            }
        }

        if(level.equals("Main Level")){
            Client clientNext = mainLevelQ.peek();

            for(Map.Entry<Client, List<Seats>> entry : waitlistedInfo.entrySet()){
                Client clientFind = entry.getKey();

                List<Seats> requestedSeats = waitlistedInfo.get(clientFind);
                boolean availabilitySeats = true;


                if(clientFind.equals(clientNext)){
                    System.out.println(clientNext + " was found in waitlist.");

                    if(requestedSeats == null || requestedSeats.isEmpty() || requestedSeats.size() == 0){
                        System.out.println(clientNext + " has not reserved any seats.");
                        availabilitySeats = false;
                            break;
                    }

                    for(Seats seats : requestedSeats){
                        if(seatsAvailable.contains(seats)){
                            seatCounter=seatCounter+1;
                            continue;
                        }
                        else{
                            availabilitySeats = false;
                            System.out.println("Not enough seats to reserve for " + clientNext);
                            break;
                        }
                    }

                    while(availabilitySeats == true){
                        reserveSeats(clientNext,level,seatCounter);
                        mainLevelQ.poll();
                        System.out.println("Reservation made for " + clientNext + " in level " + level + " for the amount of " + amount + " seat(s).");
                    }
                }
                else {
                    System.out.println(clientNext + " not found in waitlist.");
                }
            }
        }

        if(level.equals("Grandstand Level")){
            Client clientNext = grandstandlevelQ.peek();

            for(Map.Entry<Client, List<Seats>> entry : waitlistedInfo.entrySet()){
                Client clientFind = entry.getKey();

                List<Seats> requestedSeats = waitlistedInfo.get(clientFind);
                boolean availabilitySeats = true;


                if(clientFind.equals(clientNext)){
                    System.out.println(clientNext + " was found in waitlist.");

                    if(requestedSeats == null || requestedSeats.isEmpty() || requestedSeats.size() == 0){
                        System.out.println(clientNext + " has not reserved any seats.");
                        availabilitySeats = false;
                            break;
                    }

                    for(Seats seats : requestedSeats){
                        if(seatsAvailable.contains(seats)){
                            seatCounter=seatCounter+1;
                            continue;
                        }
                        else{
                            availabilitySeats = false;
                            System.out.println("Not enough seats to reserve for " + clientNext);
                            break;
                        }
                    }

                    while(availabilitySeats == true){
                        reserveSeats(clientNext,level,seatCounter);
                        grandstandlevelQ.poll();
                        System.out.println("Reservation made for " + clientNext + " in level " + level + " for the amount of " + amount + " seat(s).");
                    }
                }
                else {
                    System.out.println(clientNext + " not found in waitlist.");
                }
            }
        }

    }

    public void showReservations() {
        System.out.println("\nReservations:");
        for (Map.Entry<Client, List<Seats>> entry : reservation.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue().size() + " tickets reserved.");
        }
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public boolean cancelReservation(String email) {
        Client clientToCancel = null;
    
        // Find the client with the matching email
        for (Client client : reservation.keySet()) {
            if (client.getEmail().equals(email)) {
                clientToCancel = client;
                break;
            }
        }
    
        if (clientToCancel != null) {
            // Retrieve the reserved seats 
            List<Seats> reservedSeats = reservation.get(clientToCancel);
            if (reservedSeats != null) {
                seatsAvailable.addAll(reservedSeats);
            }
    
            // Remove the reservation and record the transaction
            reservation.remove(clientToCancel);
            transactionHistory.add("Cancelled reservation for " + clientToCancel.getName());
            undoStack.push("Cancellation: " + clientToCancel.getName());
    
            return true;
        }
    
        return false;
    }
    

    public void showAvailability() {
        System.out.println("\nSeat Availability by Level:");
        System.out.println("Field Level: " + getAvailableTicketsForLevel("Field Level") + " seats available");
        System.out.println("Main Level: " + getAvailableTicketsForLevel("Main Level") + " seats available");
        System.out.println("Grandstand Level: " + getAvailableTicketsForLevel("Grandstand Level") + " seats available");
    }
}