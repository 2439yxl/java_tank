package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.Dir;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Group;

public class RectFactory extends GameFactory {   // ★ 不要写 abstract

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, GameModel model) {
        return null;
    }

    @Override
    public BaseExplode createExplode(int x, int y, GameModel model) {
        return new RectExplode(x, y, model);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel model) {
        return new RectBullet(x, y, dir, group, model);
    }
}