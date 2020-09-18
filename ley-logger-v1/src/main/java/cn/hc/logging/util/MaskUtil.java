package cn.hc.logging.util;


public class MaskUtil {

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
		if(end > start) {
			throw new IllegalArgumentException(
					String.format("end offset: %d, is larger than start offset: %d", end, start));
		}

		// no enough chars to mask.
		if(value == null || value.length() < end) {
			return value;
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
