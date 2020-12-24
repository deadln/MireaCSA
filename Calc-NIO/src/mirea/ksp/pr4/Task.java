package mirea.ksp.pr4;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Task {
    private String expression;
    private SocketChannel client;
    private ByteBuffer buffer;
    private long id;
    private String result;

    public Task(String expression, SocketChannel client, ByteBuffer buffer, long id) {
        this.expression = expression;
        this.client = client;
        this.buffer = buffer;
        this.id = id;
        this.result = "";
    }

    public String getExpression() {
        return expression;
    }

    public SocketChannel getClient() {
        return client;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public long getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
