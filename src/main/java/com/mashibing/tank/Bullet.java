package com.mashibing.tank;

import java.awt.*;

public class Bullet
{
    private  static final float BULLET_SPEED = 20;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT= ResourceMgr.bulletD.getHeight();
    Rectangle rect = new Rectangle(0,0,WIDTH,HEIGHT);
    private int x,y;
    private Dir dir;
    private Boolean living = true;
    private Boolean isliving = living;
    private TankFrame tf;
    private Group group = Group.Bad;
    public Bullet(int x, int y, Dir dir,Group group, TankFrame tf)
    {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = this.WIDTH;
        rect.height = this.HEIGHT;

    }
    public void paint(Graphics g)
    {
        if(!living){
            tf.bullets.remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, this.x, this.y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, this.x, this.y, null);
                break;
        }

        move();
    }
    private void move() {
        switch (dir) {
            case LEFT:  x -= BULLET_SPEED; break;
            case RIGHT: x += BULLET_SPEED; break;
            case UP:    y -= BULLET_SPEED; break;
            case DOWN:  y += BULLET_SPEED; break;
        }
        rect.x = this.x;
        rect.y = this.y;

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH - 1 || y > TankFrame.GAME_HEIGHT - 1) {
            living = false;

        }
    }
    public  void collideWith(Tank tank)
    {
        if(this.group ==tank.getGroup())return;
        //一个rect记录子弹位置
        Rectangle rect1 = new Rectangle(this.x, this.y, this.WIDTH, this.HEIGHT);
        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            tf.explodes.add(new Explode(x,y,tf));
        }

    }
    private void die(){
        this.living = false;
    }
}
