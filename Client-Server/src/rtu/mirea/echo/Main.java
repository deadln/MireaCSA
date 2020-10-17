package rtu.mirea.echo;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {//Формат входной строки: "(количество сокетов) [номера портов через пробел]"
        int clientNum = Integer.parseInt(args[0]);
        CalcServer[] arr = new CalcServer[clientNum];
        for (int i = 1; i <= clientNum; i++)//инициализируем сокеты
        {
            System.out.println("Try to create server with port " + args[i]);
            arr[i - 1] = new CalcServer();
            arr[i - 1].setPort(args[i]);
            arr[i - 1].start();
        }
    }
}
