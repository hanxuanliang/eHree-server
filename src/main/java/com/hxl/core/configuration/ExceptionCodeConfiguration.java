package com.hxl.core.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 异常错误码 映射配置类
 * @Author: hanxuanliang
 * @Date: 2020/2/5 14:11
 */

@PropertySource(value = "classpath:config/exception-code.properties")
@ConfigurationProperties(prefix = "ehree")
@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionCodeConfiguration {

    private Map<Integer, String> codes = new HashMap<>();

    public String getOfMessage(Integer code) {
        return codes.get(code);
    }
}
