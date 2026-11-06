package com.mashibing.tank;

import com.mashibing.tank.abstractfactory.BaseBullet;

import java.awt.*;

public class Bullet extends BaseBullet {

    private static final float BULLET_SPEED = 35;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    public Rectangle rect = new Rectangle(0, 0, WIDTH, HEIGHT);
    private int x, y;
    private Dir dir;
    private boolean living = true;
    private GameModel model;
    public Group group = Group.Bad;

    public Bullet(int x, int y, Dir dir, Group group, GameModel model) {
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
    }

    public boolean isLiving() {
        return living;
    }

    public void paint(Graphics g) {
        switch (dir) {
            case LEFT:  g.drawImage(ResourceMgr.bulletL, this.x, this.y, null); break;
            case RIGHT: g.drawImage(ResourceMgr.bulletR, this.x, this.y, null); break;
            case UP:    g.drawImage(ResourceMgr.bulletU, this.x, this.y, null); break;
            case DOWN:  g.drawImage(ResourceMgr.bulletD, this.x, this.y, null); break;
        }
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public void move() {
        if (!living) return;
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

    public void collideWith(Tank tank) {
        if (!living) return;
        if (this.group == tank.getGroup()) return;
        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
            model.add(new Explode(eX, eY, model));
        }
    }

    public void die() {
        this.living =false;
    }
}