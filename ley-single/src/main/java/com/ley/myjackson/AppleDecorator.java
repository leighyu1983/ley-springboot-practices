package com.ley.myjackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author Leigh Yu
 * @date 2020/2/25 21:16
 */
public class AppleDecorator extends MyAbstractDecorator<Apple> {
    @JsonCreator
    public AppleDecorator() {
        RequestContextHolder request;
        request.getAttribute("");
        ThreadLocal<String> tl = new ThreadLocal<>();
    }
}
