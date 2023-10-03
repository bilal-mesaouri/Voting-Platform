import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface VoteInterf extends Remote {
    public void VoteForCandidat(Map<Integer, Integer> ClientVotes,ClientInterf cl) throws RemoteException ;
    
}
