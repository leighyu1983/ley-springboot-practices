package com.ley.myjackson;

import lombok.Data;

/**
 * @author Leigh Yu
 * @date 2020/2/25 20:37
 */
@Data
public class Apple implements AbstractEntity {
    private String name;
    private int age;
}
