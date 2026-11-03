package com.mashibing.tank.Observer;

import java.io.Serializable;

public interface TankFireObserver extends Serializable {
    <TankFireEvent> void actionOnFire(TankFireEvent e);
}
