package rtu.mirea.echo;

import java.net.*;
import java.io.*;

public class CalcServer extends Thread {

    String port;

    public void setPort(String s){
        port = s;
    }

    public void run()
    {

        int portNumber = Integer.parseInt(port);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(port));
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("created server with port " + port);
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
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }


}