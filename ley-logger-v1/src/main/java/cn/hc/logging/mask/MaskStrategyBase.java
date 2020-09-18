package cn.hc.logging.mask;

import cn.hc.logging.util.MaskUtil;

public abstract class MaskStrategyBase {

    protected String maskScopeFlexible(String value, int start, int end, char c) {
        // Don't mask if no enough char.
        if(value == null || value.length() <= (end - start + 1)) {
           return value;
        }

        // normal case
        if(value.length() >= end ) {
            return MaskUtil.mask(value, start, end, c);
        }

        /**
         * flexible case:
         * mask (end - start + 1) chars from the last but value.length digit.
         *
         * E.g.
         * start=5, end=8, value=011012
         * result: 011012 -> ****12
         */
        return MaskUtil.mask(value, value.length() - 1 - (end - start), value.length() - 1, c);
    }
}
