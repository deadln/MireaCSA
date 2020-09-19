package ksp.pr1;

import javafx.util.Pair;

public class Operator extends Thread {
    private volatile int op_num;
    private volatile int call_num;
    private volatile double call_time;
    private volatile boolean online;

    public Operator(int op_num) {
        this.op_num = op_num;
    }

    public boolean isOnline() {
        return online;
    }

    public void makeCall(Pair<Integer, Double> call)
    {
        if(online) {
            this.call_num = call.getKey();
            this.call_time = call.getValue();
            this.online = false;
        }
        else
            System.out.println("Оператор №" + this.op_num + " на данный момент занят");
    }

    @Override
    public void run()
    {
        online = true;
        do {
            if(!online)
            {
                System.out.println("Оператор №" + op_num + " обрабатывает запрос №" + call_num + " длиной " + call_time +
                        " секунд");
                try {
                    Thread.sleep((int)(1000 * call_time));
                } catch (InterruptedException e) {
                }
                System.out.println("Обработка запроса №" + call_num + " завершена");
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
}
