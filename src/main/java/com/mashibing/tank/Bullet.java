package com.mashibing.tank;

import java.awt.*;

public class Bullet
{
    private  static final float BULLET_SPEED = 10;
    private static int WIDTH = 20,HEIGHT = 20;
    private int x,y;
    private Dir dir;
    private Boolean Live = true;
    private TankFrame tf;
    public Bullet(int x, int y, Dir dir,TankFrame tf)
    {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }
    public void paint(Graphics g)
    {
        if(!Live){
            tf.bullets.remove(this);
        }

        Color c=g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);

        move();
    }
    private void move() {
        switch (dir) {
            case LEFT:  x -= BULLET_SPEED; break;
            case RIGHT: x += BULLET_SPEED; break;
            case UP:    y -= BULLET_SPEED; break;
            case DOWN:  y += BULLET_SPEED; break;
        }
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH - 1 || y > TankFrame.GAME_HEIGHT - 1) {
            Live = false;

        }
    }
}
