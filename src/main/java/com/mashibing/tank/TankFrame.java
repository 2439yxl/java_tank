package com.mashibing.tank;

import com.mashibing.tank.net.TankClientHolder;
import com.mashibing.tank.net.TankMsg;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

    public static final int GAME_WIDTH = 1200, GAME_HEIGHT = 750;

    private final GameModel model;
    private final TankController controller;
    private Image offScreenImage = null;


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Tank my = GameModel.getInstance().myTank;
        if (my == null) return;

        Dir oldDir = my.getDir();
        switch (key) {
            case KeyEvent.VK_LEFT:  my.setDir(Dir.LEFT);  my.setMoving(true); break;
            case KeyEvent.VK_RIGHT: my.setDir(Dir.RIGHT); my.setMoving(true); break;
            case KeyEvent.VK_UP:    my.setDir(Dir.UP);    my.setMoving(true); break;
            case KeyEvent.VK_DOWN:  my.setDir(Dir.DOWN);  my.setMoving(true); break;
            case KeyEvent.VK_SPACE:
                my.fire();  // fire 内部会发 BULLET_FIRE 消息，见下
                break;
        }

        // 方向变了才发（减少流量）
        if (my.getDir() != oldDir) {
            TankClientHolder.client.send(
                    TankMsg.dirChange(my.getPlayerId(), my.getDir().name()));
        }
    }

    public TankFrame() {
        this.model = GameModel.getInstance();  // 用单例
        this.controller = new TankController(model,this);

        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        this.addKeyListener(controller);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Tank my = GameModel.getInstance().myTank;
                if (my != null && my.getPlayerId() != null) {
                    TankClientHolder.client.send(TankMsg.leave(my.getPlayerId()));
                }
                TankClientHolder.client.close();   // 关闭 channel
                System.exit(0);
            }
        });
    }

    public GameModel getModel() {
        return model;
    }

    public TankController getController() {
        return controller;
    }

    @Override
    public void paint(Graphics g) {
        model.paint(g);
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

}