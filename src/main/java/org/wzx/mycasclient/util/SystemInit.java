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

    @Override
    public void run(ApplicationArguments args) {
        log.debug("权限客户端初始化。。。");
        log.debug("认证中心url:" + MyConstant.MY_CAS_SERVER_URL);
        log.debug("本客户端协议:" + MyConstant.PROTOCO);
        log.debug("客户端开放路径:" + MyConstant.PERMIT_URLS);
        new Thread(() -> {
            Map<String, Object> param = new HashMap<>();
            param.put("protocol", MyConstant.PROTOCO);
            param.put("hostname", MyConstant.CLIENT_HOST);
            param.put("port", MyConstant.CLIENT_PORT);
            param.put("state", "RUN");
            log.debug(HttpUtil.post(MyConstant.MY_CAS_SERVER_URL + "/public/clientRegister", param));
        }).start();
        log.debug("权限客户端完毕。。。");
    }
}
