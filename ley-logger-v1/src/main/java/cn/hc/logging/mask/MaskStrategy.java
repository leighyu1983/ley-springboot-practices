package cn.hc.logging.mask;

public interface MaskStrategy {
	/**
	 * Mask logic, please refer to {@link cn.hc.logging.MaskTypeEnum}
	 *
	 */
	String mask(String value);

}
