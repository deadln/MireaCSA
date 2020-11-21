package mirea.ksp.pr4;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class ResultSender extends Thread{
    Queue<Task> queue = new ArrayDeque<>();

    public void run(){
        do{
            if(!queue.isEmpty())
            {
                Task task = queue.poll();
                System.out.println(task.client.isClosed() + " - закрыт ли сокет");
                System.out.println("Channel " + task.client.getChannel());
                try (OutputStream str = task.client.getOutputStream();
                        PrintWriter out =
                             new PrintWriter(str, true); //!!!
                ) {
                    System.out.println("Result is " + task.result);
                    out.println(task.result);
                    System.out.println("Task " + task.id + " complete");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                return;
            }
        }while(true);
    }

    public void add(Task task){
        queue.add(task);
    }
}
