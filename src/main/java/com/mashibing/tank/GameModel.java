package com.mashibing.tank;

import com.mashibing.tank.abstractfactory.BaseBullet;
import com.mashibing.tank.abstractfactory.BaseExplode;
import com.mashibing.tank.abstractfactory.GameFactory;
import com.mashibing.tank.abstractfactory.RectFactory;
import com.mashibing.tank.cor.ColliderChain;
import com.mashibing.tank.cor.DefaultColliderChain;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    public Tank myTank;
    private static final GameModel INSTANCE = new GameModel();
    private ColliderChain colliderChain = new DefaultColliderChain();

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
        myTank = new Tank(TankFrame.GAME_WIDTH/2, TankFrame.GAME_HEIGHT-2*Tank.HEIGHT, Dir.DOWN, Group.Good, this);
        add(myTank);
        int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
        for (int i = 0; i < initTankCount; i++) {
            new Tank(Tank.WIDTH+i*Tank.WIDTH,Tank.HEIGHT, Dir.DOWN, Group.Bad, this);
        }
        add(new Wall(TankFrame.GAME_WIDTH/2-400, TankFrame.GAME_HEIGHT/2-200, 300, 50));
        add(new Wall(TankFrame.GAME_WIDTH/2+100, TankFrame.GAME_HEIGHT/2-200, 300, 50));
        add(new Wall(TankFrame.GAME_WIDTH/2-200, TankFrame.GAME_HEIGHT/2-50, 50, 300));
        add(new Wall(TankFrame.GAME_WIDTH/2+150, TankFrame.GAME_HEIGHT/2-50, 50, 300));
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
        objects.add(bullet);
    }

    public void add(Tank tank) {
        tanks.add(tank);
        objects.add(tank);
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
        for (BaseBullet b:bullets) {
            b.move();
        }
        // 2. 移动坦克
        for (Tank t:tanks) {
            t.move();
        }

        // 3. 碰撞检测
        collide();

        // 4. 清理死亡子弹
        for (int i = bullets.size() - 1; i >= 0; i--) {
            BaseBullet b = bullets.get(i);
            if (!b.isLiving()) {
                bullets.remove(i);
                objects.remove(b);
            }
        }

        // 5. 清理死亡坦克
        for (int i = tanks.size() - 1; i >= 0; i--) {
            Tank t = tanks.get(i);
            if (!t.isLiving()){
                tanks.remove(i);
                objects.remove(t);
            }

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
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o1 = objects.get(i);
                GameObject o2 = objects.get(j);
                if (!o1.isLiving() || !o2.isLiving()) continue;
                colliderChain.collide(o1, o2);
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

//        if (myTank != null && myTank.isLiving()) myTank.paint(g);
        for (int j = 0; j < objects.size(); j++) objects.get(j).paint(g);
//        for (int j = 0; j < bullets.size(); j++) bullets.get(j).paint(g);
//        for (int j = 0; j < tanks.size(); j++) tanks.get(j).paint(g);
        for (int j = 0; j < explodes.size(); j++) explodes.get(j).paint(g);
    }

    // ---------- 存档 ----------
    public void save() {
        File f = new File("D:/java_load/java_load.data");
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) dir.mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(new ArrayList<>(tanks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void load() {
        File f = new File("D:/java_load/java_load.data");
        if (!f.exists()) { System.out.println("存档不存在"); return; }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            List<Tank> loaded = (List<Tank>) ois.readObject();
            // 1. 先把旧坦克从 objects 里清掉（tanks 里的旧对象）
            for (Tank old : tanks) {
                objects.remove(old);
            }
            // 2. 替换列表
            tanks.clear();
            tanks.addAll(loaded);
            // 3. 每个坦克重建 transient 字段 + 注入 model
            for (Tank t : tanks) {
                t.afterLoad(this);
            }
            // 4. 新坦克加入 objects（绘制要靠它）
            for (Tank t : tanks) {
                objects.add(t);
            }
            // 5. 重新绑定 myTank（如果存档里有玩家坦克）
            myTank = null;
            for (Tank t : tanks) {
                if (t.group == Group.Good) { myTank = t; break; }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("读取异常:");
            e.printStackTrace();
        }
    }
}