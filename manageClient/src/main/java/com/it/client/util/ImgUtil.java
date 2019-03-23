package com.it.client.util;

import javafx.scene.image.Image;

import java.nio.file.Paths;

public class ImgUtil {

    public static Image IMG_ICON = getImage("skin/icon/ICON.png");
    public static Image IMG_ICON_16X16 = getImage("skin/icon/ICON_16x16.png");

    public static Image getImage(String img) {
        Image image = null;
        try {
            image = new Image(Paths.get(System.getProperty("user.dir") + "/manageClient/" + img).toUri().toURL().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

}
