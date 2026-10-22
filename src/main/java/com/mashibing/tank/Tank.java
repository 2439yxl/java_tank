package com.mashibing.tank;

import java.awt.*;

public class Tank {
    int x;
    int y;
    Dir dir = Dir.RIGHT;
    private TankFrame tf = null;
    private static final int SPEED = 10;
    private boolean moving = false;

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

    public Tank(int x, int y, Dir dir,TankFrame tf) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {
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
    private  void move() {
        if(!moving) return;
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
    }
    public  void fire() {
        tf.bullets.add(new Bullet(this.x, this.y, this.dir,this.tf));
    }
}
