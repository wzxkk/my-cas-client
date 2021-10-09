package org.wzx.mycasclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

/**
 * @description:拦截访问本系统的所有url,WebMVC配置，你可以集中在这里配置拦截器、过滤器、静态资源缓存等
 * @author: 鱼头
 * @time: 2021-2-23 15:39
 */
@Slf4j
@Configuration
public class WebInterceptor extends HandlerInterceptorAdapter implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("addInterceptors********************************");
        registry.addInterceptor(this).addPathPatterns("/**");//注册自己定义的拦截器
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("addCorsMappings********************************");
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
//                .allowedOriginPatterns("*")
                .allowedOrigins("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(3600);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {

        String url = request.getRequestURL().toString();
        System.out.println("进入客户端拦截，请求的地址:" + url);
//        log.debug("请求的地址:" + url);
        if (url.contains("setCookie")) {
            return true;
        }
        HttpSession httpSession = request.getSession();
        Enumeration<String> httpSessionAttributeNames = httpSession.getAttributeNames();
        while (httpSessionAttributeNames.hasMoreElements()) {
            String name = httpSessionAttributeNames.nextElement();
            System.out.println("session name:" + name + ",session value:" + httpSession.getAttribute(name));
        }
        Object casInfo = httpSession.getAttribute("casInfo");
        if (casInfo == null) {
            //先查看浏览器有没有中央权限的cookie
            Cookie[] cookies = request.getCookies();
            Cookie mycasCookie = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("mycasCookie".equals(cookie.getName())) {
                        mycasCookie = cookie;
                        break;
                    }
                }
            }
            if (mycasCookie == null) {
                Map<String, String[]> map = request.getParameterMap();
                if (map != null && map.size() > 0) {
//                    log.debug("request信息：" + JSONUtil.toJsonStr(map));
                }
                String info = request.getParameter("casInfo");
                if (info == null || info.trim().isEmpty()) {
                    String ticket = request.getParameter("ticket");
                    if (ticket == null || ticket.trim().isEmpty()) {
//                        log.debug("没有ticket信息,请先到权限中心登录");
                        response.setStatus(302);
                        response.setHeader("Location", "http://localhost:11011/entry/login?service=" + url);
                        return false;
                    } else {
//                        log.debug("ticket信息" + ticket);
                        response.setStatus(302);
                        response.setHeader("Location", "http://localhost:11011/entry/validate?service=" + url + "&ticket=" + ticket);
                        return false;
                    }
                } else {
                    //设置session
                    httpSession.setAttribute("casInfo", info);
                    return true;
                }
            } else {
                //如果有cookie
                String cookieValue = mycasCookie.getValue();
//                log.debug(cookieValue + "已在权限中心登录");
                return true;
            }
        } else {
//            log.debug("已登录,登录信息为：" + JSONUtil.toJsonStr(casInfo));
            return true;
        }
    }
}
