package com.mashibing.tank.abstractfactory;

import java.awt.*;

public abstract class BaseExplode {
    public abstract void paint(Graphics g);
    public abstract void update();
    public abstract boolean isFinished();
}