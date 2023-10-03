import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientInterf extends Remote {
    public String getNumero() throws RemoteException;

    public String getMDP() throws RemoteException;
}
