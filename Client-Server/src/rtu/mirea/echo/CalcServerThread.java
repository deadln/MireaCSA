package rtu.mirea.echo;

import java.net.*;
import java.io.*;

public class CalcServerThread extends Thread {

    String port;
    ServerSocket serverSocket;
    Socket clientSocket;

    public CalcServerThread(String port, ServerSocket serverSocket, Socket clientSocket) {
        this.port = port;
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }

    public void setPort(String s){
        port = s;
    }

    public void run()
    {
        try (
                /*ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(port));
                Socket clientSocket = serverSocket.accept();*/
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("created connection with port " + port);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                String[] arr = inputLine.split(" ");
                double res = 0;
                if(arr[1].equals("+"))
                    res = Double.parseDouble(arr[0]) + Double.parseDouble(arr[2]);
                else if(arr[1].equals("-"))
                    res = Double.parseDouble(arr[0]) - Double.parseDouble(arr[2]);
                else if(arr[1].equals("*"))
                    res = Double.parseDouble(arr[0]) * Double.parseDouble(arr[2]);
                else if(arr[1].equals("/"))
                    res = Double.parseDouble(arr[0]) / Double.parseDouble(arr[2]);
                out.println(Double.toString(res));
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}

//C:\Users\glebu\.jdks\openjdk-15\bin
//C:\Program Files (x86)\Common Files\Oracle\Java\javapath