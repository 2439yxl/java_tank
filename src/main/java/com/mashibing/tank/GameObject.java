package com.mashibing.tank;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
    public int x;
    public int y;

    public abstract void paint(Graphics g);
    public abstract int getWidth();
    public abstract int getHeight();
    private static final long serialVersionUID = 1L;

    public boolean isLiving() {
        return true;
    };
}
