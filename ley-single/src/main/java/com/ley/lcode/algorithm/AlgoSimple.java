package com.ley.lcode.algorithm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Leigh Yu
 * @date 2020/2/7 18:48
 */
@Slf4j
public class AlgoSimple {

    public static void reverseString() {
        String test = "abcdefg";
        log.info(new StringBuilder(test).reverse().toString());

        char[] t = test.toCharArray();
        for(int i = t.length - 1 ; i >= 0; i--) {
            log.info(String.valueOf(t[i]));
        }
    }
}
