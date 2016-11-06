package SourceryTextb1;

import java.net.*;
import java.io.*;

public class NetworkerServer {
    private int PORT = 8792;
    private ServerSocket serverSocket;

    public NetworkerServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(10000);
    }

    public void test(ColoredTextMatrix matrixToSend) {
        while(true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());

                System.out.println(in.readUTF());
                ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
                out.writeObject(matrixToSend);
                server.close();

            }catch(SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
