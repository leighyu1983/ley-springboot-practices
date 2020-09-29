package hc.logging.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WarnInfo extends BaseLogInfo {
    private final static Logger LOGGER = LoggerFactory.getLogger(WarnInfo.class);

    public void warn(Throwable throwable) {
        warn(null, null, null, throwable);
    }

    public void warn(String message) {
        warn(message, null, null, null);
    }

    public void warn(String message, Throwable throwable) {
        warn(message, null, null, throwable);
    }

    public void warn(String message, String param1, Object value1, Throwable throwable) {
        warn(message, param1, value1,null, null, throwable);
    }

    /**
     * Throw runtime exception if param1, param2have the same value.
     *
     */
    public void warn(String message, String param1, Object value1, String param2, Object value2, Throwable throwable) {
        if(super.existDuplicateValueExceptNull(param1, param2)) {
            throw new IllegalArgumentException(
                    String.format("should not exist duplicated keys, param1:%s param2:%s", param1, param2));
        }

        warn(message, param1, value1, param2, value2, null, null, throwable);
    }

    /**
     * Throw runtime exception if param1, param2, param3 have the same value.
     *
     */
    public void warn(String message, String param1, Object value1, String param2, Object value2, String param3, Object value3, Throwable throwable) {

        if(super.existDuplicateValueExceptNull(param1, param2, param3)) {
            throw new IllegalArgumentException(
                    String.format("should not exist duplicated keys, param1:%s param2:%s param3:%s", param1, param2, param3));
        }

        Map<String, Object> extendedParams = new HashMap<>(3);
        if(param1 != null) extendedParams.put(param1, value1);
        if(param2 != null) extendedParams.put(param2, value2);
        if(param3 != null) extendedParams.put(param3, value3);

        warnCore(message, extendedParams, throwable);
    }

    public void warn(String message, Map<String, ?> extendedParams, Throwable throwable) {
        warnCore(message, extendedParams, throwable);
    }

    private void warnCore(String message, Map<String, ?> params, Throwable throwable) {
        Map<String, ?> extendedParams = params == null || params.size() == 0 ? null : params;

        LOGGER.warn(
                "{" +
                        "\"module\":\"{}\", " +
                        "\"subModule\":\"{}\", " +
                        "\"triggerAtOnce\":\"{}\", " +
                        "\"message\":\"{}\", " +
                        "\"extended\":\"{}\"" +
                        "}"
                , module, subModule, triggerAtOnce, message, extendedParams, throwable);
    }



    private WarnInfo(Builder builder) {
        super.module = builder.module;
        super.subModule = builder.subModule;
        super.triggerAtOnce = builder.triggerAtOnce;
    }

    public static class Builder {
        private String module;
        private String subModule;
        private boolean triggerAtOnce;


        public WarnInfo build() {
            return new WarnInfo(this);
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
