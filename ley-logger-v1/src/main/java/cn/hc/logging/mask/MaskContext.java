package cn.hc.logging.mask;

import cn.hc.logging.MaskTypeEnum;
import cn.hc.logging.mask.impl.*;


/**
 * Strategy context
 *
 */
public class MaskContext {

	private MaskStrategy strategy;

	// cannot initialize without param.
	private MaskContext() {

	}

	public MaskContext(MaskTypeEnum type) {
		switch (type) {
			case BANK_CARD:
				strategy = new BankCardMaskStrategy();
				break;
			case CHINESE_NAME:
				strategy = new ChineseNameMaskStrategy();
				break;
			case EMAIL:
				strategy = new EmailMaskStrategy();
				break;
			case ID_CARD:
				strategy = new IdCardMaskStrategy();
				break;
			case PHONE:
				strategy = new PhoneMaskStrategy();
				break;
			default:
				throw new IllegalArgumentException(String.format("MaskTypeEnum: %s is not supported.", type.toString()));
		}
	}

	public String mask(String value) {
		return strategy.mask(value);
	}
}
