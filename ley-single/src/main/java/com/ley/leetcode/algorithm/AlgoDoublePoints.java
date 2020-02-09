package com.ley.leetcode.algorithm;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Leigh Yu
 * @date 2020/2/8 15:00
 * https://github.com/CyC2018/CS-Notes/blob/master/notes/Leetcode%20%E9%A2%98%E8%A7%A3%20-%20%E5%8F%8C%E6%8C%87%E9%92%88.md
 */
public class AlgoDoublePoints {

    /**
     * 167. Two Sum II - Input array is sorted (Easy)
     *
     * Date: 2020-2-9
     *
     * Input: numbers={2, 7, 11, 15}, target=9
     * Output: indexes [1, 2]
     *
     * 使用双指针，一个指针指向值较小的元素，一个指针指向值较大的元素。
     * 指向较小元素的指针从头向尾遍历，指向较大元素的指针从尾向头遍历。
     *
     * 如果两个指针指向元素的和 sum == target，那么得到要求的结果；
     * 如果 sum > target，移动较大的元素，使 sum 变小一些；
     * 如果 sum < target，移动较小的元素，使 sum 变大一些。
     *
     * 时间复杂度 O(1)  空间复杂度O(1)
     *
     */
    public static int[] twoSumInSortedArray(int[] numbers, int target) {
        if(numbers == null || numbers.length == 0) {
            return null;
        }

        // 头指针
        int headIndex = 0;
        // 尾指针
        int tailfIndex = numbers.length -1;

        while(headIndex < tailfIndex) {
            int sum = numbers[headIndex] + numbers[tailfIndex];
            if(sum > target) {
                tailfIndex--;
            } else if( sum <  target) {
                headIndex++;
            } else {
                return new int[] {headIndex + 1, tailfIndex + 1};
            }
        }

        return null;
    }

    /**
     * 633. Sum of Square Numbers (Easy)
     *
     * Date: 2020-2-9
     *
     * 两数平方和
     * 可以看成是在元素为 0~target 的有序数组中查找两个数，使得这两个数的平方和为 target，如果能找到，则返回 true，表示 target 是两个整数的平方和。
     *
     *  时间复杂度为 O(sqrt(target))  空间复杂度为 O(1)
     * @param target
     * @return
     */
    public static boolean sumOfSquareNumbers(int target) {
        if (target < 0) {
            return false;
        }

        int i = 0, j = (int) Math.sqrt(target);
        while(i <= j) {
            int powSum = i * i + j * j;
            // 不可变 》 可变
            if(target > powSum) {
                // 可变的增大
                i++;
            } else if(target < powSum) {
                j--;
            } else {
                // equal
                return true;
            }
        }

        return false;
    }

    /**
     * 345. Reverse Vowels of a String (Easy)
     *
     * Date: 2020-2-9
     *
     * 使用双指针，一个指针从头向尾遍历，一个指针从尾到头遍历，当两个指针都遍历到元音字符时，交换这两个元音字符。
     *
     * 时间复杂度 O(N)    空间复杂度 O(1)
     *
     * @param s
     * @return
     */
    public static String reverseVowelsOfString(String s) {
        final HashSet<Character> vowels = new HashSet<>(
                Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

        // 参数判断
        if (s == null) {
            return null;
        }

        // 定义头尾指针
        int i = 0, j = s.length() - 1;
        // 最终结果存放
        char[] result = new char[s.length()];
        while(i <= j) {
            char ci = s.charAt(i);
            char cj = s.charAt(j);

            if(!vowels.contains(ci)) {
                result[i++] = ci;
            } else if (!vowels.contains(cj)) {
                result[j--] = cj;
            } else {
                // 两个都是元音的时候，交换。
                result[i++] = cj;
                result[j--] = ci;
            }
        }

        return new String(result);
    }

    /**
     * 680. Valid Palindrome II (Easy)
     *
     * Date: 2020-2-9
     *
     * 题目：给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串。
     * 示例 1:
     *
     * 输入: “aba”
     * 输出: True
     * 示例 2:
     *
     * 输入: “abca”
     * 输出: True
     * 解释: 你可以删除c字符。
     *
     * 思路：验证回文串采用双指针，如果整体是回文串，直接true; 如果不是，那么就要根据题意分析，是左边删除一个还是右边删除一个。
     * 所以需要写一个函数对左右在进行一次判断，只要有一个为true。那么说明删除一个元素后可以是回文子串。
     *
     * @param s
     * @return
     */
    public static boolean isValidPalindrome(String s) {
        if(s == null) {
            return false;
        }

        if(s.length() == 1) {
            return true;
        }

        int i = 0, j = s.length() -1;

        while(i < j) {
            // 如果两侧的字符一致 当前是回文，左右指针挪动，判断下一次。
            if(s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
                continue;
            } else {
              // 尝试删除一个字符(左右各式一次，只要满足就可以)，在测试回文。
                return isValidPalindrome(s, ++i, j) || isValidPalindrome(s, i, --j);
            }
        }
        return true;
    }

    /**
     * 第二次判断
     */
    private static boolean isValidPalindrome(String s, int i, int j) {
        while(i < j) {
            // 如果两侧的字符一致 当前是回文，左右指针挪动，判断下一次。
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    
}
