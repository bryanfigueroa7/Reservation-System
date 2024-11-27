import java.util.*;

public class Main {
    /**
     * Method used to do several operations and obtain all the necessary information from the client. Such as their personal information, what level 
     * they want to be in and how many seats they want. It also prints the final information like how many clients have reserved, 
     * how many clients are in the waitlist, how many seats are available by level, the final total cost of the reserved seats 
     * and the transation history.
     * 
     * @param scanner               Used to ask and receive user input and store in specific variables.
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stadium stadium = new Stadium();
        Reservation reservation = new Reservation();

        boolean programRunning = true;

        System.out.println("=================================================================");
        System.out.println("============== Welcome to Wrigley Field Ticket Shop==============");
        System.out.println("=================== Home of the Chicago Cubs ====================");
        System.out.println("=================================================================");
        while (programRunning) {
            System.out.println("What would you like to do?");
            System.out.println("1. Finish for the day");
            System.out.println("2. New Client");
            System.out.println("3. Cancel Reservation");
            System.out.print("Enter your choice (1, 2, or 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Thank you! Finishing for the day.");
                    System.out.println(stadium.getTransactionHistory());
                    programRunning = false; // Exit loop
                    break;

                case 2:
                    System.out.println("\n========================= New Client ===========================");

                    // Obtain client information
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter your phone number: ");
                    String phoneNumber = scanner.nextLine();

                    // Create Client
                    Client client = new Client(name, email, phoneNumber);

                    boolean continueShopping = true;

                    while (continueShopping) {
                        // Level 
                        System.out.println("What level would you like to purchase? ");
                        System.out.println("1. Field Level");
                        System.out.println("2. Main Level");
                        System.out.println("3. Grandstand Level");
                        System.out.println("4. Done");
                        System.out.print("Enter the number corresponding to your choice: ");
                        int levelChoice = scanner.nextInt();
                        scanner.nextLine(); 

                        if (levelChoice == 4) {
                            continueShopping = false;
                        } else {
                            System.out.print("Enter number of tickets: ");
                            int numTickets = scanner.nextInt();
                            scanner.nextLine(); // consume newline

                            String level = "";
                            if (levelChoice == 1) level = "Field Level";
                            else if (levelChoice == 2) level = "Main Level";
                            else if (levelChoice == 3) level = "Grandstand Level";

                            stadium.reserveSeats(client, level, numTickets);
                        }
                    }

                    // Tester
                    System.out.println("\n======================== Ticket Details ==========================");
                    System.out.println("\nName: " + client.getName());
                    System.out.println("Email: " + client.getEmail());
                    System.out.println("Phone Number: " + client.getPhoneNumber());
                    stadium.showReservations();
                    stadium.showWaitlisted();
                    stadium.showAvailability();
                    //System.out.println("Total cost for " + client.getName() + ": $" + stadium.getTotalCostForClient(client));
                    System.out.println("\nThank you for your purchase!");
                    System.out.println("\n======================== Ticket Details ==========================");
                    
                    break;
                case 3:
                    System.out.println("\n===================== Cancel Reservation ========================");
                    System.out.print("Enter email to cancel reservation: ");
                    String cancelEmail = scanner.nextLine();
                    boolean canceled = stadium.cancelReservation(cancelEmail);

                    if (canceled) {
                        System.out.println("Reservation for " + cancelEmail + " has been cancelled.");
                    } else {
                        System.out.println("No reservation found for the provided email.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        }

        scanner.close();
    }
}