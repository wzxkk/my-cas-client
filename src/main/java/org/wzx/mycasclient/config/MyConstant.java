package org.wzx.mycasclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 获取配置常量
 * @author: 鱼头
 * @time: 2021-10-11 11:34
 */
@Component
public class MyConstant {

    public static final String CASINFO = "CAS_INFO";

    @Value("${mycas.server.url:http://localhost}")
    public volatile String MY_CAS_SERVER_URL;

    @Value("${mycas.client.permitPatterns:}")
    public volatile String PERMIT_URLS;

    @Value("${mycas.client.hostname:localhost}")
    public volatile String CLIENT_HOST;

    @Value("${server.port:8080}")
    public volatile String CLIENT_PORT;

    @Value("${server.servlet.context-path:}")
    public volatile String SERVLET_PATH;

    public volatile String PROTOCO;

    @Value("${server.ssl.key-store:http}")
    public void setPROTOCO(String PROTOCO) {
        if ("http".equals(PROTOCO)) {
            this.PROTOCO = PROTOCO;
        } else {
            this.PROTOCO = "https";
        }
    }
}
