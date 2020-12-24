import io.netty.channel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ChatHandler extends SimpleChannelInboundHandler<String> {

    static HashMap<Channel, String> users = new HashMap<>(); // Канал пользователя <-> Имя пользователя
    static ArrayList<String> messages = new ArrayList<>(); // Сообщения, отправленные в чате

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Ввод имени
        ChannelFuture cf = ctx.writeAndFlush("Enter your username:\r\n");
        if(!cf.isSuccess()) {
            System.out.println("Error: " + cf.cause());
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {
        msg = msg.trim();
        if(!users.keySet().contains(ctx.channel())){ // Если пользователь не зарегистрирован
            if(users.values().contains(msg)){ // Проверка имени на дубликат
                ctx.writeAndFlush("This username is not available. Try the other one.\r\n");
                System.out.println("Username not available");
            }
            else{ // Добавление пользователя
                users.put(ctx.channel(), msg);
                for(var message : messages)
                    ctx.channel().writeAndFlush(message);
                messages.add("User <" + msg + "> has joined the chat\r\n");
                sendMessage("User <" + msg + "> has joined the chat\r\n");
                System.out.println("New user " + msg);
            }
        }
        else{ // Отправка сообщения
            String message = "<" + users.get(ctx.channel()) + ">: " + msg + "\r\n";
            messages.add(message);
            sendMessage(message);
            System.out.println("Sending message " + msg);
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // Выход пользователя
        sendMessage("User <" + users.get(ctx.channel()) + "> has left the chat\r\n");
        messages.add("User <" + users.get(ctx.channel()) + "> has left the chat\r\n");
        users.remove(ctx.channel());
        System.out.println("User left");
    }


    public void sendMessage(String msg){ // Отправка сообщения всем пользователям
        for(var channel : users.keySet()){
            channel.writeAndFlush(msg);
        }
    }

}