package cn.hc.logging.util;

import cn.hc.logging.exception.EndLargerThanStartOffsetException;

public class MaskUtil {

	/**
	 * Params validation is done in mask method.
	 *
	 * @param value
	 * @return
	 */
	public static String mask4To8Digits(String value) {
		int startOffset = 4;
		int endOffset = 8;
		return mask(value, startOffset, endOffset);
	}

	public static String mask(String value, int start, int end) {
		return mask(value, start, end, '*');
	}

	/**
	 * Return original value if string to be masked is null or less than end offset.
	 *
	 * @param value
	 * @param start
	 * @param end
	 * @return
	 */
	public static String mask(String value, int start, int end, char c) {
		// no enough chars to mask.
		if(value == null || value.length() < end) {
			return value;
		}

		if(end > start) {
			throw new EndLargerThanStartOffsetException("end offset: " + end + ", is larger than start offset:" + start);
		}

		int len = value.length();
		char[] result = new char[len];
		for(int i = 0; i < len; i++) {
			if(i >= start && i <= end) {
				result[i] = c;
			} else {
				result[i] = value.charAt(i);
			}
		}
		return String.valueOf(result);
	}
}
