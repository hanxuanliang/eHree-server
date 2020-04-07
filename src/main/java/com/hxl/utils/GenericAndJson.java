package com.hxl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxl.exception.ServerErrorException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 泛型json转换
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 20:41
 */
@Component
public class GenericAndJson {

    private static ObjectMapper mapper;

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        GenericAndJson.mapper = mapper;
    }

    public static <T> String objectToJson(T o) {
        try {
            return GenericAndJson.mapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
    }

    public static <T> T jsonToObject(String str, TypeReference<T> t) {
        if (str == null) { return null; }

        try {
            return GenericAndJson.mapper.readValue(str, t);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException(9999);
        }
    }
}
