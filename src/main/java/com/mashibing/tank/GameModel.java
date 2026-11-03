package com.mashibing.tank;

import com.mashibing.tank.abstractfactory.BaseBullet;
import com.mashibing.tank.abstractfactory.BaseExplode;
import com.mashibing.tank.abstractfactory.GameFactory;
import com.mashibing.tank.abstractfactory.RectFactory;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    public Tank myTank;
    private static final GameModel INSTANCE = new GameModel();

    static {
        INSTANCE.init();
    }

    public List<BaseBullet> bullets = new ArrayList<>();
    public List<Tank> tanks = new ArrayList<>();
    public List<BaseExplode> explodes = new ArrayList<>();
    public GameFactory gf = new RectFactory();
    public List<GameObject> objects = new ArrayList<>();

    public GameModel() {
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void init() {
        myTank = new Tank(200, 400, Dir.DOWN, Group.Good, this);
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
        for (int i = 0; i < initTankCount; i++) {
            new Tank(50 + i * 80, 200, Dir.DOWN, Group.Bad, this);
        }
        add(new Wall(150, 150, 200, 50));
        add(new Wall(550, 150, 200, 50));
        add(new Wall(300, 300, 50, 200));
        add(new Wall(550, 300, 50, 200));
    }

    // ---------- add 系列 ----------
    public void add(Wall wall) {
        objects.add(wall);
    }

    public void add(Explode explode) {
        explodes.add(explode);
    }

    public void add(Bullet bullet) {
        bullets.add(bullet);
    }

    public void add(Tank tank) {
        tanks.add(tank);
    }

    // ---------- remove 系列 ----------
    public void removeBullet(Bullet b) {
        bullets.remove(b);
    }

    public void removeTank(Tank t) {
        tanks.remove(t);
    }

    public void removeExplode(BaseExplode e) {
        explodes.remove(e);
    }

    // ---------- 主循环推进 ----------
    public void update() {
        // 1. 移动子弹
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
        }
        // 2. 移动坦克
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).move();
        }
        if (myTank != null) myTank.move();

        // 3. 碰撞检测
        collide();

        // 4. 清理死亡子弹
        for (int i = bullets.size() - 1; i >= 0; i--) {
            BaseBullet b = bullets.get(i);
            if (!b.isLiving()) bullets.remove(i);
        }

        // 5. 清理死亡坦克
        for (int i = tanks.size() - 1; i >= 0; i--) {
            Tank t = tanks.get(i);
            if (!t.isLiving()) tanks.remove(i);
        }

        // 6. 清理爆炸动画（已结束的）
        for (int i = explodes.size() - 1; i >= 0; i--) {
            BaseExplode e = explodes.get(i);
            e.update(); // 推进动画帧
            if (e.isFinished()) explodes.remove(i);
        }
    }

    // ---------- 碰撞 ----------
    public void collide() {
        for (int j = 0; j < bullets.size(); j++) {
            BaseBullet b = bullets.get(j);
            if (!b.isLiving()) continue;
            for (int k = 0; k < tanks.size(); k++) {
                Tank t = tanks.get(k);
                if (!t.isLiving()) continue;
                b.collideWith(t);
            }
        }
    }

    // ---------- 绘制 ----------
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        g.drawString("坦克的数量：" + tanks.size(), 10, 80);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(c);

        if (myTank != null && myTank.isLiving()) myTank.paint(g);
        for (int j = 0; j < objects.size(); j++) objects.get(j).paint(g);
        for (int j = 0; j < bullets.size(); j++) bullets.get(j).paint(g);
        for (int j = 0; j < tanks.size(); j++) tanks.get(j).paint(g);
        for (int j = 0; j < explodes.size(); j++) explodes.get(j).paint(g);
    }

    // ---------- 存档 ----------
    public void save() {
        File f = new File("c:/mashibing/tank.data");
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) dir.mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(myTank);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File f = new File("c:/mashibing/tank.data");
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            myTank = (Tank) ois.readObject();
            myTank.model = this;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}