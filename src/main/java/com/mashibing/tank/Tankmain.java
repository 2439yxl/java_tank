package com.mashibing.tank;

public class Tankmain {
    public static void main(String[] args) {
        TankFrame tf = new TankFrame();
        GameModel model = tf.getModel();

        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
        for (int i = 0; i < initTankCount; i++) {
            model.tanks.add(new Tank(50 + i * 80, 200, Dir.DOWN, Group.Bad, model));
        }

        while (true) {
            try {
                Thread.sleep(50);
                tf.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            model.update();   // 先推进逻辑
            tf.repaint();     // 再刷新视图
        }
    }
}