package hc.logging.type;

import hc.logging.MaskTypeEnum;

import java.lang.annotation.*;

/**
 * Not support collection/map types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MaskField {
    MaskTypeEnum type();
}
