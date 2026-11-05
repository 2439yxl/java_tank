package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.GameObject;

import java.awt.*;

public abstract class BaseTank extends GameObject {
    public abstract void paint(Graphics g);
    public abstract void move();
    public abstract boolean isLiving();
    public abstract void die();
    public abstract int getWidth();
    public abstract int getHeight();
}