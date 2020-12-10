import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        RequestData requestData = (RequestData) msg; // Получаем строку
        ResponseData responseData = new ResponseData();

        //Парсим выражение и вычисляем значение
        String[] arr = requestData.getStringValue().split(" ");
        double res = 0;
        if (arr[1].equals("+"))
            res = Double.parseDouble(arr[0]) + Double.parseDouble(arr[2]);
        else if (arr[1].equals("-"))
            res = Double.parseDouble(arr[0]) - Double.parseDouble(arr[2]);
        else if (arr[1].equals("*"))
            res = Double.parseDouble(arr[0]) * Double.parseDouble(arr[2]);
        else if (arr[1].equals("/"))
            res = Double.parseDouble(arr[0]) / Double.parseDouble(arr[2]);

        // Отправляем результат выражения
        responseData.setDoubleValue(res);
        System.out.println(requestData.getStringValue());
        ChannelFuture future = ctx.writeAndFlush(responseData);
        future.addListener(ChannelFutureListener.CLOSE);
        System.out.println(requestData);
    }
}