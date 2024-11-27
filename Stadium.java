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

   /**
     * Constructor for a new Stadium. This constructor initializes the different data structures used in the methods.
     * It also calls the method that initializes the seats of each level: initializeSeats();
     * 
     * @param seatsAvailable           Hashset used to track the unique seats that are available in the level.
     * @param reservation              HashMap used to store the Client and the seat or seats they reserved.
     * @param transactionHistory       LinkedList used to save each transation done in the system to later print in chronological order.
     * @param undoStack                Stack used to undo operations like reservations or cancellations
     * @param fieldLevelQ              Queue used to store the clients that have been waitlisted by the specific level: Field Level
     * @param mainLevelQ               Queue used to store the clients that have been waitlisted by the specific level: Main Level
     * @param grandstandlevelQ         Queue used to store the clients that have been waitlisted by the specific level: Grandstand Level
     * @param waitlistedInfo           HashMap used to store waitlisted clients and the seat or seats they wanted to reserve. 
     * 
     * 
     * 
     */


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
    /**
     * Method that initializes the seats for the specific levels by calling unto the addSeatsForLevel method with correct 
     * information for each one like the level, starting row, capacity and cost.
     */

    private void initializeSeats() {
        addSeatsForLevel("Field Level",  500, 300.0);
        addSeatsForLevel("Main Level",  1000, 120.0);
        addSeatsForLevel("Grandstand Level",  2000, 45.0);
    }
    /**
     * Method used to create seats to specific levels that can either be: Field, Main or Grandstand level while there is 
     * capacity to add a seat in that level.
     */


    private void addSeatsForLevel(String level,  int capacity, double cost) {
        for (int i = 1; i <= capacity; i++) {
            Seats seat = new Seats(level,  i, cost);
            seatsAvailable.add(seat);
        }
    }

     /**
     * Method used to reserve a specific amount of seats for a client dpending on how much they wanted.
     * If those seats are not available, then they are added to the waitlist HashMap and Queue depending on what level they
     * wanted their seats.
     * 
     * @param reservedSeats     List used to store the seats wanted by the client.
     * @param level             String that hold the level of the seat desired.
     * @param amount            Integer that holds the amount of seats desired.
     */

    public void reserveSeats(Client client, String level, int amount) {
        List<Seats> reservedSeats = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
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
            System.out.println("Not enough seats available in " + level + ".");
            System.out.print("Would you like to be added to the waiting list? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
    
            if (response.equals("yes")) {
                // Add client to the waitlist
                List<Seats> waitlistedReservations = waitlistedInfo.getOrDefault(client, new ArrayList<>());
                waitlistedReservations.addAll(reservedSeats);
                waitlistedInfo.put(client, waitlistedReservations);

                if (level.equals("Field Level")) {
                    fieldLevelQ.add(client);
                } else if (level.equals("Main Level")) {
                    mainLevelQ.add(client);
                } else if (level.equals("Grandstand Level")) {
                    grandstandlevelQ.add(client);
                }
    
                System.out.println("Client (" + client.getName() + ") was added to the waitlist for level: " + level + " for the amount of " + amount + " seat(s).");
            } else {
                System.out.println("Client (" + client.getName() + ") was not added to the waitlist.");
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
    /**
     * Method that calculates the available tickets for reservation based on the specific level.
     * @param level            String that hold the level of the seat desired.
     */
    public int getAvailableTicketsForLevel(String level) {
        int count = 0;
        for (Seats seat : seatsAvailable) {
            if (seat.getSection().equals(level)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Method used to upgrade the waitlisted clients to the newly available seats and then they are removed from the 
     * waitlists. It checks if the amount of seats desired are available, if they are not then they are not upgraded until the 
     * amount of seats wanted are available.
     * 
     * @param fieldLevelQ            Queue stores waitlisted clients that are in this specific level: Field level
     * @param mainLevelQ             Queue stores waitlisted clients that are in this specific level: Main level
     * @param grandstandlevelQ       Queue stores waitlisted clients that are in this specific level: Grandstand level
     * @param waitlistedInfo         HashMap used to compare clients in Queues to find a match and access how many seats the client wanted.
     */

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
                        waitlistedInfo.remove(clientFind);
                        System.out.println("Reservation made for " + clientNext + " in level " + level + "  for the amount of " + amount + " seat(s).");
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
                        waitlistedInfo.remove(clientFind);
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
                        waitlistedInfo.remove(clientFind);
                        System.out.println("Reservation made for " + clientNext + " in level " + level + " for the amount of " + amount + " seat(s).");
                    }
                }
                else {
                    System.out.println(clientNext + " not found in waitlist.");
                }
            }
        }

    }

    /**
     * Method that prints out every client who has reserved and how many seats they reserved.
     */

    public void showReservations() {
        System.out.println("\nReservations:");
        for (Map.Entry<Client, List<Seats>> entry : reservation.entrySet()) {
            int numSeatsReserved = entry.getValue().size();  
            double totalCost = 0.0;
            for (Seats seat : entry.getValue()) {
                totalCost += seat.getCost();
            }
            System.out.println(entry.getKey().getName() + ": " + numSeatsReserved + " tickets reserved, Total Cost: $" + totalCost);
        }
    }

    /**
     * Method that prints out all the seat availability based on the specific level. Call the method 
     * getAvailableTicketsForLevel() to print the correct amount.
     */

    public void showAvailability() {
        System.out.println("\nSeat Availability by Level:");
        System.out.println("Field Level: " + getAvailableTicketsForLevel("Field Level") + " seats available");
        System.out.println("Main Level: " + getAvailableTicketsForLevel("Main Level") + " seats available");
        System.out.println("Grandstand Level: " + getAvailableTicketsForLevel("Grandstand Level") + " seats available");
    }
    /**
     * Method that prints out every waitlisted client and how many seats they want to reserve.
     */
    public void showWaitlisted() {
        System.out.println("\nWaitlisted:");
        for (Map.Entry<Client, List<Seats>> entry : waitlistedInfo.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getKey() + " seat(s) waitlisted.");
        }
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    /**
     * Method to cancel a clients reservation using their email to identify them. Once the seat reservation is cancelled,
     * the seat will become available for other clients to reserve.
     * 
     * @param email             User email is stored in this variable by user input.
     */
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

    public void undoLastAction() {
    if (!undoStack.isEmpty()) {
        String lastAction = undoStack.pop();
        System.out.println("Undoing last action: " + lastAction);

        // Check if last action was a reservation or cancellation
        if (lastAction.startsWith("Reservation")) {
            String[] actionDetails = lastAction.split(":");
            String clientName = actionDetails[1].split(",")[0].trim();

            // Undo reservation
            Client client = getClientByName(clientName);
            if (client != null) {
                List<Seats> reservedSeats = reservation.get(client);
                if (reservedSeats != null && !reservedSeats.isEmpty()) {
                    seatsAvailable.addAll(reservedSeats); // Return seats to available pool
                    reservation.remove(client);          // Remove reservation
                    System.out.println("Reservation for " + clientName + " has been undone.");
                } else {
                    System.out.println("No reserved seats found for " + clientName + " to undo.");
                }
            } else {
                System.out.println("Client not found for undoing reservation: " + clientName);
            }

        } else if (lastAction.startsWith("Cancellation")) {
            String[] actionDetails = lastAction.split(":");
            String clientName = actionDetails[1].trim();

            // Undo cancellation
            Client client = getClientByName(clientName);
            if (client != null) {
                List<Seats> previouslyReservedSeats = waitlistedInfo.get(client);
                if (previouslyReservedSeats != null && !previouslyReservedSeats.isEmpty()) {
                    seatsAvailable.removeAll(previouslyReservedSeats); // Re-reserve the seats
                    reservation.put(client, previouslyReservedSeats); // Restore reservation
                    waitlistedInfo.remove(client);                   // Remove from waitlisted info
                    System.out.println("Cancellation for " + clientName + " has been undone.");
                } else {
                    System.out.println("No cancelled seats found for " + clientName + " to undo.");
                }
            } else {
                System.out.println("Client not found for undoing cancellation: " + clientName);
            }
        } else {
            System.out.println("Unknown action type in undo stack: " + lastAction);
        }
    } else {
        System.out.println("No actions to undo.");
    }
}
    
    private Client getClientByName(String name) {
        for (Client client : reservation.keySet()) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }
    
    
}