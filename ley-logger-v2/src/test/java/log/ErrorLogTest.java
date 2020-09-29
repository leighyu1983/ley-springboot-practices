package log;

import hc.logging.logger.ErrorInfo;
import org.junit.Assert;
import org.junit.Test;
import pojo.User;

import java.util.HashMap;
import java.util.Map;


public class ErrorLogTest {

    private final static String NOT_REACHABLE_CODE = "not reachable code";
    private final static String ERROR_MESSAGE = "error message...";
    private final static String MODULE = "School";
    private final static String SUB_MODULE = "Interest";

    private void exceptionMethod() {
        int i = 1;
        int j = 0;
        int k = i / j;
    }

    @Test
    public void logErrorMessage() {
        ErrorInfo errorInfo = new ErrorInfo.Builder().module(MODULE).subModule(SUB_MODULE).triggerAtOnce(false).build();

        try {
            exceptionMethod();
        }catch (Exception ex) {
            errorInfo.error(ERROR_MESSAGE);
            return;
        }
        Assert.fail(NOT_REACHABLE_CODE);
    }

    @Test
    public void logErrorException() {
        ErrorInfo errorInfo = new ErrorInfo.Builder().module(MODULE).subModule(SUB_MODULE).triggerAtOnce(false).build();

        try {
            exceptionMethod();
        }catch (Exception ex) {
            errorInfo.error(ex);
            return;
        }
        Assert.fail(NOT_REACHABLE_CODE);
    }

    @Test
    public void logErrorMessageAndException() {
        ErrorInfo errorInfo = new ErrorInfo.Builder().module(MODULE).subModule(SUB_MODULE).triggerAtOnce(false).build();

        try {
            exceptionMethod();
        }catch (Exception ex) {
            errorInfo.error(ERROR_MESSAGE);
            return;
        }
        Assert.fail(NOT_REACHABLE_CODE);
    }

    @Test
    public void logErrorExtendedParamsShouldNotSame() {
        ErrorInfo errorInfo = new ErrorInfo.Builder().module(MODULE).subModule(SUB_MODULE).triggerAtOnce(false).build();
        String errorMsg = null;

        try {
            errorInfo.error(ERROR_MESSAGE, "p1", "v1", "p1", "v2", null);
        }catch (Exception ex) {
            errorMsg = ex.getMessage();
        }
        Assert.assertEquals("should not exist duplicated keys, param1:p1 param2:p1", errorMsg);
    }

    @Test
    public void logErrorExtendedParamsShouldNotSame_1() {
        ErrorInfo errorInfo = new ErrorInfo.Builder().module(MODULE).subModule(SUB_MODULE).triggerAtOnce(false).build();
        String errorMsg = null;

        try {
            errorInfo.error(ERROR_MESSAGE, "p1", "v1", "p2", "v2", "p1", "v3", null);
        }catch (Exception ex) {
            errorMsg = ex.getMessage();
        }
        Assert.assertEquals("should not exist duplicated keys, param1:p1 param2:p2 param3:p1", errorMsg);
    }

    @Test
    public void logErrorExtendedParamsNullable() {
        ErrorInfo errorInfo = new ErrorInfo.Builder().module(MODULE).subModule(SUB_MODULE).triggerAtOnce(false).build();
        String errorMsg = null;

        try {
            errorInfo.error(ERROR_MESSAGE, null, "v1", "p2", "v2", null, null, null);
            return;
        }catch (Exception ex) {
            errorMsg = ex.getMessage();
        }
        Assert.fail(NOT_REACHABLE_CODE);
    }

    @Test
    public void logErrorExtendedParamsHash() {
        ErrorInfo errorInfo = new ErrorInfo.Builder().module(MODULE).subModule(SUB_MODULE).triggerAtOnce(false).build();
        String errorMsg = null;
        Map<String, User> extendedParams = new HashMap<>();
        extendedParams.put("c1", new User("u1", "up1"));
        extendedParams.put("c2", new User("u2", "up2"));
        extendedParams.put("c3", new User("u3", "up3"));
        extendedParams.put("c4", new User("u4", "up4"));
        extendedParams.put("c5", new User("u5", "up5"));

        try {
            errorInfo.error(ERROR_MESSAGE, extendedParams, null);
            return;
        }catch (Exception ex) {
            errorMsg = ex.getMessage();
        }
        Assert.fail(NOT_REACHABLE_CODE);
    }
}
