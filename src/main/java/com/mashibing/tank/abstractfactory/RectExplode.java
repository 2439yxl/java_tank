package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.GameModel;
import com.mashibing.tank.ResourceMgr;

import java.awt.*;

public class RectExplode extends BaseExplode {

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private final GameModel model;

    private int x, y;
    private int step = 0;

    @Override
    public void update(){}

    // model 字段由 BaseExplode 提供（protected GameModel model;）
    public RectExplode(int x, int y, GameModel model) {
        this.x = x;
        this.y = y;
        this.model = model;
    }

    public boolean isFinished() {
        return step >= 5;
    }

    /** 只负责绘制，不做任何状态变更/清理。 */
    @Override
    public void paint(Graphics g) {
        if (isFinished()) return;

        Color c = g.getColor();
        g.setColor(Color.RED);

        int size = 10 * step;
        g.fillRect(x - size / 2, y - size / 2, size, size);

        g.setColor(c);

        step++;   // 仅推进动画帧，不删自己
    }
}