package com.ley.securityNew;

import com.ley.entity.Person;
import com.ley.util.JwtTokenUtils;
import com.ley.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	/**
	 * 登录成功返回结果
	 * @Author Sans
	 * @CreateTime 2019/10/3 9:27
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
		// 组装JWT
		Person selfUserEntity =  (Person) authentication.getPrincipal();
		//String token = JWTTokenUtil.createAccessToken(selfUserEntity);
		String token = JwtTokenUtils.generateToken(authentication);

		// 封装返回参数
		Map<String,Object> resultData = new HashMap<>();
		resultData.put("code","200");
		resultData.put("msg", "登录成功");
		resultData.put("token",token);
		ResultUtil.responseJson(response, resultData);
	}
}

