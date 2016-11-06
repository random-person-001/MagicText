package SourceryTextb1;

import java.io.*;
import java.net.Socket;

/**
 * Created by riley on 05-Nov-2016.
 */
public class NetworkClient {
    public ColoredTextMatrix beClient() throws ClassNotFoundException {
        String serverName = "192.168.0.250";
        int port = 8792;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + client.getLocalSocketAddress());
            InputStream inFromServer = client.getInputStream();
            ObjectInputStream in = new ObjectInputStream(inFromServer);

            //System.out.println("Server says " + in.read());
            ColoredTextMatrix matrix = (ColoredTextMatrix) in.readObject();
            client.close();
            return matrix;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
