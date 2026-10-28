package com.mashibing.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * View 层：窗口 + 双缓冲渲染。输入事件转发给 Controller。
 */
public class TankFrame extends Frame {

    public static final int GAME_WIDTH = 1200, GAME_HEIGHT = 750;

    private final GameModel model;
    private final TankController controller;
    private Image offScreenImage = null;

    public TankFrame() {
        this.model = new GameModel();
        this.controller = new TankController(model);

        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        // View 只转发按键，不处理逻辑
        this.addKeyListener(controller);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public GameModel getModel() { return model; }
    public TankController getController() { return controller; }

    @Override
    public void paint(Graphics g) {
        model.paint(g);   // 只画
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