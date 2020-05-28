package com.ley.lcode.support;


import lombok.ToString;


/**
 * @author Leigh Yu
 * @date 2020/2/11 14:30
 */
@ToString
public class MyListNode<T> {

    public MyListNode() {

    }

    public MyListNode(T data, MyListNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public T data;
    public MyListNode next;

}
