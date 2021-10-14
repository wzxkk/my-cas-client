package org.wzx.mycasclient.config;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wzx.constant.ConstString;
import org.wzx.model.Result;
import org.wzx.mycasclient.util.SessionUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:拦截访问本系统的所有url,WebMVC配置，你可以集中在这里配置拦截器、过滤器、静态资源缓存等
 * @author: 鱼头
 * @time: 2021-2-23 15:39
 */
@Slf4j
@Configuration
public class MyConfig implements AsyncHandlerInterceptor, WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MyConstant myConstant;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(this).addPathPatterns("/**")
//                .excludePathPatterns(Arrays.asList(
//                        myConstant.PERMIT_URLS.trim().isEmpty() ?
//                                new String[]{"/cookie/**", "/favicon.ico"} :
//                                (myConstant.PERMIT_URLS.trim() + ",/cookie/**,/favicon.ico").split(",")));//注册自己定义的拦截器
        registry.addInterceptor(this).addPathPatterns("/**").excludePathPatterns(Arrays.asList(myConstant.PERMIT_URLS.trim()));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {
        String url = request.getRequestURL().toString();
        log.debug("进入客户端拦截，请求的url:" + url);
//        HttpSession httpSession = request.getSession();
//        Enumeration<String> httpSessionAttributeNames = httpSession.getAttributeNames();
//        while (httpSessionAttributeNames.hasMoreElements()) {
//            String name = httpSessionAttributeNames.nextElement();
//            log.debug("session内容:" + name + "=" + httpSession.getAttribute(name));
//        }
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                log.debug("cookie内容:" + cookie.getName() + "=" + cookie.getValue());
//            }
//        }
        Object casInfo = SessionUtil.getSession(request);
        if (casInfo == null) {
            //先查看浏览器有没有认证中心的cookie
            String ticket = request.getParameter("ticket");
            if (ticket == null || ticket.trim().isEmpty()) {
                log.debug("请先到权限中心登录");
                response.sendRedirect(myConstant.MY_CAS_SERVER_URL + "/public/login?service=" + url);
            } else {
                log.debug("已在认证中心登录，开始验证ticket信息:" + ticket);
                checkTicket(request, response, url, ticket);
            }
            return false;
        } else {
            log.debug("已与浏览器建立SESSION,信息为：" + JSONUtil.toJsonStr(casInfo));
            return true;
        }
    }

    private void checkTicket(HttpServletRequest request, HttpServletResponse response, String url, String ticket) throws IOException {
        Result jsonResult = objectMapper.readValue(HttpUtil.get(myConstant.MY_CAS_SERVER_URL + "/public/validate?ticket=" + ticket), new TypeReference<>() {
        });
        if (jsonResult.getCode() == 0) {
            log.debug("登录成功！！！！！！！！！！！！！！！！！！！！！！！！！！！");
            SessionUtil.setSession(request, jsonResult.getData());
            response.sendRedirect(url);
            return;
        } else {
            log.debug("ticket验证失败，请重新到权限中心登录");
            response.sendRedirect(myConstant.MY_CAS_SERVER_URL + "/public/login?service=" + url);
            return;
        }
    }


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
//                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//                        "Access-Control-Request-Headers")
//                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                .allowCredentials(true).maxAge(3600);
//    }

//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
//        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
//        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
//        corsConfiguration.setAllowCredentials(true);//支持安全证书。跨域携带cookie需要配置这个
//        corsConfiguration.setMaxAge(3600L);//预检请求的有效期，单位为秒。设置maxage，可以避免每次都发出预检请求
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }
}
