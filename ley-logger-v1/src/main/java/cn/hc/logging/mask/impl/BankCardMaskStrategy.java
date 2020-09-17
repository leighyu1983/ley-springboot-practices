package cn.hc.logging.mask.impl;

import cn.hc.logging.mask.IMaskStrategy;
import cn.hc.logging.mask.constant.Constant;
import cn.hc.logging.util.MaskUtil;

public class BankCardMaskStrategy implements IMaskStrategy {

	/**
	 * Refer to rule described in {@link cn.hc.logging.mask.IMaskStrategy}
	 *
	 * TODO: Mask the value even it contains leading or trailing spaces.
	 *
	 * @param value
	 * @return
	 */
	public String mask(String value) {

		if(!validate(value)) {
			return null;
		}

		return MaskUtil.mask4To8Digits(value);
	}

	/**
	 * Refer to rule described in {@link cn.hc.logging.mask.IMaskStrategy}
	 *
	 * @param value
	 * @return
	 */
	public boolean validate(String value) {
		return value.length() >= Constant.BANK_CARD_LEAST_LENGTH;
	}
}
