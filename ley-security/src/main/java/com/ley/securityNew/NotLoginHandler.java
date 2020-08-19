package com.ley.securityNew;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class NotLoginHandler implements AuthenticationEntryPoint {
	/**
	 * 用户未登录返回结果
	 * @Author Sans
	 * @CreateTime 2019/10/3 9:01
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("401", "没有登陆");

		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out = response.getWriter();
			out.println(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			log.error("【JSON输出异常】"+e);
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}
	}
}

