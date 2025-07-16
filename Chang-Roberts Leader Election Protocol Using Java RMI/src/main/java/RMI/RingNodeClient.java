package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Represents a client node in the ring topology.
 * Each client is capable of sending and receiving election messages.
 */
public class RingNodeClient extends UnicastRemoteObject implements ClientCallbackInterface {

    private final int nodeId;                            // Unique ID for the node
    private ClientCallbackInterface nextNode;           // Reference to next node in the ring
    private boolean electionStarted = false;
    private boolean hasForwardedOwnId = false;
    private boolean leaderKnown = false;

    protected RingNodeClient(int nodeId) throws RemoteException {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public void setNextNode(ClientCallbackInterface next) {
        this.nextNode = next;
        try {
            System.out.println("Node " + nodeId + " set next to Node " + next.getNodeId());
        } catch (Exception ignored) {}
    }

    @Override
    public void receiveElectionMessage(int candidateId) throws RemoteException {
        if (candidateId == nodeId) {
            if (hasForwardedOwnId) {
                // If ID returns to originator, that node is elected leader
                System.out.println("Node " + nodeId + " is elected as LEADER.");
                announceLeader(nodeId);
            } else {
                hasForwardedOwnId = true;
                nextNode.receiveElectionMessage(candidateId);
            }
        } else if (candidateId > nodeId) {
            hasForwardedOwnId = true;
            nextNode.receiveElectionMessage(candidateId); // Forward higher ID
        } else {
            // Replace with own ID and forward if it is higher
            hasForwardedOwnId = true;
            nextNode.receiveElectionMessage(nodeId);
        }
    }

    @Override
    public void receiveLeaderAnnouncement(int leaderId) throws RemoteException {
        if (!leaderKnown) {
            System.out.println("Node " + nodeId + " acknowledges Leader: " + leaderId);
            leaderKnown = true;
            nextNode.receiveLeaderAnnouncement(leaderId);
        }
    }

    private void announceLeader(int leaderId) throws RemoteException {
        nextNode.receiveLeaderAnnouncement(leaderId);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter node ID: ");
            int id = scanner.nextInt();

            RingNodeClient client = new RingNodeClient(id);
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            RingNodeInterface server = (RingNodeInterface) registry.lookup("RingServer");
            registry.rebind("Node" + id, client); // Bind this client into registry
            server.registerClient(client);        // Register to the ring

            System.out.println("Node " + id + " joined the ring.");
            System.out.println("Type 'start' to begin election:");

            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("start")) {
                    client.hasForwardedOwnId = false;
                    client.electionStarted = true;
                    client.receiveElectionMessage(client.nodeId);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
