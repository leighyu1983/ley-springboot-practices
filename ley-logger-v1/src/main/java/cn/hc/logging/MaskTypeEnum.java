package cn.hc.logging;

public enum MaskTypeEnum {
	/**
	 * Mask content from last 8 digit to last 4 digit to *
	 * E.g. 62220289674123 -> 622202****4123
	 */
	BANK_CARD,
	/**
	 * Mask content from second digit to last digit to *
	 * E.g. 王小明 -> 王**; 李明 -> 李*
	 */
	CHINESE_NAME,
	/**
	 * Mask content between first digit and last digit in email account
	 * E.g. yeyongy@163.com -> y*****y@163.com
	 */
	EMAIL,
	/**
	 * Mask content from last 8 digit to last 4 digit to *
	 * E.g. 42100207221012 -> 421002****1012
	 */
	ID_CARD,
	/**
	 * Mask content from last 8 digit to last 4 digit to *
	 * E.g. 13852344213 -> 138****4213;  010 83554213 -> 010 ****4213
	 */
	PHONE
}
