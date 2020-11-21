package mirea.ksp.pr4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TaskExecutor extends Thread{
    private volatile boolean online;
    private Task task;
    private volatile ResultSender sender;

    public TaskExecutor(ResultSender sender) {
        this.sender = sender;
    }

    public void run(){
        online = true;
        do {
            if(!online)
            {
                try {

                    BufferedReader in = new BufferedReader(
                    new InputStreamReader(task.client.getInputStream()));

                    String inputLine;
                    if((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                        String[] arr = inputLine.split(" ");
                        double res = 0;
                        if (arr[1].equals("+"))
                            res = Double.parseDouble(arr[0]) + Double.parseDouble(arr[2]);
                        else if (arr[1].equals("-"))
                            res = Double.parseDouble(arr[0]) - Double.parseDouble(arr[2]);
                        else if (arr[1].equals("*"))
                            res = Double.parseDouble(arr[0]) * Double.parseDouble(arr[2]);
                        else if (arr[1].equals("/"))
                            res = Double.parseDouble(arr[0]) / Double.parseDouble(arr[2]);
                        task.result = Double.toString(res); // Передача результата потоку отправки результатов
                        sender.add(task);
                        System.out.println("Calculation complete");
                    }
                } catch (IOException e) {
                    System.out.println("Exception caught in TaskExecutor when trying to listen on port "
                            + task.client.getPort() + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
                online = true;
            }
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                return;
            }
        }while(true);
    }

    public void exec(Task task){
        this.task = task;
        online = false;
    }

    public boolean isOnline() {
        return online;
    }
}
