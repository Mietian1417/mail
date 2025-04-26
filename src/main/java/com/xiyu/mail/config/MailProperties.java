package com.xiyu.mail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailProperties {

    /**
     * 邮件服务器主机地址
     */
    private String host;

    /**
     * 邮件服务器端口
     */
    private int port;

    /**
     * 邮件服务器用户名
     */
    private String username;

    /**
     * 邮件服务器密码
     */
    private String password;
}