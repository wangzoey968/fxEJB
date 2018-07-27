package com.it.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sun.javafx.print.Units;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 二维码生成工具类
 */
public class QrCodeUtil {

    public static PageLayout createPageLayout(Double width, Double height, Double leftMargin, Double rightMargin, Double topMargin, Double bottomMargin) {
        try {
            Double mm = 1.0 / 25.4 * 72.0;    //毫米与像素的转换关系

            Class pclass = Class.forName("javafx.print.Paper");
            Constructor cons = pclass.getDeclaredConstructor(String.class, double.class, double.class, Units.class);
            cons.setAccessible(true);
            Paper tPaper = (Paper) cons.newInstance("", width, height, Units.MM);

            Class lclass = Class.forName("javafx.print.PageLayout");
            Constructor con = lclass.getDeclaredConstructor(Paper.class, PageOrientation.class,
                    double.class, double.class, double.class, double.class);
            con.setAccessible(true);
            return (PageLayout) con.newInstance(tPaper,
                    PageOrientation.PORTRAIT, leftMargin * mm, rightMargin * mm, topMargin * mm, bottomMargin * mm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //生成二维码
    public static BufferedImage createQrCodeImage(String text) throws WriterException {
        Hashtable ht = new Hashtable();
        ht.put(EncodeHintType.CHARACTER_SET, "utf-8");
        ht.put(EncodeHintType.MARGIN, 0);
        //ht.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = new QRCodeWriter().encode(text.substring(0, text.length()), BarcodeFormat.QR_CODE, 120, 120, ht);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static Path code(String orderId) {
        Path path = null;
        try {
            Hashtable ht = new Hashtable();
            ht.put(EncodeHintType.CHARACTER_SET, "utf-8");
            ht.put(EncodeHintType.MARGIN, 0);
            //ht.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix bitMatrix = new QRCodeWriter().encode(orderId.substring(0, orderId.length()), BarcodeFormat.QR_CODE, 120, 120, ht
            );
            path = Files.createTempFile(UUID.randomUUID().toString(), ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
            path.toFile().deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    @Test
    public void ss() {
        Map<String, String> map = System.getenv();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println(key + "=///=" + map.get(key));
        }
    }

}



