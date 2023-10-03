import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;

public interface VotingInitInterf extends Remote{
    public VoteInterf getVotingMaterial(ClientInterf cl) throws RemoteException;
    public ArrayList<User> getUsers() throws RemoteException;
    public void changeVote(ClientInterf cl)throws RemoteException;
    public Map<Integer, String> getCandidatsList() throws RemoteException;
    public String ListerCandidats() throws RemoteException; // basically does the same as the method above only diff it returns a string
    public boolean authentify(ClientInterf cl) throws RemoteException;
    public Date getendTime() throws RemoteException ;
    public Date getStartTime() throws RemoteException ;
    public void CloseVote() throws RemoteException ;
    public boolean getVotingStatus() throws RemoteException;
}
