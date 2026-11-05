package com.mashibing.tank.cor;

import com.mashibing.tank.*;

import java.awt.*;

public class BulletWallCollider implements Collider {
    public BulletWallCollider() {}

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Tank t = (Tank) o1;
            Wall w = (Wall) o2;
            if (t.rect.intersects(w.rect)) {
                t.back();
            }
        } else if (o1 instanceof Wall && o2 instanceof Tank) {
            return collide(o2, o1);
        }
        return true;
    }

    @Override
    public boolean collide(Graphics o1, Graphics o2) {
        return false;
    }
}