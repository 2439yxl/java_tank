package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.GameModel;
import com.mashibing.tank.Tank;

import java.awt.*;

public abstract class BaseBullet {
    public abstract void paint(Graphics g);
    public abstract void collideWith(Tank tank);
    protected GameModel model;

    public void move() {
    }

    public boolean isLiving() {
    return false;}
}
