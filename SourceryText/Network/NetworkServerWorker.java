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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Receives connections from a NetworkClient and sends them ColoredTextMatrices periodically.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkServerWorker extends Thread {
    private Updater updaterTask = new Updater();
    private Socket server = new Socket();
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Player player; //Client player

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
            in = new ObjectInputStream(server.getInputStream());
            out = new ObjectOutputStream(server.getOutputStream());
            new Timer().scheduleAtFixedRate(updaterTask, 4, 50);
            new Thread(this::keyReadLoop).start();
            System.out.println("[NetworkServerWorker] Finished execution of begin()");
        } catch (IOException e){
            System.out.println("I/O Exception in the server worker.begin() method");
            e.printStackTrace();
        }
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
     * Start doing a playerLoop that reads the mey input from the client.
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
     * Receive keys that were pressed on a window far away and sent over the network, and tell the Player about them.
     *
     * @throws IOException in case reading from the input stream goes wrong
     * @throws ClassNotFoundException if it problems occur casting the input to KeyEvent or KeyMap
     */
    private void readKeys() throws IOException, ClassNotFoundException {
        Object o = in.readObject();  //Execution should hang 'in limbo' until some input comes through;
        if (o != null && o.getClass() == KeyEvent.class) {
            if (!player.hasKeyMap()){ // This rarely happens.  But, you know...
                System.out.println("Player hasn't received the keymap yet");
                return;
            }
            KeyEvent e = (KeyEvent)o;
            System.out.println("Client pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
            player.fireKeyEvent(e);
        }
        else if (o != null && o.getClass() == KeyMap.class) {
            KeyMap m = (KeyMap) o;
            System.out.println("Client sent their keymap!  Thanks!");
            player.setKeyMap(m);
        }
    }

    private class Updater extends TimerTask {
        @Override
        public void run() {
            Layer fullImage = player.org.topDownBuild(player);
            try {
                sendImage(fullImage);
            } catch (SocketException e) {
                System.out.println("The other side probably disconnected (SocketException).");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
