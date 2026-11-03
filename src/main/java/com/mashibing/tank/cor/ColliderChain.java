package com.mashibing.tank.cor;

import com.mashibing.tank.GameObject;

import java.util.LinkedList;
import java.util.List;

public abstract class ColliderChain implements Collider {
    private List<Collider> colliders = new LinkedList<>();

    public void add(Collider c) {
        colliders.add(c);
    }

    public ColliderChain() {
        add(new BulletTankCollider());
        add(new TankTankCollider());
        add(new BulletWallCollder());
        add(new TankWallCollder());
    }

    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
           if(!colliders.get(i).collide(o1, o2)){
               return false;
           }
        }
        return true;
    }
}
