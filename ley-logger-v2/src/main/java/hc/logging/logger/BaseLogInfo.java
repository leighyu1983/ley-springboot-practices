package hc.logging.logger;

import java.util.Arrays;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public abstract class BaseLogInfo {
    protected String module;
    protected String subModule;
    protected boolean triggerAtOnce;

    protected boolean existDuplicateValueExceptNull(String... args) {
       return Arrays.asList(args).stream()
               // null is not included
               .filter(x -> x != null)
               // get count for each group.
               .collect(groupingBy(x -> x, counting()))
               // exist grouped item count > 1
               .values().stream().anyMatch(x -> x > 1);
    }
}
