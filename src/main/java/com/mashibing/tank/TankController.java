package com.mashibing.tank;

import com.mashibing.tank.net.TankClientHolder;
import com.mashibing.tank.net.TankMsg;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankController extends KeyAdapter {
    private final GameModel model;
    private final TankFrame frame;
    private boolean bL = false, bU = false, bR = false, bD = false;

    public TankController(GameModel model, TankFrame frame) {
        this.model = model;
        this.frame = frame;
    }

    private void setMainTankDir() {
        Tank myTank = model.myTank;
        Dir oldDir = myTank.getDir();
        if (myTank == null) return;
        if (!bL && !bU && !bR && !bD) {
            myTank.setMoving(false);
        } else {
            myTank.setMoving(true);
            if (bL) myTank.setDir(Dir.LEFT);
            if (bR) myTank.setDir(Dir.RIGHT);
            if (bU) myTank.setDir(Dir.UP);
            if (bD) myTank.setDir(Dir.DOWN);
        }
        if (myTank.getDir() != oldDir && myTank.getPlayerId() != null) {
            TankClientHolder.client.send(
                    TankMsg.dirChange(myTank.getPlayerId(), myTank.getDir().name()));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  bL = true; break;
            case KeyEvent.VK_RIGHT: bR = true; break;
            case KeyEvent.VK_UP:    bU = true; break;
            case KeyEvent.VK_DOWN:  bD = true; break;
            case KeyEvent.VK_SPACE: model.myTank.fire(); break;
            case KeyEvent.VK_S:     model.save(); break;
            case KeyEvent.VK_L:     model.load();
            frame.repaint();
            break;
            default: break;
        }
        setMainTankDir();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  bL = false; break;
            case KeyEvent.VK_RIGHT: bR = false; break;
            case KeyEvent.VK_UP:    bU = false; break;
            case KeyEvent.VK_DOWN:  bD = false; break;
            default: break;
        }
        setMainTankDir();
    }
}