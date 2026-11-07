// com.mashibing.tank.net.TankClientHandler
package com.mashibing.tank.net;

import com.mashibing.tank.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TankClientHandler extends SimpleChannelInboundHandler<TankMsg> {

    private final TankClient client;

    public TankClientHandler(TankClient client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankMsg msg) {
        GameModel gm = GameModel.getInstance();

        switch (msg.type) {
            case TANK_JOIN:
                System.out.println("[Client] JOIN recv: pid=" + msg.playerId + " myId=" + client.playerId);
                if (!msg.playerId.equals(client.playerId)) {
                    // 1. 检查是否已存在（防重复 JOIN 重复 new）
                    if (gm.findTankByPlayerId(msg.playerId) != null) {
                        System.out.println("[Client] tank already exists, skip: " + msg.playerId);
                        break;
                    }
                    // 2. new 别人的坦克，Group.Bad（黄色外观）
                    Tank other = new Tank(msg.x, msg.y, Dir.valueOf(msg.dir), Group.Bad, gm);
                    other.setPlayerId(msg.playerId);
                    // 3. 别人的坦克不是 AI，本地不自动动
//                    other.setAI(false);        // 如果加了 isAI
                    other.setMoving(false);    // 本地不动，只靠 TANK_MOVE 消息
                    System.out.println("[Client] new other tank, pid=" + msg.playerId
                            + " at " + msg.x + "," + msg.y
                            + " tanks.size=" + gm.tanks.size()
                            + " objects.size=" + gm.objects.size());
                }
                break;

            case TANK_LEAVE:
                gm.removeTankByPlayerId(msg.playerId);
                System.out.println("[Client] TANK_LEAVE recv: " + msg.playerId);
                break;

            case TANK_DIR_CHANGE:
                Tank t1 = gm.findTankByPlayerId(msg.playerId);
                if (t1 != null) t1.setDir(Dir.valueOf(msg.dir));
                break;

            case TANK_MOVE:
                Tank t2 = gm.findTankByPlayerId(msg.playerId);
                if (t2 != null) {
                    t2.setX(msg.x);
                    t2.setY(msg.y);
                    t2.getRect().x = msg.x;
                    t2.getRect().y = msg.y;
                }
                break;

            case BULLET_FIRE:
                // 别人开火：本地生成一颗子弹（Group 取对方坦克的 group）
                Tank shooter = gm.findTankByPlayerId(msg.playerId);
                Group g = (shooter != null) ? shooter.getGroup() : Group.Bad;
                new Bullet(msg.bulletX, msg.bulletY,
                        Dir.valueOf(msg.bulletDir), g, gm);
                break;

            case TANK_DIE:
                Tank dead = gm.findTankByPlayerId(msg.playerId);
                if (dead != null) dead.die();
                break;

            case TANK_STATE_LIST:
                // 新玩家进房时服务端下发的全量坦克列表
                // 这里可以遍历 msg 里带的列表逐个 JOIN（简单起见省略，见服务端部分）
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}