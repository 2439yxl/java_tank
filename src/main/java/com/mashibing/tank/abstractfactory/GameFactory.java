package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.Dir;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Group;

public abstract class GameFactory {

    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, GameModel model);

    public abstract BaseExplode createExplode(int x, int y, GameModel model);

    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel model);
}