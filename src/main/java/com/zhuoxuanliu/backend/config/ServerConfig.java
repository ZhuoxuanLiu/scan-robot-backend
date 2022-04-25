package com.zhuoxuanliu.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "server-config")
public class ServerConfig {
    public String ipAddress;
    public String port;
}
