import java.util.*;

public class Stadium {
    private Set<Sits> seatsAvailable;
    private Map<Client, Sits> reservation;
    private Queue<Client> waitList;
    //Added
    //private LinkedList<> registerOfTransaction;
    //private Stack<> cancellations; 

    public Stadium (){
        seatsAvailable = new HashSet<>();
        reservation = new HashMap<>();
        waitList = new LinkedList();
    }

    public void reserveSit(Client client, Sits sits){
        if(seatsAvailable.contains(sits)){
            seatsAvailable.remove(sits);
            reservation.put(client, sits);
            System.out.println("Reserve complited succesfully.");
        }
        else {
            waitList.add(client);
        }

    }
    public void cancelReservation(Client client){
        Sits sit = reservation.remove(client);
        if(sit != null){
            seatsAvailable.add(sit);

        
            if(!waitList.isEmpty()){
                Client nextClient = waitList.poll();
                reserveSit(nextClient, sit);
            }
            System.out.println(client.getName()+ "reservation got cancelled succesfuly");

        }
        else{
            System.out.println("There is no reservation by " + client.getName());

        }

    }
    public void Disponibility(){
        System.out.println("Available sits: " + seatsAvailable.size());
    }

    

    // Create Levels
    // Create different prices for each level
    // Create different capacities


}
