package mirea.ksp.pr4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {//Формат входной строки: "номер порта"

        try(ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]))
        )
        {
            TaskCreator tc = new TaskCreator(args[0], serverSocket);
            tc.start();
            while(true)
            {
                Socket clientSocket = serverSocket.accept(); // Подключение нового клиента
                System.out.println("Created new connection: " + clientSocket.getChannel());
                tc.add(clientSocket);
            }
        }
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + args[0] + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }
}
