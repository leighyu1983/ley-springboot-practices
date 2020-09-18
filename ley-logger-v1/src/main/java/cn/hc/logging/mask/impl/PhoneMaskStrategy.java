package cn.hc.logging.mask.impl;

import cn.hc.logging.mask.MaskStrategy;
import cn.hc.logging.mask.MaskStrategyBase;
import cn.hc.logging.mask.constant.Constant;

public class PhoneMaskStrategy extends MaskStrategyBase implements MaskStrategy {

	/**
	 * Refer to rule described in {@link cn.hc.logging.mask.MaskStrategy}
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
