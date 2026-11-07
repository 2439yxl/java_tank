// com.mashibing.tank.net.TankMsg
package com.mashibing.tank.net;

import java.io.Serializable;

public class TankMsg implements Serializable {
    public MsgType type;

    // 玩家唯一 ID（服务端分配或客户端 UUID）
    public String playerId;

    // 坦克相关
    public int x, y;
    public String dir;      // Dir.name()
    public String group;    // Group.name()

    // 子弹相关
    public int bulletX, bulletY;
    public String bulletDir;

    // 构造器省略，按需 new
    public TankMsg() {}

    public static TankMsg join(String pid, int x, int y, String dir, String group) {
        TankMsg m = new TankMsg();
        m.type = MsgType.TANK_JOIN;
        m.playerId = pid;
        m.x = x; m.y = y; m.dir = dir; m.group = group;
        return m;
    }

    public static TankMsg dirChange(String pid, String dir) {
        TankMsg m = new TankMsg();
        m.type = MsgType.TANK_DIR_CHANGE;
        m.playerId = pid; m.dir = dir;
        return m;
    }

    public static TankMsg move(String pid, int x, int y) {
        TankMsg m = new TankMsg();
        m.type = MsgType.TANK_MOVE;
        m.playerId = pid; m.x = x; m.y = y;
        return m;
    }

    public static TankMsg fire(String pid, int bx, int by, String bdir) {
        TankMsg m = new TankMsg();
        m.type = MsgType.BULLET_FIRE;
        m.playerId = pid;
        m.bulletX = bx; m.bulletY = by; m.bulletDir = bdir;
        return m;
    }

    public static TankMsg die(String pid) {
        TankMsg m = new TankMsg();
        m.type = MsgType.TANK_DIE;
        m.playerId = pid;
        return m;
    }
    public static TankMsg leave(String pid) {
        TankMsg m = new TankMsg();
        m.type = MsgType.TANK_LEAVE;
        m.playerId = pid;
        return m;
    }
}