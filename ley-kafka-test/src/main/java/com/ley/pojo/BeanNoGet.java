package com.ley.pojo;

import java.io.Serializable;

/**
 * @author Leigh Yu
 * @date 2020/2/13 11:08
 */
public class BeanNoGet implements Serializable {

    private String name;
    private int age;

    public BeanNoGet(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String name() {return name;}
    public int age() { return age;}


}
