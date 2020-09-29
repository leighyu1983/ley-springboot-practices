package hc.logging.mask;

public class MaskUtil {

    private final static char MASK_CHAR = '*';

    /**
     * At least parameter to be masked contains 4 chars, otherwise, it won't be masked.
     *
     * @param value
     * @return
     */
    public static String maskBankCard(String value) {
        // from right to left
        return maskFlexible(value, 4, 7);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String maskChineseName(String value) {
        // won't mask if empty string or only one char
        if(isEmpty(value) || value.length() == 1) {
            return value;
        }

        return mask(value, 1, value.length() -1);
    }

    public static String maskEmail(String value) {
        if(isEmpty(value) || !value.contains("@")) {
            return value;
        }

        /**
         * abc@email.com
         * username section is abc
         */
        String username = value.split("@")[0];

        if(username.length() == 1) {
            return value;
        }

        if(username.length() == 2) {
            return mask(value, 1, 1);
        }

        return mask(value, 1, username.length() - 2);
    }

    public static String maskIdCard(String value) {
        // from right to left
        return maskFlexible(value, 4, 7);
    }

    /**
     * Mask content from last 8 digit to last 4 digit to *, logic is similar as BANK_CARD.
     * E.g. 13852344213 -> 138****4213;  010 83554213 -> 010 ****4213
     */
    public static String maskPhone(String value) {
        // from right to left
        return maskFlexible(value, 4, 7);
    }





    private static String maskFlexible(String value, int start, int end) {
        return maskFlexible(value, start, end, MASK_CHAR);
    }

    private static String maskFlexible(String value, int start, int end, char c) {
        // Don't mask if no enough char.
        if(isSkipMask(value, start, end)) {
            return value;
        }

        // normal case
        if(value.length() >= end ) {
            return MaskUtil.mask(value, value.length() - end -1 , value.length() - start - 1, c);
        }

        /**
         * flexible case:
         * mask (end - start + 1) chars from the last but value.length digit.
         *
         * E.g.
         * start=5, end=8, value=011012
         * result: 011012 -> ****12
         */
        return MaskUtil.mask(value, 0, end - start, c);
    }

    private static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    private static boolean isSkipMask(String value, int start, int end) {
        if(isEmpty(value)) {
            return true;
        }

        if(value.length() <= (end - start + 1)) {
            return true;
        }

        return false;
    }

    private static String mask(String value, int start, int end) {
        return mask(value, start, end, MASK_CHAR);
    }

    /**
     * Return original value if string to be masked is null or less than end offset.
     *
     */
    private static String mask(String value, int start, int end, char c) {
        // throw exception if validation failed.
        validateParams(start, end);

        // no enough chars to mask.
        if(value == null || value.length() < end) {
            return value;
        }

        StringBuilder sb = new StringBuilder(value);
        String replacement = String.join("", repeatString(end - start + 1, c));
        return sb.replace(start, end + 1, replacement).toString();
    }

    private static void validateParams(int start, int end) {
        if(start < 0 || end < 0 ) {
            throw new IllegalArgumentException(
                    String.format("parameter start:%d or end:%d is less than zero, which should larger than zero", end, start));
        }

        if(start > end) {
            throw new IllegalArgumentException(
                    String.format("parameter end: %d, is larger than start: %d", end, start));
        }
    }

    private static String repeatString(int cnt, char c) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < cnt; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
