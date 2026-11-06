package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

import java.awt.*;

public abstract class BaseBullet extends GameObject {
    public abstract void paint(Graphics g);
    public abstract void move();
    public abstract boolean isLiving();
    public abstract void collideWith(Tank tank);
}