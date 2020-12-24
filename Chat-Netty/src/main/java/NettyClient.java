import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class NettyClient {
    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(
                            new DelimiterBasedFrameDecoder(1234, Delimiters.lineDelimiter()),
                            new StringEncoder(),
                            new StringDecoder(),
                            new ClientHandler()
                    );
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            Channel channel = f.channel();
            Scanner in = new Scanner(System.in);
            String s;
            while(true) { // Цикл для того чтобы клиент не закрывался сразу после получения результата
                s = in.nextLine();
                ChannelFuture cf = channel.writeAndFlush(s + "\r\n");
            }
            // ^^^ МОЯ МОДИФИКАЦИЯ ^^^
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}