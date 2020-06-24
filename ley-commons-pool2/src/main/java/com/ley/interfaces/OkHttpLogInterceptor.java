package com.ley.interfaces;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class OkHttpLogInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		log.info("OkHttpUrl : " + chain.request().url() + "  CurrentThread: " + Thread.currentThread().getName());
		return chain.proceed(chain.request());
	}
}
