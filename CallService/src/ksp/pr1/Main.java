package ksp.pr1;

import java.util.Scanner;

public class Main {
    private static int ops;
    private static int call_num;
    private static float call_len;

    public static void main(String[] args) {
        System.out.println("Введите количество операторов");

        Scanner in = new Scanner(System.in);
        ops = in.nextInt();

        call_num = 1;
        CallQueue q = new CallQueue(ops);

        System.out.println("[число > 0] - запрос на звонок длительностью [число] секунд. 0 или меньше завершит программу");
        q.start();
        while(true)
        {
            call_len = in.nextInt();
            if(call_len <= 0)
                break;
            q.makeCall(call_num, call_len);
            call_num++;
        }
        q.interrupt();
    }
}
