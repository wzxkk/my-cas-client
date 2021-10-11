package org.wzx.mycasclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class CookieUtil {

    @GetMapping("/setCookie/{name}/{value}")
    public void setCookie(HttpServletResponse response, @PathVariable("name") String name, @PathVariable("value") String value) {
        log.debug("设置了cookie:" + name + "=" + value);
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
