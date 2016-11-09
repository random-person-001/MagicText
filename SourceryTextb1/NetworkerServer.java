package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;

import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/** Receives connections from a NetworkClient and sends them ColoredTextMatrices periodically.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkerServer {
    private int PORT = 8792;
    private ServerSocket serverSocket;
    private Updater updaterTask = new Updater();
    Socket server = new Socket();
    ObjectOutputStream out;
    DataInputStream in;

    public NetworkerServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(10000);
    }

    /**
     * Find a client and start a task to periodically send them display data
     * @param playery the Player who the display should be centered on
     * @throws IOException when it's feeling down
     */
    public void doTimerSend(Player playery) throws IOException {
        connect();
        updaterTask.playery = playery;
        new Timer().scheduleAtFixedRate(updaterTask, 4, 200);
    }

    private void connect() throws IOException {
        System.out.println("Waiting for client on port " +
                serverSocket.getLocalPort() + "...");
        server = serverSocket.accept();

        System.out.println("Just connected to " + server.getRemoteSocketAddress());
        in = new DataInputStream(server.getInputStream());

        System.out.println(in.readUTF());
        out = new ObjectOutputStream(server.getOutputStream());
    }

    /**
     * Disconnect socket and stop the timer
     * @throws IOException when it gets only lumps of coal in its stocking
     */
    public void disconnect() throws IOException {
        updaterTask.cancel();
        server.close();
    }

    /**
     * Send an image *once* through the (assumed working) socket
     * @param fullImage the Layer that should be sent
     * @throws IOException on the rare occasion that its parents deny it a shiny new laptop for Christmas that it really
     * really really really wanted.
     */
    private void sendImage(Layer fullImage) throws IOException {
        out.writeObject(fullImage);
    }

    private class Updater extends TimerTask {
        Player playery;
        @Override
        public void run() {
            Layer fullImage = playery.orgo.topDownBuild(playery.getX()-22, playery.getY()-11, playery.getUsername());
            try {
                sendImage(fullImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
