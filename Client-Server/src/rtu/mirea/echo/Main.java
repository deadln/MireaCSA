package rtu.mirea.echo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public volatile int threads = 0;
    public static void main(String[] args) {//Формат входной строки: "номер порта"
        int threads = 0;
        try(ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        )
        {
            while(true)
            {                Socket clientSocket = serverSocket.accept();
                new CalcServerThread(args[0], serverSocket, clientSocket).start();
                threads++;
                System.out.println("Thread number: " + threads);
            }
        }
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + args[0] + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }
}
