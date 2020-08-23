package com.ley.securityNew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
//@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;
	@Autowired
	private NotLoginHandler notLoginHandler;
	@Autowired
	private LoginFailHandler loginFailHandler;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;
	@Autowired
	private UrlFilterSecurityMetaSource urlFilterSecurityMetaSource;
	@Autowired
	private MyAccessDecisionManager myAccessDecisionManager;


	@Override
	protected void configure(AuthenticationManagerBuilder auth){
		//登陆验证逻辑
		auth.authenticationProvider(userAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//不进行权限验证的请求或资源(从配置文件中读取)
				.antMatchers("/my-index").permitAll()
				//其他的需要登陆后才能访问
				.anyRequest().authenticated()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						object.setSecurityMetadataSource(urlFilterSecurityMetaSource);
						object.setAccessDecisionManager(myAccessDecisionManager);
						return object;
					}
				})
				.and()
				//配置未登录自定义处理类
				.httpBasic().authenticationEntryPoint(notLoginHandler)
				.and()
				//配置登录地址
				.formLogin()
				.loginProcessingUrl("/my-login")
				//配置登录成功自定义处理类
				.successHandler(loginSuccessHandler)
				//配置登录失败自定义处理类
				.failureHandler(loginFailHandler)
				.and()
				//配置登出地址
				.logout()
				.logoutUrl("/login/userLogout")
				//配置用户登出自定义处理类
//				.logoutSuccessHandler(userLogoutSuccessHandler)
				.and()
				//配置没有权限自定义处理类
				.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler)
				.and()
				// 开启跨域
				.cors()
				.and()
				// 取消跨站请求伪造防护
				.csrf().disable();
		// 基于Token不需要session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// 禁用缓存
		http.headers().cacheControl();
		// 添加JWT过滤器, 继承自BasicAuthenticationFilter extends OncePerRequestFilter
		http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
	}
}
