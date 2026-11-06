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
            if (t.rect.intersects(w.rect)) {

                resolve(t,w);
//                t.die();
//                t.back();
//                int eX = t.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
//                int eY = t.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT /2;
//                t. model.add(new Explode(eX,eY,t.gm));
//                return false;
            }
        } else if (o1 instanceof Wall && o2 instanceof Tank) {
            return collide(o2, o1);
        } return true;
    }
    private void resolve(Tank t, Wall w) {
        Rectangle tr = t.rect;
        Rectangle wr = w.rect;

        // 计算 x / y 方向的重叠深度
        int overlapX = Math.min(tr.x + tr.width, wr.x + wr.width) - Math.max(tr.x, wr.x);
        int overlapY = Math.min(tr.y + tr.height, wr.y + wr.height) - Math.max(tr.y, wr.y);

        if (overlapX < overlapY) {
            // 从 x 轴推出（左右方向）
            if (tr.x + tr.width / 2 < wr.x + wr.width / 2) {
                // 坦克中心在墙左边 → 推到墙左侧
                t.setX(wr.x - tr.width);
            } else {
                // 坦克中心在墙右边 → 推到墙右侧
                t.setX(wr.x + wr.width);
            }
        } else {
            // 从 y 轴推出（上下方向）
            if (tr.y + tr.height / 2 < wr.y + wr.height / 2) {
                t.setY(wr.y - tr.height);
            } else {
                t.setY(wr.y + wr.height);
            }
        }
    }

//    @Override
//    public boolean collide(Graphics o1, Graphics o2) {
//        return false;
//    }
}
