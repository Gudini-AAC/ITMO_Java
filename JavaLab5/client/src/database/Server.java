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

    public void stop() throws IOException { client.close(); }

    public Server(String address, int port) throws IOException {
        client = SocketChannel.open(new InetSocketAddress(address, port));
        client.configureBlocking(true);
    }

    public Response sendMessage(Request msg) {
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
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return response;
    }
}