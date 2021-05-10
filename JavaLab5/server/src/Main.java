import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.Set;
import java.util.Iterator;
import java.time.LocalDate;

import database.Database;
import protocol.*;

public class Main {
    private final int port;
    private ServerSocketChannel ssc;
    private Selector selector;
    private ByteBuffer buf = ByteBuffer.allocate(1024);
    private Database database;

    Main(int port, String login, String password) throws IOException {
        this.port = port;
        
        database = new Database(login, password);
        
        try {
            database.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        selector = Selector.open();

        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        try {
            System.out.printf("Server starting on port %d\n", port);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            while(ssc.isOpen()) {
                if (reader.ready()) {
                    String line = reader.readLine();
                    
                    if ("exit".equals(line)) break;
                }
                
                if (selector.selectNow() != 0) {
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                        
                    while(iter.hasNext()) {
                        SelectionKey key = iter.next();
    
                        if(key.isAcceptable()) handleAccept(key);
                        if(key.isReadable())   handleRead(key);
                    
                        iter.remove();
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel sc = ((ServerSocketChannel)key.channel()).accept();
        
        String address = (new StringBuilder(sc.socket().getInetAddress().toString())).append(":").append(sc.socket().getPort()).toString();
        
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ, address);

        System.out.printf("Accepted connection from: %s\n", address);
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel ch = (SocketChannel)key.channel();
        ByteArrayOutputStream sb = new ByteArrayOutputStream();
        
        buf.clear();
        int read = 0;

        try {
            while((read = ch.read(buf)) > 0) {
                System.out.printf("Read: %d\n", read);
                
                buf.flip();
                byte[] bytes = new byte[buf.limit()];
                buf.get(bytes);
                
                sb.write(bytes, 0, bytes.length);
                buf.clear();
            }
        } catch (IOException e) {
            System.out.println(key.attachment() + " forcibly left the server.\n");
            ch.close();
            return;
        }

        boolean sendUpdateRequests = false;        
        if (read < 0) {
            System.out.println(key.attachment() + " left the server.");
            ch.close();
        } else {
            ObjectInput objectInput = new ObjectInputStream(new ByteArrayInputStream(sb.toByteArray()));

            Response response = null;            
            try {
                Object obj = objectInput.readObject();
                
                if (obj instanceof Request) {
                    response = database.respond((Request)obj);
                    if (response instanceof ResponseAdd     ||
                        response instanceof ResponseReplace ||
                        response instanceof ResponseRemove  ||
                        response instanceof ResponseShuffle)
                    {
                        sendUpdateRequests = true;
                    }
                } else {
                    System.out.println("Request is corrupted.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            sb.reset();
            ObjectOutput objectOutput = new ObjectOutputStream(sb);
            objectOutput.writeObject(response);
            
            ch.write(ByteBuffer.wrap(sb.toByteArray()));
        }
        
        if (sendUpdateRequests) {
            byte[] magic = new byte[4]; 
            magic[0] = 0xC;
            magic[1] = 0xA;
            magic[2] = 0xF;
            magic[3] = 0xE;
            
            Iterator<SelectionKey> iter = selector.keys().iterator();
            while(iter.hasNext()) {
                Channel channel = iter.next().channel();
                if (channel instanceof SocketChannel) {
                    ((SocketChannel)channel).write(ByteBuffer.wrap(magic));
                    System.out.println("Sending update request.");
                }
            }
        }
        
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2 ) { System.out.println("Server expects exactly 2 arguments."); System.exit(1); }
        
        Main server = new Main(5454, args[0], args[1]);
        server.run();
    }

    
}