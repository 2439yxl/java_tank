package com.mashibing.tank.cor;

import com.mashibing.tank.*;

import java.awt.*;

public class TankWallCollider implements Collider {
    public TankWallCollider() {}
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Tank t = (Tank) o1;
            Wall w = (Wall) o2;

//            if(b.group == t.getGroup()) return true;
            if (t.rect.intersects(t.rect)) {
//                t.die();
                t.back();
//                int eX = t.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
//                int eY = t.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT /2;
//                t. model.add(new Explode(eX,eY,t.gm));
//                return false;
            }
        } else if (o1 instanceof Wall && o2 instanceof Tank) {
            return collide(o2, o1);
        } return true;
    }

    @Override
    public boolean collide(Graphics o1, Graphics o2) {
        return false;
    }
}
