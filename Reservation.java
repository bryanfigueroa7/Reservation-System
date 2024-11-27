import java.util.*;


public class Reservation {
    


        // Obtains info from client
        public void reservation(){
            Scanner scanner = new Scanner(System.in);
            Stadium stadium = new Stadium();
            
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
                    scanner.close();
        }
        
     
}