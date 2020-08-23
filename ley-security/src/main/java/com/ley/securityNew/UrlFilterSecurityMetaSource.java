package com.ley.securityNew;

import com.ley.data.SampleData;
import com.ley.entity.Permission;
import com.ley.entity.Role;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UrlFilterSecurityMetaSource implements FilterInvocationSecurityMetadataSource {

	private final AntPathMatcher matcher = new AntPathMatcher();

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		FilterInvocation fi = (FilterInvocation) object;
		List<String> matchedRoles = new ArrayList<>();

		for(Role role : SampleData.getRoles()) {
			for(Permission permission : role.getPermissions()) {
				if(matcher.match(permission.getUrl(), fi.getRequestUrl())) {
					matchedRoles.add(role.getName());
				}
			}
		}

		String[] arrays = null;
		if(matchedRoles.size() > 0) {
			arrays = matchedRoles.toArray(new String[matchedRoles.size()]);
		} else  {
			arrays = new String[]{ "USER_ROLE" };
		}
		// null：不需要权限就可以访问
		//return null;
		return SecurityConfig.createList(arrays);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
