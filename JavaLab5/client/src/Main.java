import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.Set;
import java.util.Iterator;

public class Main {
    private static SocketChannel client;
    private static Main instance;
    private static ObjectOutput objectOutput;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Main client = start();
        
        for (;;) {
            try {
                String str = reader.readLine();
                if ("exit".equals(str)) break;
                
                System.out.println(client.sendMessage(str));
            } catch (Exception e) {
                System.out.println(e.toString());
            } 
        }
        
        client.stop();   
    }

    public static Main start() {
        if (instance == null)
            instance = new Main();

        return instance;
    }

    public static void stop() throws IOException {
        client.close();
    }

    private Main() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 5454));
            client.configureBlocking(true);
        } catch (IOException e) {
            System.out.println("Network access is denied. Exiting.");
            System.exit(65);
        }
    }

    public String sendMessage(String msg) {
        String response = null;

        try {
            Socket socket = client.socket();
            
            ObjectOutput objectOutput = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            objectOutput.writeObject(msg);
            objectOutput.flush();
            
            try {
                ObjectInput objectInput = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Object obj = objectInput.readObject();
                
                if (obj instanceof String)
                    response = (String)obj;
                else
                    response = "Object in not a String!";
            } catch(Exception e) {
                e.printStackTrace();
                response = "Didn't work out!";
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return response;
    }
}