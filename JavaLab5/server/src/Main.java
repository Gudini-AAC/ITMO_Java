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
    private ByteBuffer buf = ByteBuffer.allocate(256);
    private Database database;

    Main(int port, String path) throws IOException {
        this.port = port;
        
        database = new Database(path);
        
        try {
            database.load();
        } catch (FileNotFoundException e) {
            System.out.println("Database file not found");
        } catch (Exception e) {
            System.out.println("Database file is corrupted.");
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
                    
                    if ("exit".equals(line)) {
                        break;
                    } else if ("save".equals(line)) {
                        try {
                            database.save();
                            System.out.println("Database is saved!");
                        } catch (FileNotFoundException e) {
                            System.out.println("Database file not found\n");
                        }
                    }
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
        
        String address = (new StringBuilder(sc.socket().getInetAddress().toString())).append(":").append( sc.socket().getPort()).toString();
        
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
        
        if (read < 0) {
            System.out.println(key.attachment() + " left the server.");
            ch.close();
        } else {
            ObjectInput objectInput = new ObjectInputStream(new ByteArrayInputStream(sb.toByteArray()));

            Response response = null;            
            try {
                Object obj = objectInput.readObject();
                
                if (obj instanceof Request)
                    response = database.respond((Request)obj);
                else
                    System.out.println("Request is corrupted.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            sb.reset();
            ObjectOutput objectOutput = new ObjectOutputStream(sb);
            objectOutput.writeObject(response);
            
            ch.write(ByteBuffer.wrap(sb.toByteArray()));
        }

    }

    public static void main(String[] args) throws IOException {
        Main server = new Main(5454, args[0]);
        server.run();
        
        try {
            server.database.save();
        } catch (FileNotFoundException e) {
            System.out.println("Database file not found\n");
        }
    }

    
}