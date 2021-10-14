package org.wzx.mycasclient.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.wzx.constant.ClientState;
import org.wzx.model.ClientRegister;
import org.wzx.model.Result;
import org.wzx.mycasclient.config.MyConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 系统初始化必要的信息
 * @author: 鱼头
 * @time: 2020-10-14 11:36
 */

@Slf4j
@Component
public class SystemInit implements ApplicationRunner {
    @Autowired
    private MyConstant myConstant;

    @Override
    public void run(ApplicationArguments args) {
        log.debug("权限客户端初始化。。。");
        log.debug("认证中心url:" + myConstant.MY_CAS_SERVER_URL);
        log.debug("本客户端协议:" + myConstant.PROTOCO);
        log.debug("客户端开放路径:" + myConstant.PERMIT_URLS);
        new Thread(() -> {
            Map<String, Object> param = new HashMap<>();
            param.put("protocol", myConstant.PROTOCO);
            param.put("hostname", myConstant.CLIENT_HOST);
            param.put("port", myConstant.CLIENT_PORT);
            param.put("state", "RUN");
            log.debug(HttpUtil.post(myConstant.MY_CAS_SERVER_URL + "/public/clientRegister", param));
        }).start();
        log.debug("权限客户端初始化完毕。。。");
    }
}
