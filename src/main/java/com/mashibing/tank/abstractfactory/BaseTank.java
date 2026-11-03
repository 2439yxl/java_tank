package com.mashibing.tank.abstractfactory;

import java.awt.*;

public abstract class BaseTank {
    public abstract void paint(Graphics g);
    public abstract void move();
    public abstract boolean isLiving();
    public abstract void die();
}