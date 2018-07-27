package com.it.util;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by wangzy on 2018/7/27.
 */
public class WebBack {

    public static void simpleWrite(HttpServletRequest request, HttpServletResponse response) {
        JsonObject o = null;
        try {
            o = new JsonObject();
            o.addProperty("result", true);
        } catch (Exception e) {
            o = new JsonObject();
            o.addProperty("result", false);
            o.addProperty("message", e.getMessage());
        } finally {
            write(request, response, o);
        }
    }


    public static void write(HttpServletRequest request, HttpServletResponse response, Object o) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        String callBack = request.getParameter("callback");
        try {
            out = response.getWriter();
            if (null != callBack) {
                out.write(callBack + "(" + o.toString() + ")");
            } else {
                out.write(o.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
