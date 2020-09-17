package cn.hc.logging.mask;

public interface IMaskStrategy {
	/**
	 * Mask Logic:
	 *
	 * 1. If the content is null or white spaces, return original value.
	 * 2. If the content doesn't match the Validation Rules mentioned below, return null;
	 *    We don't trim the value here since it needs to follow the input.
	 *
	 * @return
	 */
	String mask(String value);

	/**
	 * Validation Rules:
	 *  -----------------------------------
	 *  BANK_CARD: Length of the content >= 8
	 *  Chinese_Name: Length of the content >= 2
	 *  Email: At least one char in front of '@'
	 *  IDCard: Length of the content == 18
	 *  Phone: Length of the content >= 8
	 */
	boolean validate(String value);
}
