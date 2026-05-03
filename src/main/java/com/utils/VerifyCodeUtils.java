package com.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class VerifyCodeUtils {

    private static final char[] CHARS = "2345678ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz".toCharArray();
    private static final Random RANDOM = new Random();

    public static String generateVerifyCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS[RANDOM.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }

    public static boolean validateCaptcha(HttpServletRequest request, String captcha) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        Object sessionCode = session.getAttribute("captcha");
        session.removeAttribute("captcha");
        return captcha != null
                && sessionCode != null
                && captcha.trim().equalsIgnoreCase(String.valueOf(sessionCode));
    }

    public static void outputImage(int width, int height, OutputStream os, String code) throws Exception {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();

        g2.setColor(getRandColor(200, 250));
        g2.fillRect(0, 0, width, height);

        for (int i = 0; i < 6; i++) {
            g2.setColor(getRandColor(100, 200));
            int x1 = RANDOM.nextInt(width);
            int y1 = RANDOM.nextInt(height);
            int x2 = RANDOM.nextInt(width);
            int y2 = RANDOM.nextInt(height);
            g2.drawLine(x1, y1, x2, y2);
        }

        for (int i = 0; i < 40; i++) {
            g2.setColor(getRandColor(100, 200));
            int x = RANDOM.nextInt(width);
            int y = RANDOM.nextInt(height);
            g2.fillOval(x, y, 2, 2);
        }

        String[] fonts = {"Arial", "Times New Roman", "Courier New", "Verdana"};
        int charWidth = width / code.length();
        for (int i = 0; i < code.length(); i++) {
            g2.setColor(getRandColor(20, 100));
            g2.setFont(new Font(fonts[RANDOM.nextInt(fonts.length)], Font.BOLD, 28 + RANDOM.nextInt(8)));

            int degree = RANDOM.nextInt(30) - 15;
            int cx = charWidth * i + charWidth / 2;
            int cy = height / 2;
            g2.rotate(Math.toRadians(degree), cx, cy);
            g2.drawString(String.valueOf(code.charAt(i)), charWidth * i + 10, height / 2 + 12);
            g2.rotate(-Math.toRadians(degree), cx, cy);
        }

        g2.setColor(Color.GRAY);
        g2.drawRect(0, 0, width - 1, height - 1);

        g2.dispose();
        ImageIO.write(image, "jpeg", os);
    }

    private static Color getRandColor(int fc, int bc) {
        int r = fc + RANDOM.nextInt(bc - fc);
        int g = fc + RANDOM.nextInt(bc - fc);
        int b = fc + RANDOM.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
