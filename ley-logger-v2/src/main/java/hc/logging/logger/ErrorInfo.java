package hc.logging.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ErrorInfo extends BaseLogInfo {
    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorInfo.class);

    public void error(Throwable throwable) {
        error(null, null, null, throwable);
    }

    public void error(String message) {
        error(message, null, null, null);
    }

    public void error(String message, Throwable throwable) {
        error(message, null, null, throwable);
    }

    public void error(String message, String param1, Object value1, Throwable throwable) {
        error(message, param1, value1,null, null, throwable);
    }

    /**
     * Throw runtime exception if param1, param2have the same value.
     *
     */
    public void error(String message, String param1, Object value1, String param2, Object value2, Throwable throwable) {
        if(super.existDuplicateValueExceptNull(param1, param2)) {
            throw new IllegalArgumentException(
                    String.format("should not exist duplicated keys, param1:%s param2:%s", param1, param2));
        }

        error(message, param1, value1, param2, value2, null, null, throwable);
    }

    /**
     * Throw runtime exception if param1, param2, param3 have the same value.
     *
     */
    public void error(String message, String param1, Object value1, String param2, Object value2, String param3, Object value3, Throwable throwable) {

        if(super.existDuplicateValueExceptNull(param1, param2, param3)) {
            throw new IllegalArgumentException(
                    String.format("should not exist duplicated keys, param1:%s param2:%s param3:%s", param1, param2, param3));
        }

        Map<String, Object> extendedParams = new HashMap<>(3);
        if(param1 != null) extendedParams.put(param1, value1);
        if(param2 != null) extendedParams.put(param2, value2);
        if(param3 != null) extendedParams.put(param3, value3);

        errorCore(message, extendedParams, throwable);
    }

    public void error(String message, Map<String, ?> extendedParams, Throwable throwable) {
        errorCore(message, extendedParams, throwable);
    }

    private void errorCore(String message, Map<String, ?> params, Throwable throwable) {
        Map<String, ?> extendedParams = params == null || params.size() == 0 ? null : params;

        LOGGER.error(
                "{" +
                        "\"module\":\"{}\", " +
                        "\"subModule\":\"{}\", " +
                        "\"triggerAtOnce\":\"{}\", " +
                        "\"message\":\"{}\", " +
                        "\"extended\":\"{}\"" +
                        "}"
                , module, subModule, triggerAtOnce, message, extendedParams, throwable);
    }



    private ErrorInfo(Builder builder) {
        super.module = builder.module;
        super.subModule = builder.subModule;
        super.triggerAtOnce = builder.triggerAtOnce;
    }

    public static class Builder {
        private String module;
        private String subModule;
        private boolean triggerAtOnce;


        public ErrorInfo build() {
            return new ErrorInfo(this);
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
