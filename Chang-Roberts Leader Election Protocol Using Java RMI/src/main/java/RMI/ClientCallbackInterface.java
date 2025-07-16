package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for callback methods used in the Chang-Roberts leader election protocol.
 * Each node must implement this to allow communication from neighboring nodes.
 */
public interface ClientCallbackInterface extends Remote {
    void receiveElectionMessage(int candidateId) throws RemoteException;
    void receiveLeaderAnnouncement(int leaderId) throws RemoteException;
    int getNodeId() throws RemoteException;
    void setNextNode(ClientCallbackInterface next) throws RemoteException;
}
