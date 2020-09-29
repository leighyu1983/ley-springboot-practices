package test;

import hc.logging.logger.HcErrorInfo;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

public class ErrorTest {

    private void errorMethod() {
        int i = 0;
        int j = 1;
        int k = j / i;
    }

    @Before
    public void preparation() {
        MDC.put("requestId", "hi-request-id-cool");
    }

    @Test
    public void testThrowException() {

        HcErrorInfo hcErrorLogger = new HcErrorInfo.Builder()
                .module("testModule")
                .subModule("subTestModule")
                .triggerAtOnce(true)
                .build();

        try {
            errorMethod();
        } catch (Exception ex) {
            hcErrorLogger.error(ex);
        }
    }

    @Test
    public void testThrowErrorMessage() {

        HcErrorInfo hcErrorLogger = new HcErrorInfo.Builder()
                .module("testModule")
                .subModule("subTestModule")
                .triggerAtOnce(true)
                .build();

        try {
            errorMethod();
        } catch (Exception ex) {
            hcErrorLogger.error("divide zero scenario");
        }
    }

    @Test
    public void testThrowErrorMessageException() {

        HcErrorInfo hcErrorLogger = new HcErrorInfo.Builder()
                .module("testModule")
                .subModule("subTestModule")
                .triggerAtOnce(true)
                .build();

        try {
            errorMethod();
        } catch (Exception ex) {
            hcErrorLogger.error("divide zero scenario - error with exception", ex);
        }
    }

    @Test
    public void testThrowErrorCustomized() {

        HcErrorInfo hcErrorLogger = new HcErrorInfo.Builder()
                .module("testModule")
                .subModule("subTestModule")
                .triggerAtOnce(true)
                .build();

        try {
            errorMethod();
        } catch (Exception ex) {
            hcErrorLogger.error("divide zero scenario - error with exception", "key1", "value1", ex);
        }
    }

    @Test
    public void testThrowErrorCustomized2() {

        HcErrorInfo hcErrorLogger = new HcErrorInfo.Builder()
                .module("testModule")
                .subModule("subTestModule")
                .triggerAtOnce(true)
                .build();

        try {
            errorMethod();
        } catch (Exception ex) {
            hcErrorLogger.error("divide zero scenario - error with exception",
                    "key1", "value1",
                    "key2", "value2",
                    ex);
        }
    }

    @Test
    public void testThrowErrorCustomized3() {

        HcErrorInfo hcErrorLogger = new HcErrorInfo.Builder()
                .module("testModule")
                .subModule("subTestModule")
                .triggerAtOnce(true)
                .build();

        try {
            errorMethod();
        } catch (Exception ex) {
            hcErrorLogger.error("divide zero scenario - error with exception",
                    "key1", "value1",
                    "key2", "value2",
                    "key3", "value3",
                    ex);
        }
    }
}
