import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Obtains info from client
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        // Create Client
        Client client = new Client(name, email, phoneNumber);

        // Initialize Stadium class
        Stadium stadium = new Stadium();

        boolean continueShopping = true;

        while (continueShopping) {
            // Level
            System.out.println("What level would you like to purchase? ");
            System.out.println("1. Field Level");
            System.out.println("2. Main Level");
            System.out.println("3. Grandstand Level");
            System.out.print("Enter the number corresponding to your choice: ");
            int levelChoice = scanner.nextInt();
            scanner.nextLine();

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
                scanner.nextLine();


                if (amount > availableTickets) {
                    System.out.println("Cannot reserve " + amount + " tickets for " + level + ". Only "
                            + availableTickets + " tickets are available.");
                    System.out.println("\nWhat would you like to do next? \n1) Try again with a different amount \n2) Add me to the waitlist \n3) Main Menu \nSelect between the options: ");
                    scanner.nextLine(); // Clear newline
                    String response = scanner.nextLine().toLowerCase();
                    if (response.equals("1")) {
                        System.out.println("Try a different number.");
                    }
                    else if (response.equals("2")) {
                        stadium.addWaitlisted(client, level, amount);
                        break;
                    }
                    else if (response.equals("3")) {
                        System.out.println("Returning to level selection.");
                        break;
                    }
                    else{
                        System.out.println("Sorry, your input was invalid. Please try again.");
                    }
                }
                else {
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
        scanner.close();
    }
}