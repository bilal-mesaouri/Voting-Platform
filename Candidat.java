
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Candidat extends UnicastRemoteObject {
    private static int id = 0;
    public String NomComplet;
    public int Rank;
    public String Speech;

    public Candidat(String Nc, String sp) throws RemoteException {
        NomComplet = Nc ;
        Speech = sp;
        Rank = id++;
    }

}
