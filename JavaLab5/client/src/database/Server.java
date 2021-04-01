package database;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.Set;
import java.util.Iterator;

import protocol.Request;
import protocol.Response;

public class Server {
    private SocketChannel client;
    private String address;
    private int port;

    public void stop() throws IOException { client.close(); }

    public Server(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        
        client = SocketChannel.open(new InetSocketAddress(address, port));
        client.configureBlocking(true);
    }

    public Response sendMessage(Request msg) {
        if (!client.isOpen()) {
            System.out.println("Connecting to the server...");
            
            try {
                client = SocketChannel.open(new InetSocketAddress(address, port));
                client.configureBlocking(true);
            } catch (Exception e) {
                System.out.println("Cannot connect to the server.");
                return null;
            }
        }
        
        Response response = null;

        try {
            Socket socket = client.socket();
            
            ObjectOutput objectOutput = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            objectOutput.writeObject(msg);
            objectOutput.flush();
            
            try {
                ObjectInput objectInput = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Object obj = objectInput.readObject();
                
                if (obj instanceof Response)
                    response = (Response)obj;
            } catch (Exception e) {
                System.out.println("Response from the server is corrupted.");
            }
            
        } catch (IOException e) {
            System.out.println("Closing connection...");
            
            try {
                client.close();
            } catch (IOException ex) {
                System.out.println("Cannot close connection.");
            }
        }
        
        return response;
    }
}