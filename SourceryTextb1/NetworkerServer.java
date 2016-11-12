package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;

import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/** Receives connections from a NetworkClient and sends them ColoredTextMatrices periodically.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkerServer {
    private int PORT = 8793;
    private ServerSocket serverSocket;
    private Updater updaterTask = new Updater();
    private InputReader inputReceiver = new InputReader();
    private Socket server = new Socket();
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Player player;

    /**
     * @param playery the Player who the display should be centered on
     * @throws IOException
     */
    public NetworkerServer(Player playery) throws IOException {
        player = playery;
        serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(60 * 1000);
    }

    /**
     * Find a client and start a task to periodically send them display data
     * @throws IOException when it's feeling down
     */
    public void doTimerSend() throws IOException {
        connect();
        new Timer().scheduleAtFixedRate(updaterTask, 4, 50);
        new Timer().scheduleAtFixedRate(inputReceiver, 8, 50);
    }

    /**
     * Connect to a client who asks nicely
     * @throws IOException
     */
    private void connect() throws IOException {
        System.out.println("Waiting for client on port " +
                serverSocket.getLocalPort() + "...");
        server = serverSocket.accept(); // Note: code waits here until it accepts an incoming request

        System.out.println("Just connected to " + server.getRemoteSocketAddress());
        in = new ObjectInputStream(server.getInputStream());
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
        System.out.println("sent image");
    }

    /**
     * Recieve keys that were pressed on a window far away and sent over the network, and tell the Player about them.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readKeys() throws IOException, ClassNotFoundException {
        KeyEvent e = (KeyEvent) in.readObject();
        if (e != null){
            System.out.println("Client pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
            player.fireKeyEvent(e);
        }
    }

    private class Updater extends TimerTask {
        //Player playery;
        @Override
        public void run() {
            Layer fullImage = player.orgo.topDownBuild(player.getX()-22, player.getY()-11, player.getUsername());
            try {
                out.flush();
                sendImage(fullImage);
            } catch (SocketException e) {
                System.out.println("The other side probably disconnected (SocketException).");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class InputReader extends TimerTask {

        public void run(){
            try {
                readKeys();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
