package com.mashibing.tank.cor;

import com.mashibing.tank.*;

import java.awt.*;

public class BulletWallCollder implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Wall) {
            Bullet b = (Bullet) o1;
            Wall w = (Wall) o2;

//            if(b.group == t.getGroup()) return true;
            if (b.rect.intersects(t.rect)) {
//                t.die();
                b.die();
//                int eX = t.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
//                int eY = t.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT /2;
//                t. model.add(new Explode(eX,eY,t.gm));
//                return false;
            }
        } else if (o1 instanceof Wall && o2 instanceof Bullet) {
            return collide(o2, o1);
        } return true;
    }

    @Override
    public boolean collide(Graphics o1, Graphics o2) {
        return false;
    }
}
