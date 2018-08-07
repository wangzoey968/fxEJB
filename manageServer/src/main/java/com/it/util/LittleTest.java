package com.it.util;

import com.it.api.table.user.Tb_Role;
import com.it.util.ftpServer.FtpConfigService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.junit.Test;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by wangzy on 2018/8/6.
 */
public class LittleTest {

    @Test
    public void ss() {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(String.format("%.2f%%",0.56f));

        this.getClass().getResourceAsStream("");//返回的是inputstream

        //ok
        URL url = this.getClass().getResource("");//url
        System.out.println(url);

        URL resource = Class.class.getResource("");//返回的是当前Class这个类所在包开始的为置
        System.out.println(resource);

        //ok
        URL res = Class.class.getResource("/");//返回的是classpath的位置
        System.out.println(res);

        //ok
        URL r = this.getClass().getClassLoader().getResource("");//返回的是classpath的位置
        System.out.println(r);

        URL u = this.getClass().getClassLoader().getResource("/");//错误的!!
        System.out.println(u);
    }

    @Test
    public void sss() throws Exception {
        /*SAXReader reader = new SAXReader();
        URI uri = FtpConfigService.class.getClass().getClassLoader().getResource("ConfigServer.xml").toURI();
        Document document = reader.read(Paths.get(uri).toFile());
        //获取根节点元素对象
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        for (Element s : list) {
            System.out.println(s.getName() + "/" + s.getText());
        }*/
        ArrayList<String> list_1 = new ArrayList<>();
        list_1.add("111");
        list_1.add("2222");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("1111111111");
        list2.add("22222222222");
        list_1.addAll(list2);
        for (String s : list_1) {
            System.out.println(s);
        }
    }

    @Test
    public void ssssss(){

    }

}
