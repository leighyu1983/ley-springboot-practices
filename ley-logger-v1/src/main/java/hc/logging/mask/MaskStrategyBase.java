package hc.logging.mask;

import hc.logging.util.MaskUtil;

public abstract class MaskStrategyBase {

    protected boolean isSkipMask(String value) {
        return value == null || value.length() == 0;
    }

    /**
     * No need to mask if there is no enough char.
     *
     * @param value
     * @param start
     * @param end
     * @return
     */
    protected boolean isSkipMask(String value, int start, int end) {
        if(isSkipMask(value)) {
            return true;
        }

        if(value.length() <= (end - start + 1)) {
            return true;
        }

        return false;
    }

    /**
     * Attention, start / end is the offset from right to left.
     * @param value
     * @param start
     * @param end
     * @param c
     * @return
     */
    protected String maskScopeFlexible(String value, int start, int end, char c) {
        // Don't mask if no enough char.
        if(isSkipMask(value, start, end)) {
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
        return MaskUtil.mask(value, 0, end - start + 1, c);
    }
}
