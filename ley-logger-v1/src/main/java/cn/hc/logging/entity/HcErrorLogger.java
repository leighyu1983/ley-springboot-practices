package cn.hc.logging.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HcErrorLogger {

	private final static Logger LOGGER = LoggerFactory.getLogger(HcErrorLogger.class);

	private HcErrorLogger() { }

	public HcErrorLogger build() {
		return null;
	}

	public void log() {
		LOGGER.error();
	}

	private String module;
	private String subModule;
	private boolean triggerAtOnce;
	private Throwable throwable;
	private String requestId;

	public static class Builder {
		private String module;
		private String subModule;
		private boolean triggerAtOnce;
		private String requestId;
		private Throwable throwable;

		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		public String getSubModule() {
			return subModule;
		}
		public void setSubModule(String subModule) {
			this.subModule = subModule;
		}
		public boolean isTriggerAtOnce() {
			return triggerAtOnce;
		}
		public void setTriggerAtOnce(boolean triggerAtOnce) {
			this.triggerAtOnce = triggerAtOnce;
		}
		public Throwable getThrowable() {
			return throwable;
		}
		public void setThrowable(Throwable throwable) {
			this.throwable = throwable;
		}
		public String getRequestId() {
			return requestId;
		}
		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}
	}
}
