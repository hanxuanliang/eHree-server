package com.hxl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxl.exception.ServerErrorException;

import javax.annotation.Resource;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/3/16 8:43
 */
@Converter
public class SuperGenericAndJson<T> implements AttributeConverter<T, String> {

    @Resource
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    @Override
    public T convertToEntityAttribute(String message) {
        if (message == null) {
            return null;
        }
        try {
            return mapper.readValue(message, new TypeReference<T>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }
}
