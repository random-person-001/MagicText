package SourceryTextb1;

import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Receives connections fram a NetworkClient and sends them ColoredTextMatrix.
 * Created by riley on 05-Nov-2016.
 */
public class NetworkerServer {
    private int PORT = 8792;
    private ServerSocket serverSocket;
    Socket server = new Socket();
    ObjectOutputStream out;
    DataInputStream in;
    Window window;

    public NetworkerServer(Window windowFrom) throws IOException {
        serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(10000);
        window = windowFrom;
    }

    public void doTimerSend(){
        try {
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    sendImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 4, 200);
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

    private void sendImage() throws IOException {
        out.writeObject(window.txtArea);
    }
}
