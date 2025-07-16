package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Bootstraps the RMI registry and registers the RingNodeImpl server.
 */
public class RingNodeServer {
    public static void main(String[] args) {
        try {
            RingNodeImpl ring = new RingNodeImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RingServer", ring);
            System.out.println("RingNodeServer is up and running.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
