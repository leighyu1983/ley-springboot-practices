package com.ley.scenario.httppool;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * TODO
 * 1. support non-customized rest template.
 * 2. support
 */
@Slf4j
@EnableConfigurationProperties(MyHttpConfigurationProperty.class)
@Configuration
public class MyRestTemplateConfiguration implements InitializingBean {

	private MyHttpConfigurationProperty property;
	public MyRestTemplateConfiguration(MyHttpConfigurationProperty property) {
		this.property = property;
	}

	@Bean
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy () {
		return (response, context) -> {
			HeaderElementIterator it = new BasicHeaderElementIterator
					(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
			while (it.hasNext()) {
				HeaderElement he = it.nextElement();
				String param = he.getName();
				String value = he.getValue();
				if (value != null && param.equalsIgnoreCase("timeout")) {
					return Long.parseLong(value) * 1000;
				}
			}
			return 5 * 1000;
		};
	}


	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(property.getMaxTotal());
		connectionManager.setDefaultMaxPerRoute(property.getMaxPerRoute());

		// Customize per route
		//connectionManager.setMaxPerRoute();
		return connectionManager;
	}

	// should not use @Bean since it impacts RestTemplate
	@Bean
	public HttpClient httpClient(
			PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
			ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
		return httpClientBuilder.build();
	}

	@Bean
	public ClientHttpRequestFactory requestFactory(HttpClient httpClient) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		// connect to server timeout
		requestFactory.setConnectTimeout(property.getConnectionTimeoutMs());
		// socket timeout
		// SocketTimeoutException: Read timed out
		requestFactory.setReadTimeout(property.getReadTimeoutMs());
		// get request from pool timeout
		// Timeout waiting for connection from pool
		requestFactory.setConnectionRequestTimeout(property.getConnectionRequestTimeoutMs());

		return requestFactory;
	}

	/**
	 * !!! no connection pool
	 * @see org.springframework.http.client.SimpleClientHttpRequestFactory
	 * !!! it does.
	 * @see org.springframework.http.client.HttpComponentsClientHttpRequestFactory
	 *
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory) {
		return new RestTemplate(requestFactory);
	}

	@Override
	public void afterPropertiesSet() {
		log.info("Http pool configuration properties: {}", property);
	}
}
