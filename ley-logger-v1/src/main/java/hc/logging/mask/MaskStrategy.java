package hc.logging.mask;

import hc.logging.MaskTypeEnum;

public interface MaskStrategy {
	/**
	 * Mask logic, please refer to {@link MaskTypeEnum}
	 *
	 */
	String mask(String value);

}
