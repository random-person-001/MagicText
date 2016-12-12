package SourceryText;

import SourceryText.GameObjects.Player;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Receives connections from a NetworkClient and sends them ColoredTextMatrices periodically.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkServer {
    private int PORT = 8793;
    private ServerSocket serverSocket;
    private Updater updaterTask = new Updater();
    private InputReader inputReceiver = new InputReader();
    private Socket server = new Socket();
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Player player; //Client player

    /**
     * @param playery the Player who the display should be centered on
     * @throws IOException
     */
    public NetworkServer(Player playery) throws IOException {
        player = playery;
        serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(60 * 1000);
    }

    /**
     * Connect to a client and start threads to periodically send them display data
     * The method waits until a client makes a connection, and then returns after
     * starting some threads to take care of IO business
     *
     * @throws IOException when it's feeling down
     */
    public void doTimerSend() throws IOException {
        connect();
        new Timer().scheduleAtFixedRate(updaterTask, 4, 50);
        new Thread(this::keyReadLoop).start();
    }

    /**
     * Connect to a client who asks nicely.
     * More specifically, accept a connection.  Note that the method will hang until either a client
     * makes a request, or until it times out after a long time.
     *
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
     *
     * @throws IOException when it gets only lumps of coal in its stocking
     */
    public void disconnect() throws IOException {
        updaterTask.cancel();
        server.close();
    }

    /**
     * Send an image *once* through the (assumed working) socket
     *
     * @param fullImage the Layer that should be sent
     * @throws IOException on the rare occasion that its parents deny it a shiny new laptop for Christmas that it really
     *                     really really really wanted.
     */
    private void sendImage(Layer fullImage) throws IOException {
        out.writeObject(fullImage);
    }

    /**
     * Start doing a loop that reads the mey input from the client.
     */
    private void keyReadLoop() {
        try {
            while (true) {
                readKeys();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recieve keys that were pressed on a window far away and sent over the network, and tell the Player about them.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readKeys() throws IOException, ClassNotFoundException {
        KeyEvent e = (KeyEvent) in.readObject(); //Execution should hang 'in limbo' until some input comes through
        if (e != null) {
            System.out.println("Client pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
            player.fireKeyEvent(e);
        }
    }

    private class Updater extends TimerTask {
        @Override
        public void run() {
            Layer fullImage = player.org.topDownBuild(player);
            try {
                //out.flush();
                sendImage(fullImage);
            } catch (SocketException e) {
                System.out.println("The other side probably disconnected (SocketException).");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class InputReader extends TimerTask {

        public void run() {
            try {
                readKeys();
            } catch (SocketException e) {
                System.out.println("The other side probably disconnected (SocketException).");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
