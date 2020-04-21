package com.ley.register;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(LeyMapperScannerRegistrar.class)
public @interface LeyMapperScan {
	@AliasFor("value")
	String[] basePackages() default {};

	@AliasFor("basePackages")
	String[] value() default {};
}
