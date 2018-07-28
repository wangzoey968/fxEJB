package com.it.web.user.controller

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.it.api.MenuData
import com.it.api.table.Tb_Computer
import com.it.api.table.Tb_User
import com.it.api.table.Tb_UserAuthExtend
import com.it.util.GsonUtil
import com.it.util.WebBack
import com.it.web.Menus
import com.it.web.user.service.UacService
import com.it.web.user.service.UserService
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@Scope('prototype')
@RequestMapping('/uac/user')
class UserController {

    @Autowired
    Gson gson
    @Autowired
    JsonParser parser

    /**用户登录
     * @param userName 用户名
     * @param passWord 密码
     */
    @RequestMapping('/login')
    void userLogin(String userName, String passWord, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            String sid = UacService.login(userName, DigestUtils.md5Hex(passWord))
            request.getSession().setAttribute('userSid', sid)
            object.addProperty("result", true)
            object.addProperty("sid", sid)
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", e.cause ? e.cause.message : e.message)
        }
        WebBack.write(request, response, object)
    }

    /**创建用户
     * @param user 用户BEAN
     */
    @RequestMapping('/addUser')
    void addUser(Tb_User user, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            user.password = DigestUtils.md5Hex(user.password)
            Tb_User tb_user = UserService.insertUser(UacService.getUser(request.getSession()), user)
            tb_user.password = '******'
            tb_user.superPassword = '******'
            object.addProperty("result", true)
            object.add("row", parser.parse(gson.toJson(tb_user)).asJsonObject)
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", e.cause ? e.cause.message : e.message)
        }
        WebBack.write(request, response, object)
    }

    /**修改用户
     * @param user 用户BEAN
     */
    @RequestMapping('/updateUser')
    void updateUser(Tb_User user, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            Tb_User tb_user = UserService.updateUser(UacService.getUser(request.getSession()), user)
            tb_user.password = '******'
            tb_user.superPassword = '******'
            object.addProperty("result", true)
            object.add("row", parser.parse(gson.toJson(tb_user)).asJsonObject)
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", e.cause ? e.cause.message : e.message)
        }
        WebBack.write(request, response, object)
    }

    /**查询用户
     * @param name 查询关键字
     */
    @RequestMapping('/listUser')
    void listUsers(String name, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            def users = UserService.listUsers(UacService.getUser(request.getSession()), name)
            users.each { it.password = "******"; it.superPassword = "******" }
            object.addProperty("result", true)
            object.add("rows", parser.parse(gson.toJson(users)).asJsonArray)
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", e.cause ? e.cause.message : e.message)
        }
        WebBack.write(request, response, object)
    }

    /**是否允许用户登录
     * @param userId 用户ID
     * @param isEnable 允许true 禁止false
     */
    @RequestMapping('/isEnableLogin')
    void isEnableUserLogin(Long userId, boolean isEnable, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            Tb_User user = UserService.setUserEnable(UacService.getUser(request.getSession()), userId, isEnable)
            object.addProperty("result", true)
            object.add("row", parser.parse(gson.toJson(user)).asJsonObject)
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", e.cause ? e.cause.message : e.message)
        }
        WebBack.write(request, response, object)
    }

    /**添加或修改用户扩展权限
     * @param userAuthExtend 用户扩展权限BEAN
     */
    @RequestMapping('/saveOrUpdateAuthExtend')
    void addUserAuthExtend(Tb_UserAuthExtend userAuthExtend, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            Tb_UserAuthExtend authExtend = UserService.saveOrUpdateUserAuthExtend(UacService.getUser(request.getSession()), userAuthExtend)
            object.addProperty("result", true)
            object.add("row", parser.parse(gson.toJson(authExtend)).asJsonObject)
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", e.cause ? e.cause.message : e.message)
        }
        WebBack.write(request, response, object)
    }

    /**删除用户扩展权限
     * @param userAuthExtend 用户扩展权限BEAN
     */
    @RequestMapping('/deleteAuthExtend')
    void deleteUserAuthExtend(Tb_UserAuthExtend userAuthExtend, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            UserService.deleteUserAuthExtend(UacService.getUser(request.getSession()), userAuthExtend)
            object.addProperty("result", true)
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", e.cause ? e.cause.message : e.message)
        }
        WebBack.write(request, response, object)
    }

    /**查询用户扩展权限
     * @param userId 用户自增长ID
     */
    @RequestMapping('/listAuthExtend')
    void listUserAuthExtend(Long userId, HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            List<Tb_UserAuthExtend> userAuthExtendList = UserService.listUserAuthExtends(UacService.getUser(request.getSession()), userId)
            object.addProperty("result", true)
            object.add("rows", parser.parse(gson.toJson(userAuthExtendList)).asJsonArray)
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", e.cause ? e.cause.message : e.message)
        }
        WebBack.write(request, response, object)
    }

    /**获取菜单列表
     */
    @RequestMapping('/listMenus')
    void listMenus(HttpServletRequest request, HttpServletResponse response) {
        JsonObject object = new JsonObject()
        try {
            def mySelf = UacService.getUser(request.getSession());
            List<MenuData> ls = Menus.getMenus(mySelf)
            object.addProperty("result", true)
            object.addProperty("menus", gson.toJson(ls))
        } catch (e) {
            e.printStackTrace()
            object.addProperty("result", false)
            object.addProperty("message", "获取菜单列表失败")
        }
        WebBack.write(request, response, object)
    }

    /**
     * 根据userId获取用户名
     * @param userId
     */
    @RequestMapping("/getUsername")
    public void getUsername(HttpServletRequest request, HttpServletResponse response, Long userId) {
        JsonObject object = new JsonObject()
        try {
            String username = UacService.getUserName(userId)
            object.addProperty("result", true)
            object.addProperty("row", username)
        } catch (Exception e) {
            object.addProperty("result", false)
            object.addProperty("message", "获取失败")
        }
        WebBack.write(request, response, object)
    }

    /**
     * 查询注册验证；
     * @param request
     * @param response
     * @param macs
     */
    @RequestMapping("/findRegMac")
    public void findRegMac(HttpServletRequest request, HttpServletResponse response, String macs) {
        List<String> macList = GsonUtil.gson.fromJson(macs, new TypeToken<List<String>>() {}.getType())
        WebBack.sWrite(request, response, {
            JsonObject o ->
                if (ConfigService.getCfg('clientMustReg') == 'false') {
                    o.addProperty('reg', true);
                    o.addProperty('mac', '000');
                    o.addProperty('computerName', '000');
                } else {
                    if (macList.isEmpty()) throw new Exception('无法注册;')
                    List<Tb_Computer> computers = UserService.findComputerInMacs(macList);
                    if (computers.isEmpty()) throw new Exception('未注册');
                    def c = computers.find { it.reg }
                    if (c) {
                        o.addProperty('reg', true);
                        o.addProperty('mac', c.macId);
                        o.addProperty('computerName', c.computerName);
                    } else {
                        o.addProperty('reg', false);
                        o.addProperty('mac', computers.first().macId);
                        o.addProperty('computerName', computers.first().computerName);
                    }
                }
        })
    }

    /**
     * 申请注册电脑；
     * @param request
     * @param response
     * @param macId
     * @param computerName
     * @param loginName
     * @param password
     */
    @RequestMapping("/regMac")
    public void regMac(HttpServletRequest request, HttpServletResponse response,
                       String macId, String computerName, String loginName, String password) {
        WebBack.sWrite(request, response, {
            JsonObject o ->
                UserService.regMac(macId, computerName, loginName, password)
                o.addProperty("message", '提交成功；')
        })
    }

    /**
     *  查询计算机列表；
     * @param request
     * @param response
     * @param keyword
     */
    @RequestMapping("/listComputer")
    public void listComputer(HttpServletRequest request, HttpServletResponse response, String keyword) {
        WebBack.sWrite(request, response, { JsonObject o ->
            o.add('datas', GsonUtil.gson.toJsonTree(UserService.listComputer(keyword)).getAsJsonArray())
        })
    }

    /**
     * 修改注册信息；
     */
    @RequestMapping('/updateComputer')
    public void updateComputer(Tb_Computer tb_computer, HttpServletRequest request, HttpServletResponse response) {
        WebBack.sWrite(request, response, { JsonObject o ->
            Tb_Computer computer = UserService.updateComputer(UacService.getUser(request.getSession()), tb_computer)
            o.add("row", GsonUtil.gson.toJsonTree(computer).asJsonObject)
        })
    }

    /**
     * 删除注册信息；
     * @param computerId Tb_Computer 自增长ID
     */
    @RequestMapping('/deleteComputer')
    public void deleteComputer(Long computerId, HttpServletRequest request, HttpServletResponse response) {
        WebBack.sWrite(request, response, { JsonObject o ->
            UserService.deleteComputer(UacService.getUser(request.getSession()), computerId)
        })
    }
}
