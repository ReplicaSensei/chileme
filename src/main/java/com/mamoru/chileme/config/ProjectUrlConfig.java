package com.mamoru.chileme.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {
    /**
     * 点餐系统
     */
    public String chileme;
}
