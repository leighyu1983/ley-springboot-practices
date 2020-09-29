package hc.logging.logger;

import hc.logging.type.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class HcErrorInfo {

	private final static Logger LOGGER = LoggerFactory.getLogger(HcErrorInfo.class);

	public void error(Throwable throwable) {
		error(null, null, null, throwable);
	}

	public void error(String message) {
		error(message, null, null, null);
	}

	public void error(String message, Throwable throwable) {
		error(message, null, null, throwable);
	}

	public void error(String message, Map<String, Object> extendedParams, Throwable throwable) {

	}

	public void error(String message, String param1, Object value1, Throwable throwable) {
		error(message, param1, value1,null, null, throwable);
	}

	public void error(String message, String param1, Object value1, String param2, Object value2, Throwable throwable) {
		error(message, param1, value1, param2, value2, null, null, throwable);
	}

	public void error(String message, String param1, Object value1, String param2, Object value2, String param3, Object value3, Throwable throwable) {

		List<KeyValuePair<String, Object>> customizedParts = Arrays.asList(
				new KeyValuePair<>(param1, value1),
				new KeyValuePair<>(param2, value2),
				new KeyValuePair<>(param3, value3)
		);

		LOGGER.error(
				"\"{" +
						"'module':'{}', " +
						"'subModule':'{}', " +
						"'triggerAtOnce':'{}', " +
						"'message':'{}'" +
						getCustomizablePartsJson(customizedParts) +
						"}\""
				, module, subModule, triggerAtOnce, message, throwable);
	}
	
	private String getCustomizablePartsJson(List<KeyValuePair<String, Object>> pairs) {
		StringBuilder sb = new StringBuilder();

		pairs.stream()
				// filter out null objects
				.filter(x -> x != null)
				// key or value in each item in pairs cannot be null
				.filter(x -> x.getKey() != null || x.getValue() != null)
				// put each item into StringBuilder
				.forEach(x ->
						sb.append(String.format("'%s':'%s', ", x.getKey(), x.getValue()))
				);

		if(sb.length() == 0) {
			return "";
		}

		return String.format(", 'extended':{%s}", sb.toString().substring(0, sb.length() - 2));
	}

	private String module;
	private String subModule;
	private boolean triggerAtOnce;

	private HcErrorInfo(Builder builder) {
		this.module = builder.module;
		this.subModule = builder.subModule;
		this.triggerAtOnce = builder.triggerAtOnce;
	}

	public static class Builder {
		private String module;
		private String subModule;
		private boolean triggerAtOnce;


		public HcErrorInfo build() {
			return new HcErrorInfo(this);
		}

		public Builder module(String module) {
			this.module = module;
			return this;
		}

		public Builder subModule(String subModule) {
			this.subModule = subModule;
			return this;
		}

		public Builder triggerAtOnce(boolean triggerAtOnce) {
			this.triggerAtOnce = triggerAtOnce;
			return this;
		}
	}
}
