package database;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.Set;
import java.util.Iterator;

import protocol.Request;
import protocol.RequestRetrieve;
import protocol.Response;

public class Server {
    private SocketChannel client;
    private String address;
    private int port;
    private boolean updateIsRequested = true;
    
    public void stop() throws IOException { client.close(); }

    public Server(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        
        client = SocketChannel.open(new InetSocketAddress(address, port));
        client.configureBlocking(true);
    }

    public boolean isUpdateRequested() {
        while (readUpdateRequest()) updateIsRequested = true;
        return updateIsRequested;
    }
    
    private boolean readUpdateRequest() {
        Socket socket = client.socket();
        try {
            InputStream istream = socket.getInputStream();
            if (istream.available() >= 4) { // Server is sending 4 bytes (0xCAFE) to request an update.
                istream.mark(8);
                
                byte[] magic = new byte[4]; 
                istream.read(magic, 0, 4);
                
                boolean isMagic = true;
                isMagic &= magic[0] == 0xC;
                isMagic &= magic[1] == 0xA;
                isMagic &= magic[2] == 0xF;
                isMagic &= magic[3] == 0xE;
                
                if (!isMagic) istream.reset();
                return isMagic;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
    
    public Response sendMessage(Request msg) {
        while (!client.isOpen()) {
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
                while (readUpdateRequest()) updateIsRequested = true;
                ObjectInput objectInput = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Object obj = objectInput.readObject();
                
                if (obj instanceof Response)
                    response = (Response)obj;
                
                while (readUpdateRequest()) updateIsRequested = true;
            } catch (Exception e) {
                System.out.println("Response from the server is corrupted.");
            }
            
        } catch (IOException e) {
            System.out.println("Closing connection...");
            
            try {
                client.close();
                response = sendMessage(msg);
            } catch (IOException ex) {
                System.out.println("Cannot close connection.");
            }
        }
        
        if (msg instanceof RequestRetrieve && response != null && response.isSuccessful())
            updateIsRequested = false;
        
        return response;
    }
}