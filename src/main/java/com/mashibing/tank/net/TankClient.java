// com.mashibing.tank.net.TankClient
package com.mashibing.tank.net;

import com.mashibing.tank.GameModel;
import com.mashibing.tank.Tank;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.UUID;

public class TankClient {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8888;

    // 本机玩家 ID，连上后由服务端确认或本地生成
    public String playerId = UUID.randomUUID().toString();

    private Channel channel;

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    // 解决粘包/半包，最大 1MB
                                    .addLast(new ObjectDecoder(1024 * 1024,
                                            ClassResolvers.cacheDisabled(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new TankClientHandler(TankClient.this));
                        }
                    });
            ChannelFuture f = b.connect(HOST, PORT).sync();
            this.channel = f.channel();
            System.out.println("[Netty] connected, playerId=" + playerId);
            Tank my = GameModel.getInstance().myTank;
            if (my != null) {
                my.setPlayerId(playerId);   // 让 myTank 的 ID 和 client 一致
                send(TankMsg.join(my.getPlayerId(), my.getX(), my.getY(),
                        my.getDir().name(), my.getGroup().name()));
                System.out.println("[Client] JOIN sent: " + my.getPlayerId());
            } else {
                System.err.println("[Netty] myTank 还没创建，JOIN 发不出去");
            }
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 发消息给服务端（线程安全，Netty 内部会切到 EventLoop） */
    public void send(TankMsg msg) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(msg);
        }
    }

    public void close() {
        if (channel != null) {
            channel.close();
            channel = null;
        }
    }
}