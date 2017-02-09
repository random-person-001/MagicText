package SourceryText.Network;

import SourceryText.GameObjects.Player;
import SourceryText.GameSettings.KeyMap;
import SourceryText.Layer;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Receives connections from a NetworkClient and sends them ColoredTextMatrices periodically.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkServerWorker extends Thread {
    private static final boolean TEST = true;
    private Thread updaterTask = new Updater("ST-Server-Layer-Sender");
    private Socket server = new Socket();
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Player player; //Client player
    private int fps = 30; // max write (display) fps
    private int sentCount = 0;
    private boolean disconnected = false;

    /**
     * @param playery the Player who the display should be centered on
     * @throws IOException
     */
    NetworkServerWorker(Player playery, Socket socket) throws IOException {
        player = playery;
        System.out.println("[NetworkServerWorker] Player is " + ((player == null) ? "null" : "nonnull"));
        this.server = socket;
    }

    /**
     * Start threads to periodically send the client display data
     * The method returns after starting some threads to take care of IO business
     */
    public void begin() {
        try {
            System.out.println("[NetworkServerWorker] point 1");
            out = new ObjectOutputStream(server.getOutputStream());
            in = new ObjectInputStream(server.getInputStream());
            updaterTask.start();
            new Thread(this::keyReadLoop).start(); // we should cancel this, too
            System.out.println("[NetworkServerWorker] Finished execution of begin()");
        } catch (IOException e) {
            System.out.println("I/O Exception in the server worker.begin() method");
            e.printStackTrace();
            disconnect();
        }
    }

    /**
     * Disconnect socket and stop the timer
     */
    public void disconnect() {
        if (!disconnected) {
            disconnected = true;
            if (server != null && !server.isClosed()) {
                try {
                    if (server != null) server.close();
                    if (out != null) out.close();
                    if (in != null) in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            server = null;
            out = null;
            in = null;
            player.room.splashMessage("Player '" + player.getUsername() + "' disconnected \n" +
                    " from the game", "* GAME *", player.room.playo);
            player.braindead = true;
        }
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
        if (sentCount > 10) { // Memory leak stuff
            sentCount = 0;
            out.reset();
        }
    }

    private void sendImage(long time) throws IOException {
        out.writeObject(time);
        if (sentCount > 10) { // Memory leak stuff
            sentCount = 0;
            out.reset();
        }
    }

    private void sendImage(Layer image, long time) throws IOException {
        out.writeObject(new Object[]{image, time});
        if (sentCount > 10) { // Memory leak stuff
            sentCount = 0;
            out.reset();
        }
    }

    /**
     * Start doing a playerLoop that reads the mey input from the client.
     */
    private void keyReadLoop() {
        try {
            while (server != null) {
                readKeys();
            }
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("[NetworkServerWorker] Something went wrong. (IOException or ClassNotFoundException).  Aborting this whole connection");
            disconnect();
        }
    }

    /**
     * Receive keys that were pressed on a window far away and sent over the network, and tell the Player about them.
     *
     * @throws IOException            in case reading from the input stream goes wrong
     * @throws ClassNotFoundException if it problems occur casting the input to KeyEvent or KeyMap
     */
    private void readKeys() throws IOException, ClassNotFoundException {
        Object o = in.readObject();  //Execution should hang 'in limbo' until some input comes through;
        if (o != null && o.getClass() == KeyEvent.class) {
            if (!player.hasKeyMap()) { // This rarely happens.  But, you know...
                System.out.println("Server hasn't received " + player.getUsername() + "'s keymap yet, but received a" +
                        " keystroke from them over network.  Ignoring keystroke.");
                return;
            }
            KeyEvent e = (KeyEvent) o;
            System.out.println("Client pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
            player.fireKeyEvent(e);
            if (e.getKeyCode() == KeyEvent.VK_F12) {
                player.rickroll();
            }
        } else if (o != null && o.getClass() == KeyMap.class) {
            KeyMap m = (KeyMap) o;
            System.out.println("Client sent their keymap!  Thanks!");
            player.setKeyMap(m);
        }
    }

    private class Updater extends Thread {
        public Updater(String s) {
            super(s);
        }

        @Override
        public void run() {
            try {
                if(!TEST) {
                    while (server != null) {
                        Layer fullImage = player.org.topDownBuild(player);
                        sendImage(fullImage);
                        Thread.sleep(1000 / fps);
                    }
                }
                else {
                    while (server != null) {
                        long time = System.currentTimeMillis();
                        Layer fullImage = player.org.topDownBuild(player);
                        sendImage(fullImage, time);
                        Thread.sleep(1000 / fps);
                    }
                }
            } catch (SocketException e) {
                System.out.println("[NetworkServerWorker] The other side probably disconnected (SocketException).  Aborting whole connection");
                disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[NetworkServerWorker] Something went wrong. (IOException).  Aborting this whole connection");
                disconnect();
            } catch (InterruptedException | NullPointerException e) {
                e.printStackTrace();
                disconnect();
            }
        }
    }
}
