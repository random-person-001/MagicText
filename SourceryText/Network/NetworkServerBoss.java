package SourceryText.Network;

import SourceryText.GameInstance;
import SourceryText.SlaveGameInstance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Accepts incoming connection requests from clients, and creates a NetworkServerWorker for handling each one.
 * Created by riley on 31-Jan-2017.
 */
public class NetworkServerBoss {
    private GameInstance masterInstance;
    private ServerSocket serverSocket;
    private int PORT = 8793;
    private boolean acceptingConnections = false;
    private ArrayList<NetworkServerWorker> workers = new ArrayList();

    public NetworkServerBoss(GameInstance master) {
        masterInstance = master;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(50 * 1000);
            System.out.println("[NetworkServerBoss] Started server socket");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[NetworkServerBoss] IO Exception starting server socket");
        }
    }

    public void openNetworking(){
        if (!acceptingConnections) {
            acceptingConnections = true;
            (new Thread(this::networkingLoop)).start();
        }
    }

    public void closeNetworking(){
        acceptingConnections = false;
    }

    private void networkingLoop(){
        while (acceptingConnections){
            try {
                System.out.println("[NetworkServerBoss] serverSocket is " + (serverSocket==null ? "null" : "nonnull"));
                System.out.println("[NetworkServerBoss] Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept(); // Note: code waits here until it accepts an incoming request
                System.out.println("[NetworkServerBoss] Just connected to " + server.getRemoteSocketAddress());

                // On receiving a player request, make a new SlaveGameInstance (a new player and make the GameInstance
                // control its movements between rooms, etc)
                System.out.println("[NetworkServerBoss] Adding another multiplayer player!  Welcome!");
                SlaveGameInstance instance = new SlaveGameInstance(masterInstance);
                instance.runGameAsSlave();

                // Should pass whatever the serverSocket.accept() returns to the server worker, for it to listen on
                // Network server worker should also run itself as a thread, allowing us to go on
                NetworkServerWorker nsw = new NetworkServerWorker(instance.getCorrespondingPlayer(), server);
                workers.add(nsw);
                new Thread(nsw::begin).start();
                System.out.println("[NetworkServerBoss] Accepted a client and passed on to worker.  Now waiting a bit");
                Thread.sleep(2000);
            } catch (SocketTimeoutException e){
                System.out.println("[NetworkServerBoss] Bro, server socket timed out.  We'll try again.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Interrupt");
            }
        }
    }

    /**
     * Close any connections we've accepted thus far.  Does not change the state of whether we are accepting more
     * connections.
     */
    public void bootOutClients(){
        for (NetworkServerWorker w : workers){
            w.disconnect();
        }
    }
}
