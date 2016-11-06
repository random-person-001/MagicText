package SourceryTextb1;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Connects to a NetworkerServer and places the ColoredTextMatrix it recives on its own window.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkClient {
    String serverName = "192.168.0.250"; // This should probably be a parameter.
    int port = 8792;
    Window w;
    Socket client;
    ObjectInputStream in;
    DataOutputStream out;

    public void beTimerClient() throws IOException {
        connect();
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                try {
                    receiveImage();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, 4, 200);

    }

    private void connect() throws IOException {
        w = new Window();

        System.out.println("Connecting to " + serverName + " on port " + port);
        client = new Socket(serverName, port);

        System.out.println("Just connected to " + client.getRemoteSocketAddress());
        OutputStream outToServer = client.getOutputStream();
        out = new DataOutputStream(outToServer);

        out.writeUTF("Hello from " + client.getLocalSocketAddress());
        InputStream inFromServer = client.getInputStream();
        in = new ObjectInputStream(inFromServer);
    }

    private void receiveImage() throws IOException, ClassNotFoundException {
        w.txtArea = (ColoredTextMatrix) in.readObject();
        w.add(w.txtArea);
        System.out.println("Received image");
        w.txtArea.repaint();
    }
}
