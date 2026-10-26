package com.mashibing.tank;

public class FourDirFireStrategy implements FireStrategy{
    @Override
    public void fire(Tank t){
        int bx = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            new Bullet(bx,by,dir,t.group,t.tf);
        }

        new Bullet(bx, by, t.dir,t.group, t.tf);
    }
}
