import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class VotingInit extends UnicastRemoteObject implements VotingInitInterf {

    private Date startTime = new Date();
    private Date endTime;
    private Vote VT;

    public VotingInit(ArrayList<User> us, Map<Candidat, Integer> vt, int days) throws RemoteException {
        VT = new Vote(us, vt);
        endTime = new Date(startTime.getTime() + daysToMilliseconds(days));
    }

    public User getUserFromUsers(String nm) {
        for (User us : VT.getUsers()) {
            if (us.numero.equals(nm)) {
                return us;
            }

        }
        return null;
    }

    public VoteInterf getVotingMaterial(ClientInterf cl) throws RemoteException {
        User mainUser = getUser(cl);
        if (mainUser == null || mainUser.avote == true || !VT.isVoteStatus()) {
            return null;
        } else {
            return VT;
        }

    }

    public User getUser(ClientInterf cl) throws RemoteException {
        String num = cl.getNumero();
        String mdp = cl.getMDP();
        User mainUser = null;
        for (User us : VT.getUsers()) {
            if (num.equals(us.numero) && mdp.equals(us.motDePasse)) {
                mainUser = us;
                break;
            }
        }
        return mainUser;
    }

    public boolean authentify(ClientInterf cl) throws RemoteException {
        if (getUser(cl) != null)
            return true;
        return false;

    }

    public ArrayList<User> getUsers() throws RemoteException {
        return VT.getUsers();
    }

    public void changeVote(ClientInterf cl) throws RemoteException {
        User us = getUserFromUsers(cl.getNumero());
        Map<Candidat, Integer> Vots = VT.getVotes();
        for (Map.Entry<Integer, Integer> entry : us.votes.entrySet()) {
            int rk = entry.getKey();
            int vt = entry.getValue();
            for (Map.Entry<Candidat, Integer> entryVote : Vots.entrySet()) {
                Candidat cd = entryVote.getKey();
                if (cd.Rank == rk) {
                    int sommeDesVotes = entryVote.getValue();
                    Vots.put(cd, sommeDesVotes - vt);
                    break;
                }
            }
        }
        VT.setVotes(Vots);
        us.avote = false;
    }

    public Map<Integer, String> getCandidatsList() throws RemoteException {
        Map<Integer, String> cds = new HashMap<Integer, String>();
        for (Map.Entry<Candidat, Integer> entry : VT.getVotes().entrySet()) {
            Candidat cd = entry.getKey();
            cds.put(cd.Rank, cd.NomComplet);
        }
        return cds;
    }

    public String ListerCandidats() throws RemoteException {
        return VT.ListerCandidats();
    }

    public long daysToMilliseconds(int days) {
        // 1 day = 24 hours = 24 * 60 minutes = 24 * 60 * 60 seconds = 24 * 60 * 60 *
        // 1000 milliseconds
        return (long) days* 60 * 1000;
    }

    public Date getStartTime()  throws RemoteException{
        return startTime;
    }

    public Date getendTime() throws RemoteException {
        return endTime;
    }

    public void CloseVote() throws RemoteException {
        VT.closeVote();
    }
    public boolean getVotingStatus() throws RemoteException{
        return VT.isVoteStatus();
    }
}
