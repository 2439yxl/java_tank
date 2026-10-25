package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    Tank myTank = new Tank(400, 400, Dir.DOWN, Group.Good,this);
    List<Bullet> bullets = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();
    List<Explode>  explodes = new ArrayList<>();
//    Explode e = new Explode(100,100,this);

    Bullet b = new Bullet(200, 200, Dir.DOWN, Group.Good,this);
    static final int GAME_WIDTH = 1000, GAME_HEIGHT = 750;

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);


        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    Image OffScreenImage = null;

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        g.drawString("坦克的数量：" + tanks.size(), 10, 80);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);

        g.setColor(c);

        myTank.paint(g);
        for (int j = 0; j < bullets.size(); j++) {
            bullets.get(j).paint(g);
        }
        for (int j = 0; j < tanks.size(); j++) {
            tanks.get(j).paint(g);
        }
        for (int j = 0; j < explodes.size(); j++) {
            explodes.get(j).paint(g);
        }
        for (int j = 0; j < bullets.size(); j++) {
            for(int k = 0; k < tanks.size(); k++){
                bullets.get(j).collideWith(tanks.get(k));
            }
        }
//        e.paint(g);

    }

    class MyKeyListener extends KeyAdapter {

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        public void move() {
        }

        public void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) myTank.setMoving(false);
            else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Dir.LEFT);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bU) myTank.setDir(Dir.UP);
                if (bD) myTank.setDir(Dir.DOWN);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;

                case KeyEvent.VK_SPACE:
                    myTank.fire();
                    break;

                default:
                    break;
            }
//            System.out.println("keyPressed");
//            x += 100;
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                default:
                    break;

            }
            setMainTankDir();
        }
    }

    @Override
    public void update(Graphics g) {
        if (OffScreenImage == null) {
            OffScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = OffScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(OffScreenImage, 0, 0, null);
    }


}