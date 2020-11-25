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
                try{
                    System.out.println("Result in ResultSender " + task.getResult());

                    //task.getBuffer().clear(); // Как очистить буфер полностью?
                    //task.getBuffer().put(new byte[80]); // Этот код блокирует клиента
                    //task.getBuffer().clear();

                    task.getBuffer().clear();

                    for(int i = 0; i < task.getResult().length(); i++){
                        task.getBuffer().put((byte)task.getResult().charAt(i));
                    }
                    task.getBuffer().put((byte)'#'); // Костыль. Отделение результата от мусора
                    task.getBuffer().position(0);
                    while (task.getBuffer().hasRemaining()) {
                        task.getClient().write(task.getBuffer());
                    }
                    task.getBuffer().compact();

                    System.out.println("Task " + task.getId() + " complete");
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
