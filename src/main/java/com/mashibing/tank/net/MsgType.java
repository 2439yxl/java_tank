// com.mashibing.tank.net.MsgType
package com.mashibing.tank.net;

public enum MsgType {
    TANK_JOIN,        // 新玩家加入
    TANK_LEAVE,       // 玩家离开
    TANK_DIR_CHANGE,  // 坦克转向
    TANK_MOVE,        // 坦克移动（位置同步）
    TANK_DIE,         // 坦克死亡
    BULLET_FIRE,      // 开火
    TANK_STATE_LIST   // 服务端下发的全量状态（新玩家进房时）
}