import java.rmi.*;

public interface RemoteInterface extends java.rmi.Remote {

public void deposit (Beacon b) throws RemoteException;
public String execute(String CmdID) throws Exception;

}
