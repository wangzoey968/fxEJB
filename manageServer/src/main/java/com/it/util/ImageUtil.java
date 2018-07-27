package com.it.util;

import javafx.scene.image.Image;

import java.nio.file.Paths;

/**
 * Created by wangzy on 2018/5/25.
 */
public class ImageUtil {

    public static Image getImage(String imgUrl) throws Exception {
        Image image = null;
        try {
            image = new Image(Paths.get(imgUrl).toUri().toURL().toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("图片路径错误");
        }
        return image;
    }

}
