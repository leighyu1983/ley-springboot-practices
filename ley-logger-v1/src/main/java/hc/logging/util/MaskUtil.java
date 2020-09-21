package hc.logging.util;


public class MaskUtil {

	/**
	 *
	 * @param value
	 * @param start offset from right to left, start from zero
	 * @param end offset from right to left, start from zero
	 * @return
	 */
	public static String mask(String value, int start, int end) {
		return mask(value, start, end, '*');
	}

	/**
	 * Return original value if string to be masked is null or less than end offset.
	 *
	 * @param value
	 * @param start offset from right to left, start from zero
	 * @param end offset from right to left, start from zero
	 * @return
	 */
	public static String mask(String value, int start, int end, char c) {
		// throw exception if validation failed.
		validateParams(start, end);

		// no enough chars to mask.
		if(value == null || value.length() < end) {
			return value;
		}

		StringBuilder sb = new StringBuilder(value);
		return sb.replace(start, end + 1, String.valueOf(c)).toString();
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
}
