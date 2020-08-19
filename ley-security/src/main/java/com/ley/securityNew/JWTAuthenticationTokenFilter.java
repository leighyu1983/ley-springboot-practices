package com.ley.securityNew;

import com.ley.data.SampleData;
import com.ley.entity.Person;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {
	public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 获取请求头中JWT的Token
		//String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
		String tokenHeader = request.getHeader("Authorization");
//		if (null != tokenHeader && tokenHeader.startsWith(JWTConfig.tokenPrefix)) {
		if (null != tokenHeader && tokenHeader.startsWith("ley-")) {
			try {
				// 截取JWT前缀
				String tokenUsername = tokenHeader.replace("ley-", "");
//				// 解析JWT
//				Claims claims = Jwts.parser()
//						.setSigningKey("JWTSecret") // JWTConfig.secret
//						.parseClaimsJws(token)
//						.getBody();
//				// 获取用户名
//				String username = claims.getSubject();
//				String userId = claims.getId();

				Person person = SampleData.getPerson(tokenUsername).get();
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(person, person.getId(), person.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);

//				if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(userId)) {
//					// 获取角色
//					List<GrantedAuthority> authorities = new ArrayList<>();
//					String authority = claims.get("authorities").toString();
//					if (!StringUtils.isEmpty(authority)) {
//						List<Map<String, String>> authorityMap = JSONObject.parseObject(authority, List.class);
//						for (Map<String, String> role : authorityMap) {
//							if (!StringUtils.isEmpty(role)) {
//								authorities.add(new SimpleGrantedAuthority(role.get("authority")));
//							}
//						}
//					}
//					//组装参数
//					SelfUserEntity selfUserEntity = new SelfUserEntity();
//					selfUserEntity.setUsername(claims.getSubject());
//					selfUserEntity.setUserId(Long.parseLong(claims.getId()));
//					selfUserEntity.setAuthorities(authorities);
//					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(selfUserEntity, userId, authorities);
//					SecurityContextHolder.getContext().setAuthentication(authentication);
//				}
			} catch (ExpiredJwtException e) {
				log.info("Token过期");
			} catch (Exception e) {
				log.info("Token无效");
			}
		}
		filterChain.doFilter(request, response);
		return;
	}
}