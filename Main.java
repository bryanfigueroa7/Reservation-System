
import java.util.*;Kar
public class Main{
    public static void main(String[] args) {
        //Obtains info from client
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phonenum = scanner.nextLine();

        //Tester
        // System.out.println("Name: " + name);
        // System.out.println("Email: " + email);
        // System.out.println("Phone Number: " + phonenum);

        scanner.close();
    
    }
    
}

