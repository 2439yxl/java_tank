package com.mashibing.tank;

public class Tankmain {
    public static void main(String[] args) {
        TankFrame tf = new TankFrame();
        GameModel model = tf.getModel();

        while (true) {
            try {
                Thread.sleep(50);
                model.update();   // 先推进逻辑
                tf.repaint();     // 再刷新视图
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}