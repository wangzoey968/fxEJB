package com.it.test;

import com.it.service.Impl.UserServiceImpl;
import com.it.service.Inter.UserServiceInter;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by wangzy on 2018/4/21.
 */
public class ReadXmlTest {

    @Test
    public void ss() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserServiceInter userService = (UserServiceInter) context.getBean("userServiceImpl");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        Socket socket = new Socket();
        socket.setSoTimeout(1000);
        boolean b = socket.isConnected();
        if (b){
            InputStream in = socket.getInputStream();
        }

        ServerSocket server = new ServerSocket();
        Socket incoming = server.accept();
        InputStream inputStream = incoming.getInputStream();
        Scanner i = new Scanner(inputStream);
        while (i.hasNextLine()) {
            System.out.println(i.nextLine());
        }
    }

    //使用SaxReader读取xml文件
    @Test
    public void init() throws Exception {
        SAXReader reader = new SAXReader();
        //注意路径
        Document document = reader.read(new File("src/main/java/com/it/test/readXml/ClientConfig.xml"));
        Element serverElement = document.getRootElement().element("Server");
        String serverAddress = serverElement.attribute("host").getValue();
        Integer serverPort = Integer.valueOf(serverElement.attribute("port").getValue());
        System.out.println(serverAddress + "" + serverPort);
    }

}
