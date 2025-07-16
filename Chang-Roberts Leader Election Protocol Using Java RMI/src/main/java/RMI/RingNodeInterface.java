package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for the server allowing clients to register.
 */
public interface RingNodeInterface extends Remote {
    void registerClient(ClientCallbackInterface client) throws RemoteException;
}
