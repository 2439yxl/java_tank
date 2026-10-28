package com.mashibing.tank;

import com.mashibing.tank.abstractfactory.BaseBullet;
import com.mashibing.tank.abstractfactory.BaseExplode;
import com.mashibing.tank.abstractfactory.GameFactory;
import com.mashibing.tank.abstractfactory.RectFactory;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    public Tank myTank;
    public List<BaseBullet> bullets = new ArrayList<>();
    public List<Tank> tanks = new ArrayList<>();
    public List<BaseExplode> explodes = new ArrayList<>();   // ★ 改这里
    public GameFactory gf = new RectFactory();

    public GameModel() {
        myTank = new Tank(400, 400, Dir.DOWN, Group.Good, this);
    }

    /**
     * 每帧推进：移动 + 碰撞 + 清理死亡对象。由主循环调用。
     */
    public void update() {
        // 1. 移动
        myTank.move();
        for (int i = 0; i < tanks.size(); i++) tanks.get(i).move();
        for (int i = 0; i < bullets.size(); i++) bullets.get(i).move();

        // 2. 碰撞
        collide();

        // 3. 清理
        for (int i = bullets.size() - 1; i >= 0; i--) {
            if (!bullets.get(i).isLiving()) bullets.remove(i);
        }
        for (int i = tanks.size() - 1; i >= 0; i--) {
            if (!tanks.get(i).isLiving()) tanks.remove(i);
        }
        for (int i = explodes.size() - 1; i >= 0; i--) {
            if (explodes.get(i).isFinished()) explodes.remove(i);
        }
    }

    /** 只负责绘制，不做任何逻辑。 */
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        g.drawString("坦克的数量：" + tanks.size(), 10, 80);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);
        for (int j = 0; j < bullets.size(); j++) bullets.get(j).paint(g);
        for (int j = 0; j < tanks.size(); j++) tanks.get(j).paint(g);
        for (int j = 0; j < explodes.size(); j++) explodes.get(j).paint(g);
    }

    /** 碰撞检测：从 View.paint 中搬出来，放在 Model 里。 */
    public void collide() {
        for (int j = 0; j < bullets.size(); j++) {
            BaseBullet b = bullets.get(j);
            for (int k = 0; k < tanks.size(); k++) {
                b.collideWith(tanks.get(k));
            }
        }
    }
}