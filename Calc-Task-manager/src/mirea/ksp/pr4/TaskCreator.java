package mirea.ksp.pr4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

public class TaskCreator extends Thread{

    String port;
    ServerSocket server;
    List<Socket> clients = new ArrayList<>();
    List<BufferedReader> readers = new ArrayList<>();

    public TaskCreator(String arg, ServerSocket serverSocket) {
        this.port = arg;
        this.server = serverSocket;
    }

    public void run(){
        int count = 0;
        try {
            TaskManager tm = new TaskManager();
            tm.start();
            while (true) {
                for (int i = count; i < clients.size(); i++) { // Проход по всем текущим клиентам
                    if (readers.get(i).ready()) {
                        tm.add(new Task(clients.get(i), count)); // Добавление задачи в TaskManager
                        count++;
                        //Код для установки одноразового исполнения
                        clients.remove(i);
                        i--;
                    }
                    //else if (readers.get(i).) //Если помер дед Максим
                }
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void add(Socket clientSocket) throws IOException {
        clients.add(clientSocket);
        readers.add(new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())));
    }

}
