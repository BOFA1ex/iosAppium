package com.bofa.appium.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.util
 * @date 2018/12/17
 */
public class ImageUtils {

    private static final Logger log  = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 遍历图片的像素点，裁剪正确答案的图片
     *
     * @param filePath
     */
    public static Object getImagePixel(String filePath) {
        int[] rgb = new int[3];
        File file = new File(filePath);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 100; j < height; j++) {
                // 下面三行代码将一个数字转换为RGB数字
                int pixel = bi.getRGB(i, j);
                rgb[0] = (pixel >> 16) & 0xFF;
                rgb[1] = (pixel >> 8) & 0xFF;
                rgb[2] = (pixel & 0xFF);
//                log.error("(" + i + ", " + j + ")" + "\nR : " + rgb[0] + " G : " + rgb[1] + " B : " + rgb[2]);
                boolean b1 = (rgb[1] - rgb[0] > 20);
                boolean b2 = (rgb[1] - rgb[2] > 20);
                if (b1 && b2) {
                    try {
                        log.error("find green color : " + "(" + i + ", " + j + ")" + "\nR : " + rgb[0] + "G : " + rgb[1] + " B : " + rgb[2]);
                        BufferedImage subimage = ImageIO.read(new FileInputStream(filePath)).getSubimage(i, j, width - i, height - j);
                        ImageIO.write(subimage, "png", new File(filePath));
                        return OCRUtils.requestOCR();
                    } catch (IOException e) {
                        log.error(e.getLocalizedMessage());
                    }
                }
            }
        }
        return null;
    }
}
