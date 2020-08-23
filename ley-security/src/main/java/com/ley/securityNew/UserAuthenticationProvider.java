package com.ley.securityNew;

import com.ley.entity.Person;
import com.ley.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private MyUserService myUserService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 获取表单输入中返回的用户名
		String userName = (String) authentication.getPrincipal();
		// 获取表单中输入的密码
		String password = (String) authentication.getCredentials();
		// 查询用户是否存在
		Person userInfo = myUserService.loadUserByUsername(userName);
		if (userInfo == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
//		// 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
//		if (!new BCryptPasswordEncoder().matches(password, userInfo.getPassword())) {
//			throw new BadCredentialsException("密码不正确");
//		}

		if(!userInfo.getUsername().equals(userName) || !userInfo.getPassword().equals(password)) {
			throw new BadCredentialsException("密码不正确");
		}

		// 还可以加一些其他信息的判断，比如用户账号已停用等判断
		if (userInfo.getStatus().equals("PROHIBIT")){
			throw new LockedException("该用户已被冻结");
		}
		// 角色集合
		//userinfo fetch and set authorities here.
		// 进行登录
		return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
	}
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}

