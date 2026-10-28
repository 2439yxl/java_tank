package com.mashibing.tank;

import com.mashibing.tank.abstractfactory.BaseExplode;

import java.awt.*;

public class Explode extends BaseExplode {

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int x, y;
    GameModel model = null;
    private int step = 0;

    public Explode(int x, int y, GameModel model) {
        this.x = x;
        this.y = y;
        this.model = model;
    }

    public boolean isFinished() {
        return step >= ResourceMgr.explodes.length;
    }

    @Override
    public void paint(Graphics g) {
        if (isFinished()) return;
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
    }
}