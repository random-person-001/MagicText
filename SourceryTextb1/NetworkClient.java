package SourceryTextb1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Connects to a NetworkerServer and places the ColoredTextMatrix it recives on its own window.  Uses TCP.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkClient {
    private int port = 8792;
    private Window w = null;
    private Socket client = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private MultiplayerKeyListener kl = new MultiplayerKeyListener();
    private UpdateTask updateTask = new UpdateTask();

    public void main(String serverName) throws IOException {
        w = new Window();
        w.addKeyListener(kl);
        connect(serverName);
        new Timer().scheduleAtFixedRate(updateTask, 4, 200);
    }

    private void connect(String serverName) throws IOException {
        System.out.println("Connecting to " + serverName + " on port " + port);
        try {
            client = new Socket(serverName, port);
        }
        catch (ConnectException e){
            System.out.println("Could not connect Sourcerery Text server "+serverName+" port "+port+"; therefore " +
                    "nullpointers will be thrown.  Are you sure the server is up and running?" );
            client = null;
            return;
        }

        System.out.println("Just connected to " + client.getRemoteSocketAddress());
        OutputStream outToServer = client.getOutputStream();
        out = new ObjectOutputStream(outToServer);

        InputStream inFromServer = client.getInputStream();
        in = new ObjectInputStream(inFromServer);
        System.out.println("Network connections look all good!");
    }

    /**
     * Try to cancel the timer and close the sockets, if possible
     */
    public void attemptCancel() {
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Cancelling the NetworkClient failed:");
            e.printStackTrace();
        } finally {
            updateTask.cancel();
        }
    }

    /**
     * Get the image and put it on the window
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void receiveImage() throws IOException, ClassNotFoundException {
        if (in == null){
            System.out.println("Input stream is null; aborting reading ColoredTextMatrix attempt");
            return;
        }
        w.build((Layer) in.readObject());
        System.out.println("Received image!");
    }

    /**
     * Send a KeyEvent to the server of the game.
     * @param event a KeyEvent that occurred on this window
     */
    private void sendKey(KeyEvent event) throws IOException {
        if (out != null){
            out.writeObject(event);
        }else {
            System.out.println("Bro, I would send this juicy key event ("+event.toString()+") to Kathmandu or wherever" +
                    " my fellow Sourcery Text server is, but the output stream is just really null right now.");
        }
    }

    /**
     * Send keypresses to the Client
     */
    private class MultiplayerKeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent event) {
            try {
                sendKey(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void keyReleased(KeyEvent event) {
            try {
                sendKey(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            try {
                receiveImage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
