package com.ley.myjackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * @author Leigh Yu
 * @date 2020/2/25 21:09
 */
public class MyAbstractDecorator<T> implements Serializable {
    private static final ObjectMapper mapper =
            new ObjectMapper().configure(
              DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SneakyThrows
    public static <T> MyAbstractDecorator<T> fromJson(String json) {
        return mapper.readValue(json, MyAbstractDecorator.class);
    }
}
