package com.mashibing.tank.Decorator;

import com.mashibing.tank.GameObject;

import java.awt.*;

import static com.mashibing.tank.Bullet.HEIGHT;
import static com.mashibing.tank.Bullet.WIDTH;

public class GODecorator extends GameObject{
    GameObject go;

    public GODecorator(GameObject go) {
        this.go = go;
    }

    @Override
    public void paint(Graphics g) {
        go.paint(g);
    }
    @Override
    public int getWidth(){
        return WIDTH;
    }
    @Override
    public int getHeight(){
        return HEIGHT;
    }
}
