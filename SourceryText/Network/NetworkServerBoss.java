package SourceryText.Network;

import SourceryText.GameInstance;
import SourceryText.SlaveGameInstance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Accepts incoming connection requests from clients, and creates a NetworkServerWorker for handling each one.
 * Created by riley on 31-Jan-2017.
 */
public class NetworkServerBoss extends Thread{
    private GameInstance masterInstance;
    private ServerSocket serverSocket;
    private int PORT = 8793;

    public NetworkServerBoss(GameInstance master) {
        masterInstance = master;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(60 * 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            try {
                System.out.println("[NetworkServerBoss] serverSocket is " + (serverSocket==null ? "null" : "nonnull"));
                System.out.println("[NetworkServerBoss] Waiting for client on port " + serverSocket.getLocalPort() + "...");
                System.out.println(serverSocket.toString());
                Socket server = serverSocket.accept(); // Note: code waits here until it accepts an incoming request
                System.out.println("[NetworkServerBoss] Just connected to " + server.getRemoteSocketAddress());

                // On receiving a player request, make a new SlaveGameInstance (a new player and make the GameInstance
                // control its movements between rooms, etc)
                System.out.println("[NetworkServerBoss] Adding another multiplayer player!  Welcome!");
                SlaveGameInstance instance = new SlaveGameInstance(masterInstance);
                instance.runGameAsSlave();

                // Should pass whatever the serverSocket.accept() returns to the server worker, for it to listen on
                // Network server worker should also run itself as a thread, allowing us to go on
                NetworkServerWorker nsb = new NetworkServerWorker(instance.getCorrespondingPlayer(), server);
                new Thread(nsb::begin).start();
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
}
