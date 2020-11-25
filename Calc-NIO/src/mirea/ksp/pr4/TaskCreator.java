package mirea.ksp.pr4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskCreator extends Thread{

    String port;
    ServerSocketChannel server;
    List<Socket> clients = new ArrayList<>();
    List<BufferedReader> readers = new ArrayList<>();
    Map<SocketChannel, ByteBuffer> sockets;
    int count = 0;

    public TaskCreator(String arg, ServerSocketChannel serverSocket, Map<SocketChannel, ByteBuffer> sockets) {
        this.port = arg;
        this.server = serverSocket;
        this.sockets = sockets;
    }


    public void run(){
            TaskManager tm = new TaskManager();
            tm.start();
            while (true) {
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
                            System.out.println("Data in TaskCreator: " + s.toString());

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
