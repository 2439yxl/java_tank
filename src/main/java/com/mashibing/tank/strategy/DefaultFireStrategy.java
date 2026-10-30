package com.mashibing.tank.strategy;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Decorator.RectDecorator;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Tank;

public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank t){

        // 子弹左上角 = 坦克中心 - 子弹半宽/半高，使子弹中心落在坦克中心
        int bx = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        GameModel.getInstance().add(
                new RectDecorator(
                        new TainDeractor(
                        new Bullet(bx,by,t.dir,t.group))));

        new Bullet(bx, by, t.dir,t.group, t.model);
    }

}
