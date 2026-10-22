package test.java.com;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ImageTest {

    @test
    void test() {
//        fail("Not yet implemented");
        try {
        BufferedImage image = ImageIO.read(new File("E:/图/01aae4563735f732f87512f626ac33.jpg@2o.jpg"));
        assertNotNull(image);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
