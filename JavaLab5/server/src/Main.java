import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.Set;
import java.util.Iterator;



public class Main {
    private final int port;
    private ServerSocketChannel ssc;
    private Selector selector;
    private ByteBuffer buf = ByteBuffer.allocate(256);

    Main(int port) throws IOException {
        this.port = port;
        
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        selector = Selector.open();

        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        try {
            System.out.printf("Server starting on port %d\n", port);

            Iterator<SelectionKey> iter;
            SelectionKey key;
            while(ssc.isOpen()) {
                selector.select();
                iter = selector.selectedKeys().iterator();
                
                while(iter.hasNext()) {
                    key = iter.next();

                    if(key.isAcceptable()) handleAccept(key);
                    if(key.isReadable())   handleRead(key);
                
                    iter.remove();
                }
            }
        } catch(IOException e) {
            System.out.printf("IOException, server of port %d terminating. Stack trace:", port);
            e.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel sc = ((ServerSocketChannel)key.channel()).accept();
        
        String address = (new StringBuilder( sc.socket().getInetAddress().toString() )).append(":").append( sc.socket().getPort() ).toString();
        
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ, address);

        System.out.printf("Accepted connection from: %s\n", address);
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel ch = (SocketChannel)key.channel();
        ByteArrayOutputStream sb = new ByteArrayOutputStream();
        
        buf.clear();
        int read = 0;
        while((read = ch.read(buf)) > 0) {
            System.out.printf("Read: %d\n", read);
            
            buf.flip();
            byte[] bytes = new byte[buf.limit()];
            buf.get(bytes);
            
            sb.write(bytes, 0, bytes.length);
            buf.clear();
        }
        
        String msg;
        if (read < 0) {
            msg = key.attachment() + " left the server.\n";
            ch.close();
        } else {
            ObjectInput objectInput = new ObjectInputStream(new ByteArrayInputStream(sb.toByteArray()));
            
            try {
                Object obj = objectInput.readObject();
                
                if (obj instanceof String)
                    msg = key.attachment() + ": Echo : " + (String)obj;
                else
                    msg = "Object in not a String!";
            } catch(Exception e) {
                e.printStackTrace();
                msg = "Didn't work out!";
            }
            
            sb.reset();
            
            ObjectOutput objectOutput = new ObjectOutputStream(sb);
            objectOutput.writeObject(msg);
            
            ch.write(ByteBuffer.wrap(sb.toByteArray()));
        }

        System.out.println(msg);
    }

    public static void main(String[] args) throws IOException {
        Main server = new Main(5454);
        server.run();
    }

    
}