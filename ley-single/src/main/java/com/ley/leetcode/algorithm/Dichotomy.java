package com.ley.leetcode.algorithm;

/**
 * Dichotomy [daɪˈkɒtəmi] 二分
 *
 * @author Leigh Yu
 * @date 2020/2/23 18:36
 */
public class Dichotomy {

    /**
     *  Sqrt(x) (Easy)
     *
     * Date: 2020-2-23
     *
     * Input: sqrt(4)
     * Output: 2
     *
     * 在如下开闭区间（ 0，参数/2 ]，判断区间内的数的平方 和 目标数 是否在误差的范围内。
     *
     * 时间复杂度 O(1)  空间复杂度O(1)
     *
     */
    public static double sqrt(double n) {

        if(n == 0 || n == 1) {
            return n;
        }

        // 误差
        double p = 0.0001;
        double min = 1;
        double max = n / 2;

        while(min < max) {
            //mid = (min + max) / 2;
            double mid = min + (max - min) / 2;
            if(mid * mid - p < n && mid * mid + p > n) {
                return mid;
            } else if(mid * mid > n) {
                // 直接从中间，和指针的区别
                max = mid - 1;
            } else {
                // 直接从中间，和指针的区别
                min = mid + 1;
            }
        }

        return max;
    }
}
