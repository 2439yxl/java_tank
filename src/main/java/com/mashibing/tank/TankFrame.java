package com.mashibing.tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

    public static final int GAME_WIDTH = 1200, GAME_HEIGHT = 750;

    private final GameModel model;
    private final TankController controller;
    private Image offScreenImage = null;

    public TankFrame() {
        this.model = GameModel.getInstance();  // 用单例
        this.controller = new TankController(model);

        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        this.addKeyListener(controller);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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