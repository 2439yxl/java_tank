package com.mashibing.tank;

import java.awt.*;

public abstract class GameObject {
    public int x;
    public int y;

    public abstract void paint(Graphics g);
    public abstract void getWidth();
    public abstract void getHeight();

}
