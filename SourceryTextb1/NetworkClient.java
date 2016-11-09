package SourceryTextb1;

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
    int port = 8792;
    Window w;
    Socket client;
    ObjectInputStream in;
    DataOutputStream out;

    public void beTimerClient(String serverName) throws IOException {
        connect(serverName);
        new Timer().scheduleAtFixedRate(new TimerTask(){
            public void run() {
                try {
                    receiveImage();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, 4, 200);

    }

    private void connect(String serverName) throws IOException {
        w = new Window();


        System.out.println("Connecting to " + serverName + " on port " + port);
        try {
            client = new Socket(serverName, port);
        }
        catch (ConnectException e){
            System.out.println("Could not connect Sourcerery Text server "+serverName+" port "+port+"; therefore " +
                    "nullpointers will be thrown.  Are you sure the server is up and running?" );
            return;
        }


        System.out.println("Just connected to " + client.getRemoteSocketAddress());
        OutputStream outToServer = client.getOutputStream();
        out = new DataOutputStream(outToServer);

        out.writeUTF("Hello from " + client.getLocalSocketAddress());
        InputStream inFromServer = client.getInputStream();
        in = new ObjectInputStream(inFromServer);
    }

    private void receiveImage() throws IOException, ClassNotFoundException {
        if (in == null){
            System.out.println("Input stream is null; aborting reading ColoredTextMatrix attempt");
            return;
        }
        w.build((Layer) in.readObject());
        System.out.println("Received image!");
    }
}
