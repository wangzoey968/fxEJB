package com.it.controller;

import com.google.gson.Gson;
import com.it.entity.Tb_User;
import com.it.service.Inter.UserServiceInter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by wangzy on 2018/5/26.
 */
@Controller
public class CommonController {

    @Autowired
    UserServiceInter userServiceImpl;

    @Autowired
    Gson gson;

    /**
     * 直接在Controller中写的测试
     */
    @Test
    public void ss() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        UserServiceInter userService = (UserServiceInter) context.getBean("userServiceImpl");
        Tb_User us = userService.getUser(1);
        System.out.println(us.toString());
    }

    @RequestMapping("/views/{path}")
    public String common(@PathVariable("path") String path) {
        System.out.println("请求路径是:"+path);
        return path;
    }

    @RequestMapping("/listUser")
    @ResponseBody
    public Tb_User listUser(HttpServletRequest request, HttpServletResponse response, Integer id) {
        //return "redirect:/jsp/user/aaa.jsp";
        //return "forward:/index.jsp";
        //return "/jsp/user/userInfo";
        Tb_User user = userServiceImpl.getUser(id);
        return user;
    }

    /**
     * 获取硬件的mac地址
     */
    @Test
    public void getMac() throws Exception {
        InetAddress ia = InetAddress.getLocalHost();
        NetworkInterface address = NetworkInterface.getByInetAddress(ia);
        byte[] mac = address.getHardwareAddress();
        StringBuffer sb = new StringBuffer("");

        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            System.out.println("每8位:" + str);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        System.out.println("本机MAC地址:" + sb.toString().toUpperCase());
    }

}
