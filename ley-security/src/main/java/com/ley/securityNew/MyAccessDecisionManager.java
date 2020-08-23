package com.ley.securityNew;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;


@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while(ite.hasNext()) {
			ConfigAttribute ca = (SecurityConfig)ite.next();
			String role = ca.getAttribute();
			for(GrantedAuthority ga : authentication.getAuthorities()) {
				if(ga.getAuthority().equals(role)) {
					return;
				}
			}
		}

		throw new AccessDeniedException("没有权限访问");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
