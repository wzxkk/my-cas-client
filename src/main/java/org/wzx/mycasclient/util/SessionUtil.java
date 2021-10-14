package org.wzx.mycasclient.util;

import org.wzx.mycasclient.config.MyConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 客户端session操作类
 * @author: 鱼头(韦忠幸)
 * @time: 2021-10-13 14:09
 */
public class SessionUtil {
    public static void setSession(HttpServletRequest request, Object casInfo) {
        request.getSession().setAttribute(MyConstant.CASINFO, casInfo);
    }

    public static Object getSession(HttpServletRequest request) {
        return request.getSession().getAttribute(MyConstant.CASINFO);
    }
}
