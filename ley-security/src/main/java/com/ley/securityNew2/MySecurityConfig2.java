package com.ley.securityNew2;

import com.ley.securityNew.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


/**
 * AuthenticationEntryPoint 登陆失败回调
 * OncePerRequestFilter 每次访问都会回调
 * BasicAuthenticationFilter 继承自 OncePerRequestFilter, 但是用户名和密码明文在header中，
 * 		当一个HTTP请求中包含一个名字为Authorization的头部，并且其值格式是Basic xxx时，
 * 		该Filter会认为这是一个BASIC authorization头部，
 * 		其中xxx部分应该是一个base64编码的{username}:{password}字符串。
 * 		比如用户名/密码分别为 admin/secret, 则对应的该头部是 : Basic YWRtaW46c2VjcmV0 。
 *
 * AuthenticationManagerBuilder
 * 		userDetailsService, 该方法的参数 UserDetailsService接口， 只提供根据用户名校验。
 * 		authenticationProvider,  该方法的参数 AuthenticationProvider 接口，可以获取用户传入的用户名和密码，通过抛出对应的异常处理。
 *
 *
 * UserAuthenticationProvider 比
 *
 * AuthenticationManager 中规范了 Spring Security 的过滤器要如何执行身份认证，
 * 		并在身份认证成功后返回一个经过认证的 Authentication 对象。
 *
 *
 *
 *
 *
 */
//@EnableWebSecurity
public class MySecurityConfig2 extends WebSecurityConfigurerAdapter {

	@Autowired private UserAuthenticationProvider userAuthenticationProvider;

	// 处理登陆
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(userAuthenticationProvider);
	}

	// 不进行权限验证的请求或资源(从配置文件中读取)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/my-index", "/my-test/**");
	}

	// 处理非登陆的其他请求
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//					@Override
//					public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//						o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
//						o.setAccessDecisionManager(urlAccessDecisionManager);
//						return o;
//					}
//				})
//				//配置未登录自定义处理类
//				.httpBasic().authenticationEntryPoint(notLoginHandler)
//				.and()
//				//配置登录地址
//				.formLogin()
//				.loginProcessingUrl("/my-login")
//				//配置登录成功自定义处理类
//				.successHandler(loginSuccessHandler)
//				//配置登录失败自定义处理类
//				.failureHandler(loginFailHandler)
//				.and()
//				//配置登出地址
//				.logout()
//				.logoutUrl("/login/userLogout")
//				//配置用户登出自定义处理类
////				.logoutSuccessHandler(userLogoutSuccessHandler)
////				.and()
////				//配置没有权限自定义处理类
////				.exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
//				.and()
//				// 开启跨域
//				.cors()
//				.and()
//				// 取消跨站请求伪造防护
//				.csrf().disable();
//		// 基于Token不需要session
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		// 禁用缓存
//		http.headers().cacheControl();
	}
}
