package com.mashibing.tank;

public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank t){

        // 子弹左上角 = 坦克中心 - 子弹半宽/半高，使子弹中心落在坦克中心
        int bx = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        new Bullet(bx, by, t.dir,t.group, t.tf);
    }

}
