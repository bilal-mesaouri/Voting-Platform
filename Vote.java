import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.ArrayList;

public class Vote extends UnicastRemoteObject implements VoteInterf {
    private static ArrayList<User> Users;
    // contains the IDs of the Users
    private static Map<Candidat, Integer> Votes;

    private static boolean voteStatus =false ;

    // Contains the IDs of candidats and there sore
    public Vote(ArrayList<User> us, Map<Candidat, Integer> vt) throws RemoteException {
        Users = us;
        Votes = vt;
        voteStatus=true;
    }

    public Vote() throws RemoteException {
    }

    public String ListerCandidats() {
        return this.toString();
    }

    public void VoteForCandidat(Map<Integer, Integer> ClientVotes, ClientInterf cl) throws RemoteException {
        String num = cl.getNumero();
        User us = getUserFromUsers(num);
        if (us != null) {
            for (Map.Entry<Integer, Integer> entry : ClientVotes.entrySet()) {
                Integer rk = entry.getKey();
                int vt = entry.getValue();
                if (vt < -1 || vt > 2)
                    throw new RemoteException("vote invalide");

                for (Map.Entry<Candidat, Integer> entryVote : Votes.entrySet()) {
                    Candidat cd = entryVote.getKey();
                    int sommeDesVotes = entryVote.getValue();

                    if (cd.Rank == rk) {
                        Votes.put(cd, sommeDesVotes + vt);
                        us.avote = true;

                    }
                }
            }
            us.votes = ClientVotes;
        }
    }

    public User getUserFromUsers(String nm) {
        for (User us : Users) {
            if (us.numero.equals(nm)) {
                return us;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String txt = "";
        for (Map.Entry<Candidat, Integer> entry : Votes.entrySet()) {
            Candidat cd = entry.getKey();
            int sommeDesVotes = entry.getValue();
            txt += "Vote Number : " + cd.Rank + " Candidat : " + cd.NomComplet + " Speech : " + cd.Speech + " Votes : "
                    + Integer.toString(sommeDesVotes) + "\n";

        }
        return txt;
    }

    public void closeVote(){
            Vote.voteStatus=false;
    }

    public static ArrayList<User> getUsers() {
        return Users;
    }

    public static void setUsers(ArrayList<User> users) {
        Users = users;
    }

    // Getters and setters for Votes
    public static Map<Candidat, Integer> getVotes() {
        return Votes;
    }

    public static void setVotes(Map<Candidat, Integer> votes) {
        Votes = votes;
    }

    // Getters and setters for voteStatus
    public static boolean isVoteStatus() {
        return voteStatus;
    }

    public static void setVoteStatus(boolean status) {
        voteStatus = status;
    }


}
