package com.ley.leetcode.support;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author Leigh Yu
 * @date 2020/2/11 14:30
 */
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