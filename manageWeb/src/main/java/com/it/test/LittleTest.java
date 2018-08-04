package com.it.test;

import com.it.entity.Tb_Auth;
import com.it.util.WebBack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by wangzy on 2018/5/5.
 */
public class LittleTest {

    static Logger logger = Logger.getLogger(LittleTest.class);

    @Test
    public void s1() throws Exception {
        logger.setLevel(Level.ERROR);
        logger.error("错误");
        Locale china = Locale.CHINA;
        Collator collator = Collator.getInstance(china);
        if (collator.compare("a", "A") < 0) {
            //类加载器
            Thread thread = Thread.currentThread();
            ClassLoader loader = thread.getContextClassLoader();
            Class user = loader.loadClass("com.it.model.Tb_User");
        }

        //比较,通过使用collator,locale
        new Comparator<Locale>() {
            Collator c = Collator.getInstance(Locale.getDefault());

            @Override
            public int compare(Locale o1, Locale o2) {
                return c.compare(o1, o2);
            }
        };


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ANY, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Platform.setImplicitExit(false);
                        try {
                            Stage.class.newInstance().hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alert.setTitle("this is title");
            }
        });
        Button button = new Button("xinjian");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();
            }
        });
    }

    @Test
    public void sss() {
        //Java中所有方法的参数的传递都是“值传递”
        //所有的Java对象都是在堆中
        String a = "s";
        String b = "s";
        System.out.println(a == b);//true

        String c = new String("aa");
        String d = new String("aa");
        System.out.println(c == d);//false

        String s = "ss";
        String s2 = new String("ss");
        System.out.println(s == s2);//false
        System.out.println(s.equals(s2));//true

    }

    @Test
    public void ss() throws IOException {
        System.out.println(System.getProperty("user.dir"));

        //使用command调用
        Runtime.getRuntime().exec("cmd /c " + "calc");

        System.out.println(WebBack.class.getName());//com.it.util

    }

    @Test
    public void ssss() {
        ArrayList<Tb_Auth> list1 = new ArrayList<>();
        Tb_Auth auth = new Tb_Auth();
        auth.setAuthname("1122");
        auth.setId(1);
        auth.setNote("11");
        list1.add(auth);

        ArrayList<Tb_Auth> list2 = new ArrayList<>();
        Tb_Auth auth1 = new Tb_Auth();
        auth1.setAuthname("1122");
        auth1.setId(1);
        auth1.setNote("11");
        list2.add(auth1);

        System.out.println(list2.contains(auth));
    }

    @Test
    public void s2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(1532509295010L));
    }

    @Test
    public void ss11(){
        System.out.println(System.getProperty("webModuleRootDir")+"src/main/java/com/it/util/ftpServer/ConfigServer.xml");
    }

}
