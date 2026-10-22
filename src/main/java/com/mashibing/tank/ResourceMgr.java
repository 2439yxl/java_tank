package com.mashibing.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ResourceMgr {
    public static BufferedImage tankL,tankR,tankU,tankD;

    static {
        try {
            tankL = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getResourceAsStream("/tankL.gif")));
            tankR = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getResourceAsStream("/tankR.gif")));
            tankU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getResourceAsStream("/tankU.gif")));
            tankD = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getResourceAsStream("/tankD.gif")));
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

    static {
        tankL = read("/tankL.gif");
        tankR = read("/tankR.gif");
        tankU = read("/tankU.gif");
        tankD = read("/tankD.gif");
    }
}
