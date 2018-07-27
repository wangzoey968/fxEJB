package com.it.test;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.Comparator;

/**
 * Created by wangzy on 2018/4/29.
 */
public class FileTest {

    @Test
    public void readFileWithBuffer() throws Exception {
        File file = new File("C:/Users/Administrator/Desktop/temp.txt");
        FileReader reader = new FileReader(file);
        BufferedReader buffer = new BufferedReader(reader);
        String line = null;
        while ((line = buffer.readLine())!= null) {
            System.out.println(line);
        }
        reader.close();
        buffer.close();
    }


    @Test
    public void readerSocket() throws IOException {
        Socket socket = new Socket("127.0.0.1", 5000);
        InputStreamReader stream = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(stream);
        String line = reader.readLine();
        reader.close();
        stream.close();
        //比较
        new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
    }

    @Test
    public void writeDataToSocket() throws IOException {
        Socket socket = new Socket("127.0.0.1", 5000);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println("aaaaaaaaaaaaaaaa");
    }

}
