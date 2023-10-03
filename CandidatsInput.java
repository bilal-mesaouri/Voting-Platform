import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class CandidatsInput {
    private static final String CANDIDATE_FILE_PATH = "candidates.ser";
    private static final String USER_FILE_PATH = "users.ser";

    public static void main(String[] args) {
        try {
            Map<Candidat, Integer> Votes = new HashMap<Candidat, Integer>();
            Scanner scanner = new Scanner(System.in);

            // Code to populate candidates (you can use Scanner for user input)

            System.out.println("enter the number of the candidats : ");
            int Nbr_cds = scanner.nextInt();
            for (int i = 0; i < Nbr_cds; i++) {

                System.out.println("enter Nombre Complet : ");
                String nc = scanner.next();
                System.out.println("enter speech : ");
                String sp = scanner.next();
                Votes.put(new Candidat(nc, sp), 0);
            }

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CANDIDATE_FILE_PATH));
            out.writeObject(Votes);
            System.out.println("Candidates serialized and saved to " + CANDIDATE_FILE_PATH);

            ArrayList<User> Users = new ArrayList<User>();
            System.out.println("enter the number of the Users : ");
            int Nbr_usrs = scanner.nextInt();
            for (int i = 0; i < Nbr_usrs; i++) {

                System.out.println("enter numero : ");
                String num = scanner.next();
                System.out.println("enter mot de passe : ");
                String mdp = scanner.next();
                Users.add(new User(num, mdp));
            }
            out = new ObjectOutputStream(new FileOutputStream(USER_FILE_PATH));
            out.writeObject(Users);
            System.out.println("Users serialized and saved to " + USER_FILE_PATH);
            scanner.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}