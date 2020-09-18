package cn.hc.logging;

public enum MaskTypeEnum {

	/**
	 * If content length >= 8,
	 *      Mask content in [the last but 8 digit,  the last but 5 digit]
	 * If content length >= 4 and length < 8,
	 *      Mask content in [the last but length of content digit, the last but (length of content digit - 4) digit]
	 * If content length < 4,
	 *      Content won't be masked.
	 *
	 * E.g. 62220289674123 -> 622202****4123; 674123 -> ****23; 123 -> 123
	 */
	BANK_CARD,

	/**
	 * Mask content from second digit to last digit to *, content won't be masked if there is only one char.
	 * E.g. 王小明 -> 王**; 李明 -> 李*; 李 -> 李
	 */
	CHINESE_NAME,

	/**
	 * NOTE: username is the part before 'at' sign in email.
	 *
	 * If length of username >= 3
	 *      Mask content between first digit and last digit of username.
	 * If length of username = 2
	 *      Mask the second char of username.
	 * If length of username <= 1
	 *      Content won't be masked.
	 *
	 * E.g. yeyongy@163.com -> y*****y@163.com; ye@163.com -> y*@163.com; y@163.com -> y@163.com
	 */
	EMAIL,

	/**
	 * Mask content from last 8 digit to last 4 digit to *, logic is similar as BANK_CARD.
	 * E.g. 42100207221012 -> 421002****1012
	 */
	ID_CARD,

	/**
	 * Mask content from last 8 digit to last 4 digit to *, logic is similar as BANK_CARD.
	 * E.g. 13852344213 -> 138****4213;  010 83554213 -> 010 ****4213
	 */
	PHONE
}
