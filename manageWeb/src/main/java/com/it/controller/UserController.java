package com.it.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.it.entity.Tb_User;
import com.it.service.Inter.UserServiceInter;
import com.it.util.WebBack;
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
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by wangzy on 2018/5/22.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceInter userServiceImpl;

    @Autowired
    Gson gson;

    /**
     * 请求测试
     */
    @RequestMapping("/user")
    public String test() {
        System.out.println("/user请求");
        return "/user";
    }

    //此方法,效果等同于下面的responseBody注解方法
    @RequestMapping("/getUser")
    public void getUser(HttpServletRequest request, HttpServletResponse response, Integer id) {
        JsonObject object = new JsonObject();
        try {
            Tb_User user = userServiceImpl.getUser(id);
            object.addProperty("isok", true);
            object.addProperty("result", gson.toJson(user));
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("isok", false);
            object.addProperty("message", e.getMessage());
        }
        WebBack.write(request, response, object);
    }

    @RequestMapping("/getUsers")
    @ResponseBody
    public List<Tb_User> getUsers(HttpServletRequest request, HttpServletResponse response) {
        List<Tb_User> users = userServiceImpl.getUsers();
        System.out.println(users.toString() + "______users");
        return users;
    }

    //登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Tb_User user) throws IOException {
        Tb_User u = userServiceImpl.getUser(user.getId());
        ModelAndView view = new ModelAndView();
        if (u == null) {
            view.addObject("message", "用户不存在");
            view.setViewName("redirect:/index.jsp");
        } else if (u.getPassword().equals(DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword()).getBytes()))) {
            view.addObject("message", "密码输入错误");
            view.setViewName("redirect:/index.jsp");
        } else {
            view.setViewName("/jsp/user/home");
            request.getSession().setAttribute(u.getUsername(), u);
            request.getSession().setMaxInactiveInterval(1000 * 60 * 60 * 24 * 3);
        }
        return view;
    }

    //退出登录
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String name) {
        request.getSession().removeAttribute(name);
        return "forward:/index.jsp";
    }

    //将图片写入到输出流中,客户端读取到图片
    @ResponseBody
    @RequestMapping("/userImg")
    public byte[] userImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource resource = new ClassPathResource("/headIcon.jpg");
        byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        //file sss = new file(Paths.get("sss").toUri().toURL().toString());
        return bytes;
    }

    /**
     * 上传文件
     * 自动创建目标文件夹
     * 如果文件夹下已经含有相同文件名的文件,不上传;不同文件名的,则上传
     *
     * @param req
     * @param resp
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        //目录为,用户名/年/月/日
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = format.format(new Date());
        String path = "D:/" + datePath;
        File file = new File(path);
        //判断上传文件的保存目录是否存在
        if ((!file.isDirectory()) && (!file.exists())) {
            System.out.println("目录不存在，需要创建！");
            file.mkdirs();
        }
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
        try {
            Set<String> set = request.getFileMap().keySet();
            for (String s : set) {
                MultipartFile f = request.getFile(s);
                f.transferTo(Paths.get(path + "/" + f.getOriginalFilename()).toFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - start));//460M,1290mills
        return "/jsp/user/home";
    }

}
