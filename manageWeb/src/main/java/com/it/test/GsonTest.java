package com.it.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.entity.Tb_User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by wangzy on 2018/4/21.
 */
public class GsonTest {

    Tb_User user=null;
    @Before
    public void prep() {
        user = new Tb_User();
        user.setUsername("zhao");
        user.setRemark("11111.jpg");
        user.setAddress("11111");
    }

    @Test
    public void ss() {
        //obj转为json
        Gson gson = new Gson();
        String json = gson.toJson(user);
        System.out.println(json);

        //json转obj
        String j="{\"name\":\"zhao\",\"sex\":true,\"address\":\"上海\",\"photo\":\"11111.jpg\"}";
        Tb_User tb_user = gson.fromJson(j, Tb_User.class);
        System.out.println(tb_user.toString());

        //转化为list
        String jlist = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
        List<Tb_User> list = gson.fromJson(jlist,new TypeToken<List<Tb_User>>() {}.getType());
        System.out.println(list.toString());

        //将json转换成Set
        Set<Tb_User> set = gson.fromJson(jlist,new TypeToken<Set<Tb_User>>() {}.getType());
        System.out.println(set.toString());

    }

}
