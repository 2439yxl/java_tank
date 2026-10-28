package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Explode;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Group;
import com.mashibing.tank.Tank;

public abstract class DefaultFactory extends GameFactory {

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, GameModel model) {
        return new Tank(x, y, dir, group, model);
    }

    @Override
    public BaseExplode createExplode(int x, int y, GameModel model) {
        return new Explode(x, y, model);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel model) {
        // 图片版子弹由 DefaultFireStrategy 直接 new Bullet(...) 创建，工厂这里不负责
        return null;
    }
}