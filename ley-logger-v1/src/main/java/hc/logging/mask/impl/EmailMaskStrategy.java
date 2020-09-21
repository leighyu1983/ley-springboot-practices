package hc.logging.mask.impl;

import hc.logging.mask.MaskStrategy;
import hc.logging.mask.MaskStrategyBase;
import hc.logging.mask.constant.Constant;

public class EmailMaskStrategy extends MaskStrategyBase implements MaskStrategy {

	public String mask(String value) {
		if(super.isSkipMask(value) || !value.contains("@")) {
			return value;
		}

		/**
		 * abc@email.com
		 * username section is abc
		 */
		String username = value.split("@")[0];

		if(super.isSkipMask(username)) {
			return value;
		}

		if(username.length() == 1) {
			return value;
		}

		StringBuilder sb = new StringBuilder(value);
		if(username.length() == 2) {
			return sb.replace(1,2, String.valueOf(Constant.MASK_CHAR)).toString();
		}

		return sb.replace(1, username.length(), String.valueOf(Constant.MASK_CHAR)).toString();
	}
}
