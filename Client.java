public class Client {
    private String name;
    private String email;
    private String phoneNumber;

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

    @Override
    public String toString() {
        return "Client Details:\n" +
               "Name: " + this.name + "\n" +
               "Email: " + this.email + "\n" +
               "Phone: " + this.phoneNumber;
    }
}
