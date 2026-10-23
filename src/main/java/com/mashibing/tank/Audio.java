//package com.mashibing.tank;
//
//public class Audio extends Thread {
//    @Override
//    public void run() {
//        try {
//            byte[] b = new byte[1024];
//            int len = 0;
//            sourceDataLine.open(audioFormat, 1024);
//            sourceDataLine.start();
//            while ((len = audioInputStream.read(b)) > 0) {
//                sourceDataLine.write(b, 0, len);
//            }
//            audioInputStream.close();
//            sourceDataLine.drain();
//            sourceDataLine.close();
//        }
//        catech(Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
