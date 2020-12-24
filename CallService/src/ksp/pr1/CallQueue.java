package ksp.pr1;

import javafx.util.Pair;

import java.util.*;

public class CallQueue extends Thread {
    private volatile int ops;
    private volatile ArrayList<Operator> operators;
    private volatile Queue<Pair<Integer, Double>> queue;

    public CallQueue(int ops) {
        this.ops = ops;
    }

    public void makeCall(int call, double time)
    {
        queue.add(new Pair<Integer, Double>(call, time));
        System.out.println("Звонок №" + call + " добавлен в очередь");
    }

    @Override
    public void run()
    {
        operators = new ArrayList<Operator>();
        for (int i = 1; i <= ops; i++)
        {
            Operator op = new Operator(i);
            op.start();
            operators.add(op);
        }

        queue = new ArrayDeque<Pair<Integer, Double>>();
        do {
            if(!queue.isEmpty())
            {
                for(Operator op : operators)
                {
                    if(op.isOnline())
                    {
                        Pair<Integer, Double> call = queue.poll();
                        op.makeCall(call);
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
                for(Operator op : operators)
                    op.interrupt();
                return;
            }
        }while(true);
    }
}
