package com.mashibing.tank;

import com.mashibing.tank.abstractfactory.BaseBullet;

import java.awt.*;

public class Bullet extends BaseBullet {

    private static final float BULLET_SPEED = 20;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    Rectangle rect = new Rectangle(0, 0, WIDTH, HEIGHT);
    private int x, y;
    private Dir dir;
    private boolean living = true;
    private GameModel model;
    private Group group = Group.Bad;

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
        model.bullets.add(this);
    }

    public boolean isLiving() { return living; }

    /** 只负责绘制。 */
    public void paint(Graphics g) {
        if (!living) return;
        switch (dir) {
            case LEFT:  g.drawImage(ResourceMgr.bulletL, this.x, this.y, null); break;
            case RIGHT: g.drawImage(ResourceMgr.bulletR, this.x, this.y, null); break;
            case UP:    g.drawImage(ResourceMgr.bulletU, this.x, this.y, null); break;
            case DOWN:  g.drawImage(ResourceMgr.bulletD, this.x, this.y, null); break;
        }
    }

    /** 由 GameModel.update() 显式调用。 */
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
            model.explodes.add(model.gf.createExplode(x, y, model));
        }
    }

    private void die() { this.living = false; }
}