import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Obtains info from client
        Scanner scanner = new Scanner(System.in);
        Stadium stadium = new Stadium();

        boolean programRunning = true;

        while (programRunning) {
            System.out.println("What would you like to do?");
            System.out.println("1. Finish for the day");
            System.out.println("2. New Client");
            System.out.print("Enter your choice (1 or 2): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("Thank you! Finishing for the day.");
                    programRunning = false; // Exit loop
                    break;

                case 2:
                    System.out.println("\n=== New Client ===");
                    
                    // Obtain client information
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter your phone number: ");
                    String phoneNumber = scanner.nextLine();

                    // Create Client
                    Client client = new Client(name, email, phoneNumber);

                    // Initialize Stadium class
                    // Stadium stadium = new Stadium();

                    boolean continueShopping = true;

                    while (continueShopping) {
                        // Level
                        System.out.println("What level would you like to purchase? ");
                        System.out.println("1. Field Level");
                        System.out.println("2. Main Level");
                        System.out.println("3. Grandstand Level");
                        System.out.print("Enter the number corresponding to your choice: ");
                        int levelChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume leftover newline

                        // Determining level
                        String level = "";
                        switch (levelChoice) {
                            case 1:
                                level = "Field Level";
                                break;
                            case 2:
                                level = "Main Level";
                                break;
                            case 3:
                                level = "Grandstand Level";
                                break;
                            default:
                                System.out.println("Invalid choice. Defaulting to 'Field Level'.");
                                level = "Field Level";
                                break;
                        }

                        // Amount of Tickets
                        int availableTickets = stadium.getAvailableTicketsForLevel(level);
                        int amount = 0;

                        while (true) {
                            System.out.print("How many tickets from " + level + " would you like? ");
                            amount = scanner.nextInt();

                            if (amount > availableTickets) {
                                System.out.println("Cannot reserve " + amount + " tickets for " + level + ". Only "
                                        + availableTickets + " tickets are available.");
                                System.out.println("Would you like to try again with a different amount? (yes/no)");
                                scanner.nextLine(); // Clear newline
                                String response = scanner.nextLine().toLowerCase();
                                if (!response.equals("yes")) {
                                    System.out.println("Returning to level selection.");
                                    break;
                                }
                            } else {
                                // Reserve tickets
                                stadium.reserveSeats(client, level, amount);
                                break;
                            }
                        }

                        // Ask if they want tickets from an additional level
                        System.out.println("Would you like to purchase tickets from another level? (yes/no)");
                        scanner.nextLine(); // Clear
                        String continueResponse = scanner.nextLine().toLowerCase();
                        if (!continueResponse.equals("yes")) {
                            continueShopping = false;
                        }
                    }

                    // Tester
                    System.out.println("\n================= Ticket Details =================");
                    System.out.println("\nName: " + client.getName());
                    System.out.println("Email: " + client.getEmail());
                    System.out.println("Phone Number: " + client.getPhoneNumber());
                    stadium.showReservations();
                    stadium.showAvailability();
                    System.out.println("Total cost for " + client.getName() + ": $" + stadium.getTotalCostForClient(client));
                    System.out.println("\nThank you for your purchase!");
                    System.out.println("\n================= Ticket Details =================");
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1 or 2.");
                    break;
            }
        }

        scanner.close();
    }
}
