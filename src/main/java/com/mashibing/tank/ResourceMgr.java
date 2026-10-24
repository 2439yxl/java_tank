package com.mashibing.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ResourceMgr {
    public static BufferedImage tankL,tankR,tankU,tankD;
    public static BufferedImage bulletL,bulletR,bulletU,bulletD;
    public  static  BufferedImage[] explodes = new BufferedImage[16];
    static {
        try {
            tankU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getResourceAsStream("/BadTank1.png")));
            tankD = ImageUtil.rotateImage(tankU, 180);
            tankL = ImageUtil.rotateImage(tankU, -90);
            tankR = ImageUtil.rotateImage(tankU, 90);

            bulletD = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getResourceAsStream("/bulletD.gif")));
            bulletL = ImageUtil.rotateImage(bulletD, 90);
            bulletR = ImageUtil.rotateImage(bulletD, -90);
            bulletU = ImageUtil.rotateImage(bulletD, 180);


            for(int i=0;i<16;i++){
                explodes[i] = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getResourceAsStream("/e"+(i+1)+".gif")));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage read(String path) {
        var in = ResourceMgr.class.getResourceAsStream(path);
        if (in == null) {
            throw new RuntimeException("classpath 根目录找不到: " + path);
        }
        try (in) {
            return ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException("读取图片失败: " + path, e);
        }
    }

//    static {
//        tankL = read("/images/tankL.gif");
//        tankR = read("/images/tankR.gif");
//        tankU = read("/images/tankU.gif");
//        tankD = read("/images/tankD.gif");
//    }
}
