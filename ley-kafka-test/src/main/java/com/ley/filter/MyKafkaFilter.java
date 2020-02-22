package com.ley.filter;

import java.lang.annotation.*;

/**
 * Only work on class level. Customized aspect code loop each methods that has KafkaListener
 * annotation and perform enhance.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyKafkaFilter {
}
