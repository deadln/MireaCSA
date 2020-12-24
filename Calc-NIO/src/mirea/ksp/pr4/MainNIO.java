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

    static int count = 0;

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocket = ServerSocketChannel.open(); // Creating the server on port 8080

        // Binding this server on the port
        serverSocket.bind(new InetSocketAddress(8080));

        serverSocket.configureBlocking(false); // Make Server nonBlocking

        final Map<SocketChannel, ByteBuffer> sockets = new ConcurrentHashMap<>();

        //TaskCreator tc = new TaskCreator(args[0], serverSocket, sockets);
        //tc.start();

        TaskManager tm = new TaskManager();
        tm.start();

        while (true) {
            // Accept means it will accept the incoming connection
            final SocketChannel socket = serverSocket.accept();
            // It may return null, as since its Non-Blocking, there may not be anything on this socket everytime.

            if (socket != null) {
                socket.configureBlocking(false); // Required, socket should also be NonBlocking

                // Every socket will have its own byte buffer
                sockets.put(socket, ByteBuffer.allocateDirect(200));
            }

            sockets.keySet().removeIf((socketChannel) -> !socketChannel.isOpen());
            sockets.forEach((socketCh, byteBuffer) -> {
                try {
                    int data = socketCh.read(byteBuffer);
                    if (data == -1) {
                        closeSocket(socketCh);
                    } else if (data != 0) { // 0 means socket has nothing to say
                        byteBuffer.flip();
                        StringBuilder s = new StringBuilder();
                        for (int x = 0; x < byteBuffer.limit(); x++) { // Считываем пример посимвольно
                            s.append((char)byteBuffer.get());
                        }
                        System.out.println("Data in Main: " + s.toString());

                        tm.add(new Task(s.toString(), socketCh, byteBuffer, count)); // Добавление задачи в TaskManager
                        count++;
                    }
                } catch (IOException e) {
                    closeSocket(socketCh); // Исключение срабатывает при отключении клиента
                    //throw new UncheckedIOException(e); // Я не уверен что это нормально
                }
            });
        }
    }

    private static void closeSocket(final SocketChannel socket) {
        try {
            socket.close();
        } catch (IOException ignore) {
            System.out.println("Error after attempt of closing socket");
        }
    }
}
