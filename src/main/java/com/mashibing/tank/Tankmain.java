package com.mashibing.tank;

import com.mashibing.tank.net.TankClientHolder;

public class Tankmain {
    public static void main(String[] args) {
        TankFrame tf = new TankFrame();
        GameModel model = tf.getModel();
        TankClientHolder.client.connect();

        while (true) {
            try {
                Thread.sleep(50);
                model.update();   // 先推进逻辑
                tf.repaint();     // 再刷新视图
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch (Exception e) {   // ✅ 捕获所有异常，别让 while 退出
                e.printStackTrace();
                // 不 break，继续循环
            }
        }
    }
}