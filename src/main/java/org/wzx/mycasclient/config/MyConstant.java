package org.wzx.mycasclient.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @description: 获取配置常量
 * @author: 鱼头
 * @time: 2021-10-11 11:34
 */
public class MyConstant {

    public static volatile String MY_CAS_SERVER_URL;

    public static volatile String PERMIT_URLS;

    public static volatile String CLIENT_HOST;

    public static volatile String PROTOCO;

    public static volatile String CLIENT_PORT;

    public static volatile String SERVLET_PATH;

    @Value("${mycas.server.url:http://localhost}")
    public void setMY_CAS_SERVER_URL(String MY_CAS_SERVER_URL) {
        this.MY_CAS_SERVER_URL = MY_CAS_SERVER_URL;
    }

    @Value("${mycas.client.permitUrls:}")
    public void setPERMIT_URLS(String PERMIT_URLS) {
        this.PERMIT_URLS = PERMIT_URLS;
    }

    @Value("${mycas.client.hostname:localhost}")
    public void setCLIENT_HOST(String CLIENT_HOST) {
        this.CLIENT_HOST = CLIENT_HOST;
    }

    @Value("${server.port:8080}")
    public void setCLIENT_PORT(String CLIENT_PORT) {
        this.CLIENT_PORT = CLIENT_PORT;
    }

    @Value("${server.servlet.context-path:}")
    public void setSERVLET_PATH(String SERVLET_PATH) {
        this.SERVLET_PATH = SERVLET_PATH;
    }

    @Value("${server.ssl.key-store:http}")
    public void setPROTOCO(String PROTOCO) {
        if ("http".equals(PROTOCO)) {
            this.PROTOCO = PROTOCO;
        } else {
            this.PROTOCO = "https";
        }
    }
}
