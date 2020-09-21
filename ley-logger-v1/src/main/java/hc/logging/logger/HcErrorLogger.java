package hc.logging.logger;

import hc.logging.type.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;


public class HcErrorLogger {

	private final static Logger LOGGER = LoggerFactory.getLogger(HcErrorLogger.class);

	public void error() {
		error(null);
	}

	public void error(String message) {
		error(message, null, null);
	}

	public void error(String message, String param1, Object value1) {
		error(message, param1, value1,null, null);
	}

	public void error(String message, String param1, Object value1, String param2, Object value2) {
		error(message, param1, value1, param2, value2, null, null);
	}

	public void error(String message, String param1, Object value1, String param2, Object value2, String param3, Object value3) {
		String throwableStr = getThrowableStr(this.throwable);

		List<KeyValuePair<String, Object>> customizedParts = Arrays.asList(
				new KeyValuePair<String, Object>(param1, value1),
				new KeyValuePair<String, Object>(param2, value2),
				new KeyValuePair<String, Object>(param3, value3)
		);

		LOGGER.error(
				"\"{" +
						"'module':'{}', " +
						"'submodule':'{}', " +
						"'triggerAtOnce':'{}', " +
						"'throwableStr':'{}', " +
						"'message':'{}'" +
						getCustomizablePartsJson(customizedParts) +
						"}\""
				, module, subModule, triggerAtOnce, throwableStr, message);
	}

	/**
	 * TODO test null.
	 *
	 * @param pairs
	 * @return
	 */
	private String getCustomizablePartsJson(List<KeyValuePair<String, Object>> pairs) {
		StringBuilder sb = new StringBuilder();

		for(KeyValuePair<String, Object> pair : pairs) {
			sb.append(String.format(", '{}':'{}'", pair.getKey(), pair.getValue()));
		}

		return sb.toString();
	}


	private String getThrowableStr(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
	}

	private String module;
	private String subModule;
	private boolean triggerAtOnce;
	private Throwable throwable;

	private HcErrorLogger(Builder builder) {
		this.module = builder.module;
		this.subModule = builder.subModule;
		this.triggerAtOnce = builder.triggerAtOnce;
		this.throwable = builder.throwable;
	}

	public static class Builder {
		private String module;
		private String subModule;
		private boolean triggerAtOnce;
		private Throwable throwable;


		public HcErrorLogger build() {
			return new HcErrorLogger(this);
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

		public Builder throwable(Throwable throwable) {
			this.throwable = throwable;
			return this;
		}
	}
}
