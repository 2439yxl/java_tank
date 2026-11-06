package com.mashibing.tank.cor;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Bullet && o2 instanceof Tank){
           ((Bullet) o1).collideWith((Tank) o2);
        }else if(o1 instanceof Tank && o2 instanceof Bullet){
           ((Bullet) o2).collideWith((Tank) o1);
        }
        return true;
    }

//    @Override
//    public boolean collide(Graphics o1, Graphics o2) {
//        return false;
//    }
}