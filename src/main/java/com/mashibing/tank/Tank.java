package com.mashibing.tank;

import java.awt.*;
import java.util.Random;

public class Tank {
    int x, y;
    Dir dir = Dir.RIGHT;
    TankFrame tf = null;
    private static final int SPEED = 4;
    private Random random = new Random();
    public static int WIDTH = ResourceMgr.tankD.getWidth();
    public static int HEIGHT = ResourceMgr.tankD.getHeight();
    Group group = Group.Bad;
    Rectangle rect = new Rectangle();

    private boolean moving = true;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    private boolean living = true;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void die() {
        this.living = false;
    }

    FireStrategy fs;

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = this.WIDTH;
        rect.height = this.HEIGHT;
        if (group == Group.Good) {
            String goodFSName = (String) PropertyMgr.get("GoodFS");
            try {
                fs = (FireStrategy) Class.forName(goodFSName)
                        .getDeclaredConstructor()
                        .newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                fs = new FourDirFireStrategy();   // 兜底，避免 fs 为 null 导致 NPE
            }
        } else {
            fs = new DefaultFireStrategy();
        }
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.tanks.remove(this);
            return;
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.tankL, this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankR, this.x, this.y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.tankU, this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankD, this.x, this.y, null);
                break;
        }
//        Color c = g.getColor();
//        g.setColor(Color.RED);
//        g.fillRect(x, y, 40, 40);
//        g.setColor(c);
//        g.drawImage(ResourceMgr.tankL,x,y,null);
        move();
    }

    private void move() {
        if (!moving) return;
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;

        }
        if (this.group == Group.Bad && random.nextInt(100) > 95) this.fire();

        if (this.group == Group.Bad && random.nextInt(100) > 95) randomDir();
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
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        fs.fire(this);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
