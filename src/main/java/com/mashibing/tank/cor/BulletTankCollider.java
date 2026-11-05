package com.mashibing.tank.cor;

import com.mashibing.tank.GameObject;

import java.awt.*;

public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        return true;
    }

    @Override
    public boolean collide(Graphics o1, Graphics o2) {
        return false;
    }
}