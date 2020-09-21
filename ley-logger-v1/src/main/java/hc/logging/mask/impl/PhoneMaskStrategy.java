package hc.logging.mask.impl;

import hc.logging.mask.MaskStrategy;
import hc.logging.mask.MaskStrategyBase;
import hc.logging.mask.constant.Constant;

public class PhoneMaskStrategy extends MaskStrategyBase implements MaskStrategy {

	/**
	 * Refer to rule described in {@link MaskStrategy}
	 *
	 * @param value
	 * @return
	 */
	public String mask(String value) {
		return super.maskScopeFlexible(value,
				Constant.MASK_PHONE_CARD_START_INDEX,
				Constant.MASK_PHONE_CARD_END_INDEX,
				Constant.MASK_CHAR);
	}
}
