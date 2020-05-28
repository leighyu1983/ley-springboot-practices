package com.ley.lcode.datastructure;


import com.ley.lcode.support.MyListNode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Leigh Yu
 * @date 2020/4/12 20:47
 */
@Slf4j
public class ListNodePractice {

    /**
     * 单链表反转
     *
     * @param head
     * @return
     */
    public static MyListNode<String> reverseNodeList(MyListNode<String> head) {
        if(head == null || head.next == null) {
            return head;
        }

        MyListNode<String> newHead = null;
        MyListNode<String> tmpNext = null;

        // 缓存老节点的后一个节点，老节点指向新节点，将弄好的老节点(有了新指向)赋给新节点，老节点向后移动
        while(head != null) {
            // 备份当前节点的下一个节点便于移动
            tmpNext = head.next;
            // 老节点的当前节点指向新节点的当前节点 （这一步其实是新节点没办法设置next，因为一直在给他设置parent, 所以）
            head.next = newHead;
            // 将设置好的老的当前节点，赋给新节点的当前节点
            newHead = head;
            // 老节点向后移动
            head = tmpNext;
        }
        return newHead;
    }

    /**
     * 单链表部分反转 LeetCode92
     *
     * @param head
     * @return
     */
    public static MyListNode<String> reverseBetween(MyListNode<String> head, int m, int n) {
        log.info("from {} - to {}", m, n);
        if (head == null || head.next == null || m == n) {
            return head;
        }

        // dummy: 新节点， 其结构为  | null | head node |
        MyListNode<String> dummy = new MyListNode<String>();
        dummy.next = head;

        // pre: 用于遍历的节点，指向要遍历的前一个节点
        MyListNode<String> pre = dummy;
        for ( int i = 1; i < m ; i++) {
            pre = pre.next;
        }

        // pcur: 要遍历的当前节点
        MyListNode<String> pcur = pre.next;

        for (int i = m; i < n; i++) {
            // pnext: 当前节点的next节点
            MyListNode<String> pnext = pcur.next;
            pcur.next = pcur.next.next; // temp.next;
            pnext.next = pre.next;
            pre.next = pnext;
            log.info("round {}", i - m);
        }
        return dummy.next;
    }
}
