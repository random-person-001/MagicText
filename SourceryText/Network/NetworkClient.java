package SourceryText.Network;

import SourceryText.GameSettings.KeyMap;
import SourceryText.Layer;
import SourceryText.SpecialText;
import SourceryText.Window;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Connects to a NetworkServer and places the ColoredTextMatrix it receives on its own window.  Uses TCP.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkClient {
    private static final boolean TEST = true;
    private int port = 8793;
    private Window w = null;
    private Socket server = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private MultiplayerKeyListener kl = new MultiplayerKeyListener();
    private UpdateTask updateTask = new UpdateTask("ST-Client-Layer-Receiver");
    private String ipAddress;
    private int fps = 35; // Max read fps
    private int keysSentSoFar = 0;
    Layer l;

    public void main(String serverName, KeyMap keymap) throws IOException {
        ipAddress = serverName;
        w = new Window();
        w.txtArea.addKeyListener(kl);
        if (connect(serverName)) {
            sendKeyMap(keymap);
            updateTask.start();
        } else {
            attemptCancel();
        }
    }

    private void sendKeyMap(KeyMap keymap) throws IOException {
        if (out != null) {
            System.out.println("Sending keymap over");
            out.writeObject(keymap);
        } else {
            System.out.println("Couldn't send keymap - out stream was null :(");
        }
    }

    private boolean connect(String serverName) throws IOException {
        System.out.println("Connecting to " + serverName + " on port " + port);
        try {
            server = new Socket(serverName, port);
            server.setSoTimeout(5 * 1000);
        } catch (SocketException | UnknownHostException e) {
            System.out.println("[NetworkClient] Could not connect Sourcerery Text server " + serverName + " port " +
                    port + "; therefore nullpointers will be thrown.  Are you sure the server is up and running?");
            server = null;
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Connected to " + server.getRemoteSocketAddress() + ", and now trying to actually talk");

        try {
            InputStream inFromServer = server.getInputStream();
            in = new ObjectInputStream(new BufferedInputStream(inFromServer));
            OutputStream outToServer = server.getOutputStream();
            out = new ObjectOutputStream(outToServer);

            System.out.println("Network connections look all good!");
            return true;
        } catch (SocketTimeoutException e) {
            System.out.println("Socket timed out.  Are you sure that the multiplayer server is *accepting* connections?");
            return false;
        }
    }

    /**
     * Try to cancel the timer and close the sockets, if possible
     */
    public void attemptCancel() {
        try {
            if (server != null) server.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.out.println("Cancelling the NetworkClient failed:");
            e.printStackTrace();
        } finally {
            //updateTask.cancel();
            server = null;
            in = null;
            out = null;
        }
        w.removeKeyListener(kl);
        w.dispose();
        JOptionPane.showMessageDialog(null, "Connection to Sourcery Text LAN game at " + ipAddress +
                " failed, was interrupted, or was terminated.\nMake sure you entered the correct IP and that " +
                "they are accepting connections.", "Connection failed", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Get the image and put it on the window
     *
     * @throws IOException            when things go wrong with the network
     * @throws ClassNotFoundException but really shouldn't
     */
    private void receiveImage() throws IOException, ClassNotFoundException {
            if (in == null) {
                System.out.println("Input stream is null; aborting reading ColoredTextMatrix attempt");
                return;
            }
            try {
                //System.out.println(in.available());
                if(!TEST) {
                    l = (Layer) in.readObject();
                }
                else {
                    Object obj = in.readObject();
                    if (obj.getClass()==Object[].class) {
                        long serverTime = (long) ((Object[])obj)[1];
                        l = (Layer) ((Object[])obj)[0];
                        long time = System.currentTimeMillis();
                        String deltaTime = Long.toString(time - serverTime);
                        for (int i = 0; i < deltaTime.length(); i++) {
                            l.setSpecTxt(14, 16+i, new SpecialText(deltaTime.substring(i, i + 1)));
                        }
                    }
                    else if(obj.getClass()==Long.class) {
                        long serverTime = (long) obj;
                        long time = System.currentTimeMillis();
                        String deltaTime = Long.toString(time - serverTime);
                        String[][] timeTxt = new String[28][46];
                        for (int i = 0; i < deltaTime.length(); i++) {
                            timeTxt[14][16 + i] = deltaTime.substring(i, i + 1);
                        }
                        l = new Layer(timeTxt);
                    }
                    else {
                        l = (Layer) obj;
                    }
                }
            } catch (EOFException e) {
                System.out.println("Input stream has ended :(");
                attemptCancel();
                return;
            }
            if (l != null) {
                System.out.println("Received image!");
                w.build(l);
            } else {
                System.out.println("no layer received over network");
            }
//                new Thread(() -> {
//                    if (l != null) {
//                        System.out.println("Received image!");
//                        w.build(l);
//                    } else {
//                        System.out.println("no layer received over network");
//                    }
//                }).start();
    }

    /**
     * Send a KeyEvent to the server of the game.
     *
     * @param event a KeyEvent that occurred on this window
     */
    private void sendKey(KeyEvent event) throws IOException {
        if (out != null) {
            System.out.println("You pressed a key.  I'll try to send it over.");
            out.writeObject(event);
            keysSentSoFar++;
        } else {
            System.out.println("Bro, I would send this juicy key event (" + event.toString() + ") to Kathmandu or wherever" +
                    " my fellow Sourcery Text server is, but the output stream is just really null right now.");
        }
        if (keysSentSoFar > 30) {
            out.reset();
            keysSentSoFar = 0;
        }
    }

    /**
     * Send keypresses to the Client
     */
    private class MultiplayerKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            try {
                sendKey(event);
            } catch (SocketException e) {
                System.out.println("The other side probably disconnected (SocketException).");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void keyReleased(KeyEvent event) {
            keyPressed(event); //Does same thing anyway
        }
    }

    private class UpdateTask extends Thread {
        public UpdateTask(String myName) {
            super(myName);
        }

        @Override
        public void run() {
            try {
                while (server != null) {
                    receiveImage();
                }
            } catch (SocketException e) {
                System.out.println("The other side probably disconnected (SocketException).");
                attemptCancel();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                attemptCancel();}
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
