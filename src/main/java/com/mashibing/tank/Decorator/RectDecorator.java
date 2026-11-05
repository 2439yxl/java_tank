package com.mashibing.tank.Decorator;

import com.mashibing.tank.GameObject;

import java.awt.*;

public class RectDecorator extends GODecorator{
    private int width = super.getWidth();
    private int height = super.getHeight();
    public RectDecorator(GameObject go) {
        super(go);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.drawRect(super.go.x,super.go.y,width+2,height+2);
        g.setColor(c);
    }
    @Override
    public int getWidth(){
        return super.go.getWidth();
    }
    @Override
    public int getHeight(){
        return super.go.getHeight();
    }
}
