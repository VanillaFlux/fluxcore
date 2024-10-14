package me.quickscythe.fluxcore.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtils {

    public static BufferedImage resize(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage resizedImg = new BufferedImage(32, 32, img.getType());
        Graphics2D g = resizedImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, 32, 32, 0, 0, w, h, null);
        g.dispose();
        return resizedImg;
    }


}
