// com.mashibing.tank.net.TankServerHandler
package com.mashibing.tank.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TankServerHandler extends SimpleChannelInboundHandler<TankMsg> {
    public static final Map<String, TankMsg> tankStates = new ConcurrentHashMap<>();
    // TankServerHandler
    public static final Map<Channel, String> channelToPlayerId = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        TankServer.channels.add(ctx.channel());
        System.out.println("[Server] client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankMsg msg) {
        System.out.println("[Server] recv: type=" + msg.type + " pid=" + msg.playerId
                + " from " + ctx.channel().remoteAddress());
        if (msg.type == MsgType.TANK_JOIN) {
            if (msg.playerId == null) {
                System.out.println("[Server] JOIN with null pid, ignore");
                return;
            }
            tankStates.put(msg.playerId, msg);
            channelToPlayerId.put(ctx.channel(), msg.playerId);

            for (TankMsg m : tankStates.values()) {
                if (m.playerId.equals(msg.playerId)) continue;

                // ★ 关键：不管 m 是什么类型，强制重新构造一条 TANK_JOIN
                String dir = (m.dir == null) ? "UP" : m.dir;
                TankMsg join = TankMsg.join(m.playerId, m.x, m.y, dir, m.group);

                System.out.println("[Server] force-send JOIN: pid=" + join.playerId
                        + " type=" + join.type + " x=" + join.x + " y=" + join.y
                        + " dir=" + join.dir);
                ctx.channel().writeAndFlush(join);
            }
        }
        // 更新状态
        if (msg.type == MsgType.TANK_MOVE) {
            TankMsg join = tankStates.get(msg.playerId);
            if (join != null) {
                join.x = msg.x;
                join.y = msg.y;
                // join.dir 保持不变
            }
        }
        if (msg.type == MsgType.TANK_DIR_CHANGE) {
            TankMsg join = tankStates.get(msg.playerId);
            if (join != null) {
                join.dir = msg.dir;
            }
        }
        // 广播给其他人
        for (Channel ch : TankServer.channels) {
            if (ch != ctx.channel() && ch.isActive()) {
                ch.writeAndFlush(msg);
                System.out.println("[Server] broadcast " + msg.type + " pid=" + msg.playerId
                        + " to " + ch.remoteAddress());
            } else {
                System.out.println("[Server] skip broadcast to " + ch.remoteAddress()
                        + " active=" + ch.isActive() + " same=" + (ch == ctx.channel()));
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 客户端断开，广播离开消息（简单起见用 playerId 为空，实际应映射 channel→playerId）
        String pid = channelToPlayerId.remove(ctx.channel());
        System.out.println("[Server] client disconnected: " + ctx.channel().remoteAddress());
        TankServer.channels.remove(ctx.channel());
        if (pid != null) {
            tankStates.remove(pid);   // ✅ 删掉幽灵坦克状态
            System.out.println("[Server] disconnected, removed pid=" + pid);

            // 广播 TANK_LEAVE 给其他客户端
            TankMsg leave = TankMsg.leave(pid);
            for (Channel ch : TankServer.channels) {
                if (ch.isActive()) {
                    ch.writeAndFlush(leave);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}