package com.it.util;

import com.google.gson.JsonObject;
import com.it.entity.Tb_User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 此类对所有的访问url,在请求之前进行拦截,判断是否已登录
 */
public class RequestInterceptor implements HandlerInterceptor {

    //在请求之前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Tb_User user = (Tb_User) session.getAttribute("productUser");
        JsonObject object = new JsonObject();
        if (user == null) {
            object.addProperty("isok", false);
            object.addProperty("message", "登录超时,请重新登录");
            WebBack.write(httpServletRequest, httpServletResponse, object);
            return false;
        } else {
            if (user.getViewer()) {
                object.addProperty("isok", false);
                object.addProperty("message", "您只是查看者");
                WebBack.write(httpServletRequest, httpServletResponse, object);
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //视图渲染之前调用
        System.out.println("请求后,获得的对象是" + o.getClass().newInstance().toString());
        System.out.println("请求后,视图名称是" + modelAndView.getViewName());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //视图渲染之后进行调用,多用于清理资源
        System.out.println("请求错误" + e.toString());
    }
}