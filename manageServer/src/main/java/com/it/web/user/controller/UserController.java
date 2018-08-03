package com.it.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.it.api.MenuData;
import com.it.api.table.Tb_Computer;
import com.it.api.table.user.Tb_User;
import com.it.util.GsonUtil;
import com.it.util.WebBack;
import com.it.util.base.Menus;
import com.it.web.user.service.CoreService;
import com.it.web.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    Gson gson;
    @Autowired
    JsonParser parser;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    void userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            Long sid = CoreService.login(username, DigestUtils.md5Hex(password), false);
            request.getSession().setAttribute("userSid", sid);
            object.addProperty("result", true);
            object.addProperty("sid", sid);
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getMessage());
        }
        WebBack.write(request, response, object);
    }

    /**
     * 创建用户
     *
     * @param user 用户BEAN
     */
    @RequestMapping("/addUser")
    void addUser(Tb_User user, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            Tb_User tb_user = UserService.insertUser(CoreService.getUser(request.getSession()), user);
            tb_user.setPassword("******");
            tb_user.setSuperPassword("******");
            object.addProperty("result", true);
            object.add("row", parser.parse(gson.toJson(tb_user)).getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
        }
        WebBack.write(request, response, object);
    }

    /**
     * 修改用户
     *
     * @param user 用户BEAN
     */
    @RequestMapping("/updateUser")
    void updateUser(Tb_User user, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            Tb_User tb_user = UserService.updateUser(CoreService.getUser(request.getSession()), user);
            tb_user.setPassword("******");
            tb_user.setSuperPassword("******");
            object.addProperty("result", true);
            object.add("row", parser.parse(gson.toJson(tb_user)).getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
        }
        WebBack.write(request, response, object);
    }

    /**
     * 查询用户
     *
     * @param name 查询关键字
     */
    @RequestMapping("/listUser")
    void listUsers(String name, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            List<Tb_User> users = UserService.listUsers(CoreService.getUser(request.getSession()), name);
            for (Tb_User u : users) {
                u.setPassword("******");
                u.setSuperPassword("******");
            }
            object.addProperty("result", true);
            object.add("rows", parser.parse(gson.toJson(users)).getAsJsonArray());
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
        }
        WebBack.write(request, response, object);
    }

    /**
     * 是否允许用户登录
     *
     * @param userId   用户ID
     * @param isEnable 允许true 禁止false
     */
    @RequestMapping("/isEnableLogin")
    void isEnableUserLogin(Long userId, boolean isEnable, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            Tb_User user = UserService.setUserEnable(CoreService.getUser(request.getSession()), userId, isEnable);
            object.addProperty("result", true);
            object.add("row", parser.parse(gson.toJson(user)).getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
        }
        ;
        WebBack.write(request, response, object);
    }

    /**
     * 获取菜单列表
     */
    @RequestMapping("/listMenus")
    void listMenus(HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            Tb_User user = CoreService.getUser(request.getSession());
            List<MenuData> ls = Menus.getMenus(user);
            object.addProperty("result", true);
            object.addProperty("menus", gson.toJson(ls));
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", "获取菜单列表失败");
        }
        WebBack.write(request, response, object);
    }

    /**
     * 根据userId获取用户名
     *
     * @param userId
     */
    @RequestMapping("/getUsername")
    public void getUsername(HttpServletRequest request, HttpServletResponse response, Long userId) {
        JsonObject object = new JsonObject();
        try {
            String username = CoreService.getUserName(userId);
            object.addProperty("result", true);
            object.addProperty("row", username);
        } catch (Exception e) {
            object.addProperty("result", false);
            object.addProperty("message", "获取失败");
        }
        WebBack.write(request, response, object);
    }

    /**
     * 申请注册电脑；
     */
    @RequestMapping("/regMac")
    public void regMac(HttpServletRequest request, HttpServletResponse response,
                       String macId, String computerName, String loginName, String password) {
        JsonObject object = new JsonObject();
        try {
            UserService.regMac(macId, computerName, loginName, password);
            object.addProperty("result", true);
            object.addProperty("message", "注册成功");
        } catch (Exception e) {
            object.addProperty("result", false);
            object.addProperty("message", "注册失败");
        }
        WebBack.write(request, response, object);
    }

    /**
     * 查询计算机列表；
     */
    @RequestMapping("/listComputer")
    public void listComputer(HttpServletRequest request, HttpServletResponse response, String keyword) {
        JsonObject object = new JsonObject();
        try {
            List<Tb_Computer> list = UserService.listComputer(keyword);
            object.addProperty("result", true);
            object.add("rows", GsonUtil.gson.toJsonTree(list).getAsJsonArray());
        } catch (Exception e) {
            object.addProperty("result", false);
            object.addProperty("message", "注册失败");
        }
        WebBack.write(request, response, object);
    }

    /**
     * 修改注册信息；
     */
    @RequestMapping("/updateComputer")
    public void updateComputer(Tb_Computer computer, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            Tb_Computer c = UserService.updateComputer(CoreService.getUser(request.getSession()), computer);
            object.addProperty("result", true);
            object.add("row", GsonUtil.gson.toJsonTree(c).getAsJsonObject());
        } catch (Exception e) {
            object.addProperty("result", false);
            object.addProperty("message", "失败");
        }
        WebBack.write(request, response, object);
    }

    /**
     * 删除注册信息；
     *
     * @param computerId Tb_Computer 自增长ID
     */
    @RequestMapping("/deleteComputer")
    public void deleteComputer(Long computerId, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            UserService.deleteComputer(CoreService.getUser(request.getSession()), computerId);
            object.addProperty("result", true);
            object.addProperty("row", "删除成功");
        } catch (Exception e) {
            object.addProperty("result", false);
            object.addProperty("message", "删除失败");
        }
        WebBack.write(request, response, object);
    }

}
