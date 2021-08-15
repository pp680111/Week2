package server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

public class NettyHttpHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest requestEntity = (FullHttpRequest) msg;
            String uri = requestEntity.uri();
            if (uri.contains("/hello")) {
                requestHandler(requestEntity, ctx, "Shoreline/海岸线");
            } else {
                requestHandler(requestEntity, ctx, "Polyphia-Crush");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void requestHandler(FullHttpRequest request, ChannelHandlerContext ctx, String body) {
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, Unpooled.wrappedBuffer(body.getBytes()));
            response.headers().set("Content-Type", "text/html");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        } finally {
            if (request != null) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                if (!HttpUtil.isKeepAlive(request)) {
//                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                } else {
//                    //TODO
//                }
            }
        }
    }
}
