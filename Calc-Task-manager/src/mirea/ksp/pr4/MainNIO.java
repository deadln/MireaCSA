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

            sockets.keySet().removeIf((socketChannel) -> !socketChannel.isOpen());
            // Remove socketChannel which is not opened

            // Iterate through sockets to see f any socket has anything to say
            sockets.forEach((socketCh, byteBuffer) -> {
                try {
                    int data = socketCh.read(byteBuffer);
                    if (data == -1) {
                        closeSocket(socketCh);
                    } else if (data != 0) { // 0 means socket has nothing to say
                        byteBuffer.flip();
                        invertCase(byteBuffer);
                        while (byteBuffer.hasRemaining()) {
                            socketCh.write(byteBuffer);
                        }
                        byteBuffer.compact();

                    }
                } catch (IOException e) {
                    closeSocket(socketCh);
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

    private static void closeSocket(final SocketChannel socket) {
        try {
            socket.close();
        } catch (IOException ignore) {

        }
    }

    private static void invertCase(final ByteBuffer byteBuffer) {
        for (int x = 0; x < byteBuffer.limit(); x++) { // read every byte in it.
            byteBuffer.put(x, (byte) invertCase(byteBuffer.get(x)));
        }
    }

    private static int invertCase(final int data) {
        return Character.isLetter(data) ?

                Character.isUpperCase(data)
                        ? Character.toLowerCase(data)
                        : Character.toUpperCase(data) :

                data;
    }
}
