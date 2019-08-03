package com.it.client;

import com.it.client.util.HttpRequestUtils;
import org.junit.Test;

public class MyTest {

    @Test
    public void sss(){
        String s = HttpRequestUtils.sendGet("http://www.baidu.com");
        System.out.println(s);


    }

}
