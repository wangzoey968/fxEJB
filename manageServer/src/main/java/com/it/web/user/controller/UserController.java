package com.it.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.it.api.MenuData;
import com.it.api.table.Tb_Computer;
import com.it.api.table.Tb_User;
import com.it.api.table.Tb_UserAuthExtend;
import com.it.util.GsonUtil;
import com.it.util.WebBack;
import com.it.web.Menus;
import com.it.web.user.service.AuthService;
import com.it.web.user.service.AuthService;
import com.it.web.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("/uac/user")
public class UserController {

    @Autowired
    Gson gson;
    @Autowired
    JsonParser parser;

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param passWord 密码
     */
    @RequestMapping("/login")
    void userLogin(String userName, String passWord, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            String sid = AuthService.login(userName, DigestUtils.md5Hex(passWord),false);
            request.getSession().setAttribute("userSid", sid);
            object.addProperty("result", true);
            object.addProperty("sid", sid);
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
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
            Tb_User tb_user = UserService.insertUser(AuthService.getUser(request.getSession()), user);
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
            Tb_User tb_user = UserService.updateUser(AuthService.getUser(request.getSession()), user);
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
            List<Tb_User> users = UserService.listUsers(AuthService.getUser(request.getSession()), name);
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
            Tb_User user = UserService.setUserEnable(AuthService.getUser(request.getSession()), userId, isEnable);
            object.addProperty("result", true);
            object.add("row", parser.parse(gson.toJson(user)).getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
        };
        WebBack.write(request, response, object);
    }

    /**
     * 添加或修改用户扩展权限
     *
     * @param userAuthExtend 用户扩展权限BEAN
     */
    @RequestMapping("/saveOrUpdateAuthExtend")
    void addUserAuthExtend(Tb_UserAuthExtend userAuthExtend, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            Tb_UserAuthExtend authExtend = UserService.saveOrUpdateUserAuthExtend(AuthService.getUser(request.getSession()), userAuthExtend);
            object.addProperty("result", true);
            object.add("row", parser.parse(gson.toJson(authExtend)).getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
        }
        WebBack.write(request, response, object);
    }

    /**
     * 删除用户扩展权限
     *
     * @param userAuthExtend 用户扩展权限BEAN
     */
    @RequestMapping("/deleteAuthExtend")
    void deleteUserAuthExtend(Tb_UserAuthExtend userAuthExtend, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            UserService.deleteUserAuthExtend(AuthService.getUser(request.getSession()), userAuthExtend);
            object.addProperty("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
        }
        WebBack.write(request, response, object);
    }

    /**
     * 查询用户扩展权限
     *
     * @param userId 用户自增长ID
     */
    @RequestMapping("/listAuthExtend")
    void listUserAuthExtend(Long userId, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            List<Tb_UserAuthExtend> userAuthExtendList = UserService.listUserAuthExtends(AuthService.getUser(request.getSession()), userId);
            object.addProperty("result", true);
            object.add("rows", parser.parse(gson.toJson(userAuthExtendList)).getAsJsonArray());
        } catch (Exception e) {
            e.printStackTrace();
            object.addProperty("result", false);
            object.addProperty("message", e.getCause().getMessage());
        }
        WebBack.write(request, response, object);
    }

    /**
     * 获取菜单列表
     */
    @RequestMapping("/listMenus")
    void listMenus(HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject();
        try {
            Tb_User mySelf = AuthService.getUser(request.getSession());
            List<MenuData> ls = Menus.getMenus(mySelf);
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
            String username = AuthService.getUserName(userId);
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
            Tb_Computer c = UserService.updateComputer(AuthService.getUser(request.getSession()), computer);
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
            UserService.deleteComputer(AuthService.getUser(request.getSession()), computerId);
            object.addProperty("result", true);
            object.addProperty("row", "删除成功");
        } catch (Exception e) {
            object.addProperty("result", false);
            object.addProperty("message", "删除失败");
        }
        WebBack.write(request, response, object);
    }

}
