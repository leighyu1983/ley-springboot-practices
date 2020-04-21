package com.ley.test.leetcode.datastructure;

import com.ley.leetcode.datastructure.ListNodePractice;
import com.ley.leetcode.support.MyListNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Leigh Yu
 * @date 2020/4/12 21:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ListNodePracticeTest {
    @Test
    public void testReverseVowelsOfString() {

        MyListNode<String> a = new MyListNode<String>("a", null);
        MyListNode<String> b = new MyListNode<String>("b", null);
        MyListNode<String> c = new MyListNode<String>("c", null);
        MyListNode<String> d = new MyListNode<String>("d", null);
        MyListNode<String> e = new MyListNode<String>("e", null);

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;

        MyListNode<String> t = a;

        while (t != null) {
            log.info("------>" + t.data);
            t = t.next;
        }

        log.info("------------------->");
        MyListNode<String> newNode = ListNodePractice.reverseNodeList(a);

        while (newNode != null) {
            log.info("------>" + newNode.data);
            newNode = newNode.next;
        }

    }

    @Test
    public void testReverseBetween() {

        MyListNode<String> a = new MyListNode<String>("a", null);
        MyListNode<String> b = new MyListNode<String>("b", null);
        MyListNode<String> c = new MyListNode<String>("c", null);
        MyListNode<String> d = new MyListNode<String>("d", null);
        MyListNode<String> e = new MyListNode<String>("e", null);
        MyListNode<String> f = new MyListNode<String>("f", null);
        MyListNode<String> g = new MyListNode<String>("g", null);
        MyListNode<String> h = new MyListNode<String>("h", null);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
        f.next = g;
        g.next = h;

        MyListNode<String> t = a;

        while (t != null) {
            log.info("------>" + t.data);
            t = t.next;
        }

        log.info("------------------->");
        MyListNode<String> newNode = ListNodePractice.reverseBetween(a, 3, 6);

        while (newNode != null) {
            log.info("------>" + newNode.data);
            newNode = newNode.next;
        }
    }
}
