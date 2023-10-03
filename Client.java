import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterf {
    public String numero;
    public String motDePasse;

    public Client(String n, String mdp) throws RemoteException {
        numero = n;
        motDePasse = mdp;
    }

    public Client() throws RemoteException {

    }

    public String getNumero() throws RemoteException {

        return numero;
    }

    public String getMDP() throws RemoteException {

        return motDePasse;
    }

    public static Map<Integer, Integer> retrieveVotes(Scanner scanner, VotingInitInterf Vi) throws RemoteException {
        System.out.println("\n############# The vote is Starting !!! ###############\n");
        System.out.println("here's the nominations you can choose from :");
        System.out.println(Vi.ListerCandidats());
        System.out.println("\n------------------------------------------------------\n");
        int cdVote;
        Map<Integer, Integer> cvotes = new HashMap<>();
        for (Map.Entry<Integer, String> entry : Vi.getCandidatsList().entrySet()) {
            Integer rk = entry.getKey();
            String nom = entry.getValue();
            System.out.print("please enter your vote(-1 to 2) for the candidat -> " + rk + " - " + nom + " : ");
            cdVote = scanner.nextInt();
            cvotes.put(rk, cdVote);
        }
        return cvotes;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner scanner = new Scanner(System.in);

        String remoteServerAddress = "127.0.0.1";
        int registryPort = 5002;

        Registry registry = LocateRegistry.getRegistry(remoteServerAddress, registryPort);

        VotingInitInterf Vi = (VotingInitInterf) registry.lookup("VotesInit");

        try {
            System.out.println("\t Welcome to Voting Program !! ");
            VoteInterf vt = null;
            ClientInterf cll;
            int ClientChoice = -1;
            boolean authentified = false;
            do {

                System.out.print("Please enter your student Number : ");
                String name = scanner.next();
                System.out.print("Please enter your Passeword : ");
                String mdp = scanner.next();

                cll = new Client(name, mdp);
                authentified = Vi.authentify(cll);

                if (!authentified) {
                    System.out.println("Wrong Credentials");
                    System.out.println("to quit enter 0 press or another number to re-enter your credentials : ");
                    ClientChoice = scanner.nextInt();
                }

            } while (authentified == false && ClientChoice != 0);

            vt = Vi.getVotingMaterial(cll);
            if (authentified && vt == null && Vi.getVotingStatus()) {
                System.out.println("to change your votes press 1 else press any number :");
                ClientChoice = scanner.nextInt();
                if (ClientChoice == 1) {
                    System.out.println("Deleting your previous Votes ...");
                    Vi.changeVote(cll);
                    System.out.println(Vi.ListerCandidats());
                    vt = Vi.getVotingMaterial(cll);
                }
            }
            if (authentified && vt != null) {
                Map<Integer, Integer> RetrievedVotes = retrieveVotes(scanner, Vi);
                vt.VoteForCandidat(RetrievedVotes, cll);
                System.out.println("\n------------------------------------------------------\n");
                System.out.println("actual results : \n");
                System.out.println(Vi.ListerCandidats());

            }
            if (!Vi.getVotingStatus()) {
                System.out.println("Vote has ended, Here is the results : ");
                System.out.println(Vi.ListerCandidats());
            }

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            System.out.println(e);
        }
        scanner.close();
        System.out.println("\nThank you for voting !!");

    }
}
