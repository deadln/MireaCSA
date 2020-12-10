import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Scanner;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {

        RequestData msg = new RequestData();
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        if(s.equals("")){
            return;
        }
        msg.setStringValue(s);
        ChannelFuture future = ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println(((ResponseData)msg).getDoubleValue()); // Добавил сам getIntValue()
        ctx.close();
    }
}