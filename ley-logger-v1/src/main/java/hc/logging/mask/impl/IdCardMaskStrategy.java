package hc.logging.mask.impl;

import hc.logging.mask.MaskStrategy;
import hc.logging.mask.MaskStrategyBase;
import hc.logging.mask.constant.Constant;

public class IdCardMaskStrategy extends MaskStrategyBase implements MaskStrategy {

	public String mask(String value) {
		return super.maskScopeFlexible(value,
				Constant.MASK_ID_CARD_START_INDEX,
				Constant.MASK_ID_CARD_END_INDEX,
				Constant.MASK_CHAR);
	}
}
