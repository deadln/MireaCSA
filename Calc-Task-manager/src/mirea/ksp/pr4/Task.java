package mirea.ksp.pr4;

import java.net.Socket;

public class Task {
    public Socket client;
    public long id;
    public String result;

    public Task(Socket client, long id) {
        this.client = client;
        this.id = id;
        this.result = "";
    }
}
