package com.mashibing.tank;

import com.mashibing.tank.abstractfactory.BaseTank;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

public class Tank extends BaseTank {
    public int x, y;
    public int oldX, oldY;

    public Dir dir = Dir.UP;
    public transient GameModel model;
    private static final int SPEED = 4;
    private transient Random random = new Random();
    public static int WIDTH = ResourceMgr.BadtankD.getWidth();
    public static int HEIGHT = ResourceMgr.BadtankD.getHeight();
    public Group group = Group.Bad;
    public transient Rectangle rect = new Rectangle();
    private boolean moving = true;
    private boolean living = true;

//    transient FireStrategy fs;

    public boolean isLiving() { return living; }
    public boolean isMoving() { return moving; }
    public void setMoving(boolean moving) { this.moving = moving; }
    public Dir getDir() { return dir; }
    public void setDir(Dir dir) { this.dir = dir; }
    public void setX(int x) { this.x = x; this.rect.x = x; }
    public void setY(int y) { this.y = y; this.rect.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void die() { this.living = false; }

    public Tank(int x, int y, Dir dir, Group group, GameModel model) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.model = model;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = this.WIDTH;
        rect.height = this.HEIGHT;
        model.add(this);
//        if (group == Group.Good) {
//            String goodFSName = (String) PropertyMgr.get("GoodFS");
//            try {
//                fs = (FireStrategy) Class.forName(goodFSName)
//                        .getDeclaredConstructor().newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//                fs = new DefaultFireStrategy();
            }
//        } else {
//            fs = new DefaultFireStrategy();
//        }

//    }

//    @Override
//    public int getWidth() { return WIDTH; }
//    @Override
//    public int getHeight() { return HEIGHT; }

    public void paint(Graphics g) {
        if (!living) return;
        if (group == Group.Good) {
            switch (dir) {
                case LEFT:  g.drawImage(ResourceMgr.GoodtankL, x, y, null); break;
                case RIGHT: g.drawImage(ResourceMgr.GoodtankR, x, y, null); break;
                case UP:    g.drawImage(ResourceMgr.GoodtankU, x, y, null); break;
                case DOWN:  g.drawImage(ResourceMgr.GoodtankD, x, y, null); break;
            }
        } else {
            switch (dir) {
                case LEFT:  g.drawImage(ResourceMgr.BadtankL, x, y, null); break;
                case RIGHT: g.drawImage(ResourceMgr.BadtankR, x, y, null); break;
                case UP:    g.drawImage(ResourceMgr.BadtankU, x, y, null); break;
                case DOWN:  g.drawImage(ResourceMgr.BadtankD, x, y, null); break;
            }
        }
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    public void back() {
        x = oldX;
        y = oldY;
        rect.x = this.x;
        rect.y = this.y;
    }

    public void move() {
        oldX = x;
        oldY = y;
        if (!living || !moving) return;
        switch (dir) {
            case LEFT:  x -= SPEED; break;
            case UP:    y -= SPEED; break;
            case RIGHT: x += SPEED; break;
            case DOWN:  y += SPEED; break;
        }
        if (this.group == Group.Bad && random.nextInt(100) > 96) this.fire();
        if (this.group == Group.Bad && random.nextInt(100) > 97) randomDir();
        boundsCheck();
        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundsCheck() {
        if (x < 2) x = 2;
        if (y < 20) y = 20;
        if (x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
    }

    private void randomDir() {
        this.dir = com.mashibing.tank.Dir.values()[random.nextInt(4)];
    }

//    public void fire() {
//        if (!living)
//            return;
//        fs.fire(this);
//    }
private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
    in.defaultReadObject();          // 恢复普通字段
    this.random = new Random();      // 重建 transient 字段
    this.rect = new Rectangle();     // 重建 Rectangle
    this.rect.x = this.x;
    this.rect.y = this.y;
    this.rect.width = WIDTH;
    this.rect.height = HEIGHT;
    // model 无法在这里恢复，需要外部 load() 里注入
}

    // 供 load() 调用，重建 rect 并注入 model
    public void afterLoad(GameModel model) {
        this.model = model;
        if (this.rect == null) this.rect = new Rectangle();
        this.rect.x = this.x;
        this.rect.y = this.y;
        this.rect.width = WIDTH;
        this.rect.height = HEIGHT;
    }

    public void fire() {
        if (!living) return;
        new Bullet(x + Tank.WIDTH / 2 - Bullet.WIDTH / 2,
                y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2,
                dir, group, model);
    }
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
}