package org.wzx.mycasclient.controller;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzx.model.Result;
import org.wzx.mycasclient.config.MyConstant;
import org.wzx.mycasclient.util.SessionUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("clientAPI")
public class ClientAPI {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MyConstant myConstant;


    @GetMapping("loginSucceed")
    public void loginSucceed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("开始注销系统。。。");
        //获取当前用户信息
//        Object casinfo = SessionUtil.getSession(request);
//        Map<String, Object> param = new HashMap<>();
//        param.put("logoutParam", casinfo);
//        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
//        response.setHeader("Location", myConstant.MY_CAS_SERVER_URL + "/user/logout?username=wzx");
        response.sendRedirect(myConstant.MY_CAS_SERVER_URL + "/user/logout?username=wzx");
        log.debug("注销系统完成。。。");
        return;
//        HttpUtil.get(myConstant.MY_CAS_SERVER_URL + "/user/logout?username=wzx");

//        response.addHeader("kek", "ddddddddddddddddddddddd");


        //请求认证中心注销所有子系统
//        Result jsonResult = objectMapper.readValue(
//                HttpUtil.post(MyConstant.MY_CAS_SERVER_URL + "/user/logout", param),
//                new TypeReference<>() {
//                });
//        if (jsonResult.getCode() == 0) {
//            log.debug("注销成功！！！！！！！！！！！！！！！！！！！！！！！！！！！");
//            jsonResult.setMessage("注销成功");
//        } else {
//            log.error("注销失败！！！！！！！！！！！！！！！！！！！！！！！！！！！");
//            jsonResult.setCode(-1);
//            jsonResult.setMessage("注销失败");
//        }

    }

    @GetMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("开始注销系统。。。");
        //获取当前用户信息
//        Object casinfo = SessionUtil.getSession(request);
//        Map<String, Object> param = new HashMap<>();
//        param.put("logoutParam", casinfo);
//        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
//        response.setHeader("Location", myConstant.MY_CAS_SERVER_URL + "/user/logout?username=wzx");
        response.sendRedirect(myConstant.MY_CAS_SERVER_URL + "/user/logout?username=wzx");
        log.debug("注销系统完成。。。");
        return;
//        HttpUtil.get(myConstant.MY_CAS_SERVER_URL + "/user/logout?username=wzx");

//        response.addHeader("kek", "ddddddddddddddddddddddd");


        //请求认证中心注销所有子系统
//        Result jsonResult = objectMapper.readValue(
//                HttpUtil.post(MyConstant.MY_CAS_SERVER_URL + "/user/logout", param),
//                new TypeReference<>() {
//                });
//        if (jsonResult.getCode() == 0) {
//            log.debug("注销成功！！！！！！！！！！！！！！！！！！！！！！！！！！！");
//            jsonResult.setMessage("注销成功");
//        } else {
//            log.error("注销失败！！！！！！！！！！！！！！！！！！！！！！！！！！！");
//            jsonResult.setCode(-1);
//            jsonResult.setMessage("注销失败");
//        }

    }

    @GetMapping("setCookie/{name}/{value}")
    public void setCookie(HttpServletRequest request, HttpServletResponse response, @PathVariable("name") String name, @PathVariable("value") String value) {
        log.debug(request.getRequestURL() + "设置了cookie:" + name + "=" + value);
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
        return;
    }

    @GetMapping("getCookie/{name}")
    public String getCookie(HttpServletRequest request, @PathVariable("name") String name) {
        log.debug(request.getRequestURL() + "取cookie");
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    log.debug("获取了cookie:" + name + "=" + value);
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }

    @GetMapping("lookCookie")
    public String setCookieLook(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            StringBuffer stringBuffer = new StringBuffer();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    log.debug("cookie内容:" + cookie.getName() + "=" + cookie.getValue());
                    stringBuffer.append("cookie内容:");
                    stringBuffer.append(cookie.getName());
                    stringBuffer.append("=");
                    stringBuffer.append(cookie.getValue());
                    stringBuffer.append("\n");
                }
            }
            if (stringBuffer.length() == 0) {
                return "没有cookie";
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "获取cookie出现异常";
        }
    }
}
