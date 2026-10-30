package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.Dir;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Group;
import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;

import java.awt.*;

public class RectBullet extends BaseBullet {

    private static final float BULLET_SPEED = 20;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();

    private Rectangle rect = new Rectangle(0, 0, WIDTH, HEIGHT);
    private int x, y;
    private Dir dir;
    private boolean living = true;
    private Group group = Group.Bad;

    // model 字段由 BaseBullet 提供（protected GameModel model;）
    public RectBullet(int x, int y, Dir dir, Group group, GameModel model) {
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

    public boolean isLiving() {
        return living;
    }

    /** 只负责绘制，不做任何状态变更。 */
    @Override
    public void paint(Graphics g) {
        if (!living) return;
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 20, 20);
        g.setColor(c);
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

    @Override
    public boolean collideWith(Tank tank) {
        if (!living) return false;
        if (this.group == tank.getGroup()) return false;
        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            model.explodes.add(model.gf.createExplode(x, y, model));
        }
        return false;
    }

    private void die() {
        this.living = false;
    }
}