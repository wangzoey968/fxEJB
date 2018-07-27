package com.it.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 可以使用此类,进行页面的数据传递
 */
public class WebBack {
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
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
