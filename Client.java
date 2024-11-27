/**
 * Client class this class is used to create and object with their name, email and phone number
 */
public class Client {
    private String name;
    private String email;
    private String phoneNumber;
    /**
     * Construct a new Client with the specified name, email and phone number
     * 
     * @param name          Name of the client
     * @param email         Email of the client
     * @param phoneNumber   Phone number of the client
     */

    // Constructor
    public Client(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Returns a string representation of the client details.
     *
     * @return a string containing the name, email, and phone number of the client
     */
    @Override
    public String toString() {
        return "Client Details:\n" +
               "Name: " + this.name + "\n" +
               "Email: " + this.email + "\n" +
               "Phone: " + this.phoneNumber;
    }
}