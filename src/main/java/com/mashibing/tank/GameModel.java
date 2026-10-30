package com.mashibing.tank;

import com.mashibing.tank.abstractfactory.BaseBullet;
import com.mashibing.tank.abstractfactory.BaseExplode;
import com.mashibing.tank.abstractfactory.GameFactory;
import com.mashibing.tank.abstractfactory.RectFactory;
import com.mashibing.tank.cor.BulletTankCollider;
import com.mashibing.tank.cor.Collider;
import com.mashibing.tank.cor.ColliderChain;
import com.mashibing.tank.cor.TankTankCollider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    public Tank myTank;
    Collider collider = new BulletTankCollider();
    Collider collider2 = new TankTankCollider();
    ColliderChain chain = new ColliderChain();
    private static final GameModel INSTANCE = new GameModel();
    Tank myTank;
    static {
        INSTANCE.init();

    }


    public List<BaseBullet> bullets = new ArrayList<>();
    public List<Tank> tanks = new ArrayList<>();
    public List<BaseExplode> explodes = new ArrayList<>();   // ★ 改这里
    public GameFactory gf = new RectFactory();
    public List<GameObject> objects = new ArrayList<>();


    public void init() {
        myTank = new Tank(200, 400, Dir.DOWN, Group.Good);
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
        for (int i = 0; i < initTankCount; i++) {
            new Tank(50 + i * 80, 200, Dir.DOWN, Group.Bad, this);
        }
        add(new Wall(150, 150, 200, 50));
        add(new Wall(550, 150, 200, 50));
        add(new Wall(300, 300, 50, 200));
        add(new Wall(550, 300, 50, 200));


    }

    public GameModel() {

    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void add(Explode go) {
        this.objects.add(go);

    }

    public void remove(GameObject go) {
        this.objects.remove(go);
    }


    /**
     * 每帧推进：移动 + 碰撞 + 清理死亡对象。由主循环调用。
     */
    public void update() {

    }

    /**
     * 只负责绘制，不做任何逻辑。
     */
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        g.drawString("坦克的数量：" + tanks.size(), 10, 80);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);
        for (int j = 0; j < objects.size(); j++) objects.get(j).paint(g);
        for (int j = 0; j < bullets.size(); j++) bullets.get(j).paint(g);
        for (int j = 0; j < tanks.size(); j++) tanks.get(j).paint(g);
        for (int j = 0; j < explodes.size(); j++) explodes.get(j).paint(g);
    }

    for(
    int i = 0;i<objects.size();i++)

    {
        for (int j = i + 1; j < objects.size(); j++) {
            GameObject o1 = objects.get(j);
            GameObject o2 = objects.get(j);
            collider.collide(o1, o2);
            collider.collide(o1, o2);
            //for
            chain.collide(o1, o2);
        }
    }

    /**
     * 碰撞检测：从 View.paint 中搬出来，放在 Model 里。
     */
    public void collide() {
        for (int j = 0; j < bullets.size(); j++) {
            BaseBullet b = bullets.get(j);
            for (int k = 0; k < tanks.size(); k++) {
                b.collideWith(tanks.get(k));
            }
        }
    }
}