package com.ley.configuration;


import com.ley.interfaces.OkHttpLogInterceptor;
import feign.Client;
import feign.okhttp.OkHttpClient;
import okhttp3.ConnectionPool;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@AutoConfigureBefore(FeignAutoConfiguration.class)
@EnableFeignClients(basePackages = "com.ley")
public class OkHttpConfig {

	private okhttp3.OkHttpClient okHttpClient;

	@Bean
	public Client feignClient(okhttp3.OkHttpClient client) {
		return new OkHttpClient(client);
	}

	@Bean
	public okhttp3.OkHttpClient okHttpClient() {
		return new okhttp3.OkHttpClient.Builder()
				//设置连接超时
				.connectTimeout(10, TimeUnit.SECONDS)
				//设置读超时
				.readTimeout(10, TimeUnit.SECONDS)
				//设置写超时
				.writeTimeout(10, TimeUnit.SECONDS)
				//是否自动重连
				.retryOnConnectionFailure(false)
				.addInterceptor(new OkHttpLogInterceptor())
				.connectionPool(new ConnectionPool(2, 5L, TimeUnit.MINUTES))
				.build();
	}
}

