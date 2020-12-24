package mirea.ksp.pr4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class TaskManager extends Thread {
    Queue<Task> queue = new ArrayDeque<>();
    List<TaskExecutor> executors = new ArrayList<>();
    ResultSender sender = new ResultSender();
    final int THREAD_NUMBER = 3;

    public TaskManager() { // Инициализация потоков исполнения
        sender.start();
        for(int i = 0; i < THREAD_NUMBER; i++){
            executors.add(new TaskExecutor(sender));
            executors.get(i).start();
        }
    }

    public void run(){
        do{
            if(!queue.isEmpty())
            {
                for(var ex : executors){
                    if (ex.isOnline()){
                        Task t = queue.poll();
                        System.out.println("Data in TaskManager " + t.getExpression());
                        ex.exec(t); // Передача задачи свободному потоку исполнения
                        System.out.println("Found online executor, begin to execute");
                        break;
                    }
                }
            }
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                for(var ex : executors)
                    ex.interrupt();
                return;
            }
        }while(true);
    }

    public void add(Task task){
        queue.add(task);
        System.out.println("New task has been added to TaskManager");
    }
}
