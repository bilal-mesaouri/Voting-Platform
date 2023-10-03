
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.util.*;
public class Server {

    /**
     * @param args the command line arguments
     */
    private static final String CANDIDATE_FILE_PATH = "candidates.ser";
    private static final String USER_FILE_PATH = "users.ser";

    public static void main(String[] args) throws RemoteException {
        try {
           
            Scanner scanner = new Scanner(System.in);
            System.out.println("type Start to launch the vote : ");
            String userInput = scanner.nextLine();
            if ("start".equalsIgnoreCase(userInput)) {
            Map<Candidat, Integer> Votes = deserializeCandidates();
            System.out.println("Candidates loaded from " + CANDIDATE_FILE_PATH);
            
        
            ArrayList<User> Users = deserializeUsers();
            System.out.println("Candidates loaded from " + CANDIDATE_FILE_PATH);

            // put minutes herre not seconds for testing purposes
            VotingInitInterf mi = new VotingInit(Users, Votes,1);
            
            Registry rg = LocateRegistry.createRegistry(5002);
            
            rg.bind("VotesInit", mi);
            
            Date startTime = new Date();
            System.out.println(startTime.toString());
            Date endTime = mi.getendTime();
            while (new Date().before(endTime)) {
                ArrayList<Long> remTime = getRaminingTime (endTime);
                System.out.println("Time remaining: " + remTime.get(0) + " days, " + remTime.get(1) + " hours, " + remTime.get(2) + " minutes");
                Thread.sleep(60*1000); // Sleep for 1 second before checking again
            }
            if(!new Date().before(endTime)){
                System.out.println("Vote Closed");
                mi.CloseVote();
                mi.ListerCandidats();
            }
                // Print the thread name
                
            } 
            scanner.close();
        }
            catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }

    }

    public static ArrayList<Long> getRaminingTime(Date enDate) {
        long remainingTime = enDate.getTime() - new Date().getTime();
        long remainingDays = remainingTime / (24 * 60 * 60 * 1000); // 1 day = 24 hours = 24 * 60 minutes = 24 * 60 * 60
                                                                    // seconds = 24 * 60 * 60 * 1000 milliseconds
        long remainingHours = (remainingTime % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        long remainingMinutes = ((remainingTime % (24 * 60 * 60 * 1000)) % (60 * 60 * 1000)) / (60 * 1000);
        ArrayList<Long> arr = new ArrayList<>();
        arr.add(remainingDays);
        arr.add(remainingHours);
        arr.add(remainingMinutes);
        return arr;
    }

    public static long daysToMilliseconds(int days) {
        // 1 day = 24 hours = 24 * 60 minutes = 24 * 60 * 60 seconds = 24 * 60 * 60 *
        // 1000 milliseconds
        return (long) days * 24 * 60 * 60 * 1000;
    }
    private static Map<Candidat, Integer> deserializeCandidates(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CANDIDATE_FILE_PATH))) {
            return (Map<Candidat, Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>(); // Return an empty map if deserialization fails
        }
    }

    private static ArrayList<User> deserializeUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USER_FILE_PATH))) {
            return (ArrayList<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list if deserialization fails
        }
    }

}
