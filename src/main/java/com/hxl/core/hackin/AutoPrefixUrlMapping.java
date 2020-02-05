package com.hxl.core.hackin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @Description: 自动根据包路径构建requestmapping路由
 * @Author: hanxuanliang
 * @Date: 2020/2/5 15:30
 */
public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {

    @Value("${ehree.api-basepackage}")
    private String apiBasePackagePath;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo originMappingInfo = super.getMappingForMethod(method, handlerType);
        if (originMappingInfo != null) {
            String prefix = getPrefix(handlerType);
            return RequestMappingInfo.paths(prefix).build().combine(originMappingInfo);
        }
        return originMappingInfo;
    }

    private String getPrefix(Class<?> handlerType) {
        String packageName = handlerType.getPackage().getName();
        String dotPath = packageName.replaceAll(apiBasePackagePath, "");
        return dotPath.replace(".", "/");
    }
}
