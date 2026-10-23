package com.mashibing.tank;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tank {
    private int x, y;
    private Dir dir = Dir.RIGHT;
    private TankFrame tf = null;
    private static final int SPEED = 2;
    private Random random = new Random();
    public static int WIDTH = ResourceMgr.tankD.getWidth();
    public static int HEIGHT = ResourceMgr.tankD.getHeight();
    private Group group = Group.Bad;

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

    public Tank(int x, int y, Dir dir,Group group, TankFrame tf) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.tanks.remove(this);
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
        if(random.nextInt(10)>5) this.fire();
    }

    public void fire() {
        // 子弹左上角 = 坦克中心 - 子弹半宽/半高，使子弹中心落在坦克中心
        int bx = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        tf.bullets.add(new Bullet(bx, by, this.dir,this.group, this.tf));
        ;
    }
    public Group getGoup(){
        return group;
    }
    public void setGoup(Group group){
        this.group = group;
    }
}
