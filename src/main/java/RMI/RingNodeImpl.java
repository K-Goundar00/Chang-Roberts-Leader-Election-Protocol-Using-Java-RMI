package RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Server-side class that manages ring topology.
 * Handles registration and ring closure.
 */
public class RingNodeImpl extends UnicastRemoteObject implements RingNodeInterface {

    private final List<ClientCallbackInterface> clients = new ArrayList<>();

    protected RingNodeImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void registerClient(ClientCallbackInterface client) throws RemoteException {
        clients.add(client);
        System.out.println("Registered node: " + client.getNodeId());

        // Link previous client to the new one
        if (clients.size() > 1) {
            clients.get(clients.size() - 2).setNextNode(client);
        }

        // Close the ring if 3+ nodes exist
        if (clients.size() > 2) {
            clients.get(clients.size() - 1).setNextNode(clients.get(0));
        }
    }
}
