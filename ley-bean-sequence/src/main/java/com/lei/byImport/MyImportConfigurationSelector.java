package com.lei.byImport;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.lei.byImport.EnableMyImport.InterceptMode;
import static com.lei.byImport.EnableMyImport.InterceptMode.PROXY_METHOD;

public class MyImportConfigurationSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnableMyImport.class.getName(), false));
		InterceptMode mode = attributes.getEnum("interceptMode");
		if (mode == PROXY_METHOD) {
			// TODO ??? to enable apo ????
			//return new String[]{AutoProxyRegistrar.class.getName(), MyMethodProxyConfiguration.class.getName()};
			return new String[]{ MyMethodProxyConfiguration.class.getName() };
		} else {
			throw new UnsupportedOperationException("Unknown mode " + mode);
		}

	}
}
