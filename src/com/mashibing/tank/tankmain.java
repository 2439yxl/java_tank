package com.mashibing.tank;

public class tankmain {
    public static void main(String[] args) {

        TankFrame tf = new TankFrame();

        while(true){
            try {
                Thread.sleep(50);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            tf.repaint();
        }
    }
}