
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
        String phonenum = scanner.nextLine();

        // Level

        System.out.println("What level would you like to purchase? ");
        System.out.println("1. Field Level");
        System.out.println("2. Main Level");
        System.out.println("3. Grandstand Level");
        System.out.print("Enter the number corresponding to your choice: ");
        int levelChoice = scanner.nextInt();

        //Amount of Tickets

        System.out.print("How many tickets from level " + levelChoice + " would you like? ");
        int amount = scanner.nextInt();

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

        

        // Tester
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phonenum);
        System.out.println("Amount of tickets from " + level + " is: " + amount);

        scanner.close();

    }

    }
}
