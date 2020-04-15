package com.ley.test.leetcode.datastructure;

import com.ley.leetcode.datastructure.ListNodePractice;
import com.ley.leetcode.support.SingleDirectionNode;
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

        SingleDirectionNode<String> a = new SingleDirectionNode<String>("a", null);
        SingleDirectionNode<String> b = new SingleDirectionNode<String>("b", null);
        SingleDirectionNode<String> c = new SingleDirectionNode<String>("c", null);
        SingleDirectionNode<String> d = new SingleDirectionNode<String>("d", null);
        SingleDirectionNode<String> e = new SingleDirectionNode<String>("e", null);

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;

        SingleDirectionNode<String> t = a;

        while(t != null) {
            log.info("------>" + t.data);
            t = t.next;
        }

        log.info("------------------->");
        SingleDirectionNode<String> newNode = ListNodePractice.reverseNodeList(a);

        while(newNode != null) {
            log.info("------>" + newNode.data);
            newNode = newNode.next;
        }

    }
}
