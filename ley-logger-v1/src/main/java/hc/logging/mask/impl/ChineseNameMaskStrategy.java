package hc.logging.mask.impl;

import hc.logging.mask.MaskStrategy;
import hc.logging.mask.MaskStrategyBase;
import hc.logging.mask.constant.Constant;

public class ChineseNameMaskStrategy extends MaskStrategyBase implements MaskStrategy {

	/**
	 * Refer to rule described in {@link MaskStrategy}
	 * TODO test
	 *
	 * @param value
	 * @return
	 */
	public String mask(String value) {

		if(super.isSkipMask(value)) {
			return value;
		}

		if(value.length() == 1) {
			return value;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(value.charAt(0));

		for(int i = 1; i < value.length(); i++) {
			sb.append(Constant.MASK_CHAR);
		}

		return sb.toString();
	}
}
