package cn.hc.logging;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HcMaskField {
    /**
     * Field type to be masked.
     *
     * @return
     */
    MaskTypeEnum type();
}
