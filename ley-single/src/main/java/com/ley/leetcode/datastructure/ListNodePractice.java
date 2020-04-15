package com.ley.leetcode.datastructure;


import com.ley.leetcode.support.SingleDirectionNode;

/**
 * @author Leigh Yu
 * @date 2020/4/12 20:47
 */
public class ListNodePractice {

    /**
     * 单链表反转
     *
     * @param head
     * @return
     */
    public static SingleDirectionNode<String> reverseNodeList(SingleDirectionNode<String> head) {
        if(head == null || head.next == null) {
            return head;
        }

        SingleDirectionNode<String> newHead = null;
        SingleDirectionNode<String> next = null;

        // 缓存老节点的后一个节点，老节点指向新节点，将弄好的老节点(有了新指向)赋给新节点，老节点向后移动
        while(head != null) {
            // 备份当前节点的下一个节点便于移动
            next = head.next;
            // 老节点的当前节点指向新节点的当前节点 （这一步其实是新节点没办法设置next，因为一直在给他设置parent, 所以）
            head.next = newHead;
            // 将设置好的老的当前节点，赋给新节点的当前节点
            newHead = head;
            // 老节点向后移动
            head = next;
        }
        return newHead;
    }


}
