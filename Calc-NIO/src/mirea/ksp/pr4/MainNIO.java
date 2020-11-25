package mirea.ksp.pr4;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MainNIO {

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocket = ServerSocketChannel.open(); // Creating the server on port 8080

        // Binding this server on the port
        serverSocket.bind(new InetSocketAddress(8080));

        serverSocket.configureBlocking(false); // Make Server nonBlocking

        final Map<SocketChannel, ByteBuffer> sockets = new ConcurrentHashMap<>();

        TaskCreator tc = new TaskCreator(args[0], serverSocket, sockets);
        tc.start();

        while (true) {
            // Accept means it will accept the incoming connection
            final SocketChannel socket = serverSocket.accept();
            // It may return null, as since its Non-Blocking, there may not be anything on this socket everytime.

            if (socket != null) {
                socket.configureBlocking(false); // Required, socket should also be NonBlocking

                // Every socket will have its own byte buffer
                sockets.put(socket, ByteBuffer.allocateDirect(80));
            }
        }
    }
}
