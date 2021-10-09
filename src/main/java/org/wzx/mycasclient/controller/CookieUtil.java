package org.wzx.mycasclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class CookieUtil {

    @GetMapping("/setCookie/{name}/{value}")
    public void src(HttpServletResponse response, @PathVariable("name") String name, @PathVariable("value") String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        log.debug("one设置了cookie");
    }
}
