package rtu.mirea.echo;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class CalcQueue extends Thread{
    private Queue<String> queue;
    PrintWriter outStream;

    public void setOutStream(PrintWriter os)//Установка выводящего потока
    {
        outStream = os;
    }

    public void executeCalculation(String s)//Добавление строки с выражением в очередь в очередь
    {
        queue.add(s);
    }

    @Override
    public void run()
    {
        queue = new ArrayDeque<>();

        do {
            if(!queue.isEmpty())
            {
                String inputLine = queue.poll();
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
                outStream.println(Double.toString(res));
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
}
