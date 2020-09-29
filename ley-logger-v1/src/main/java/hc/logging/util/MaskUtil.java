package hc.logging.util;

import hc.logging.MaskTypeEnum;
import hc.logging.mask.MaskContext;


public class MaskUtil {

	public static String maskBankCard(String value) {
		return new MaskContext(MaskTypeEnum.BANK_CARD).mask(value);
	}

	public static String maskChineseName(String value) {
		return new MaskContext(MaskTypeEnum.CHINESE_NAME).mask(value);
	}

	public static String maskEmail(String value) {
		return new MaskContext(MaskTypeEnum.EMAIL).mask(value);
	}

	public static String maskIdCard(String value) {
		return new MaskContext(MaskTypeEnum.ID_CARD).mask(value);
	}

	public static String maskPhone(String value) {
		return new MaskContext(MaskTypeEnum.PHONE).mask(value);
	}

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
	 */
	public static String mask(String value, int start, int end, char c) {
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
