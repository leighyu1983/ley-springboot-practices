package cn.hc.logging.mask;

import cn.hc.logging.MaskTypeEnum;
import cn.hc.logging.exception.NotSupportedException;
import cn.hc.logging.mask.impl.*;


/**
 * Strategy context
 *
 */
public class MaskContext {

	private IMaskStrategy strategy;

	// cannot initialize without param.
	private MaskContext() {

	}

	/**
	 * Throw {@link cn.hc.logging.exception.NotSupportedException} if mask type is not implemented in mask context constructor.
	 *
	 * @param type
	 */
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
				throw new NotSupportedException(
						"cn.hc.logging.mask.MaskContext: mask type enum is not supported:" + type.toString());
		}
	}

	public String mask(String value) {
		return strategy.mask(value);
	}
}
