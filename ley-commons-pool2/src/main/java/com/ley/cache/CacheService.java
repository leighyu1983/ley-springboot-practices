package com.ley.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CacheService {
	@Autowired private StringRedisTemplate template;

	public String get(String k) {
		return template.opsForValue().get(k);
	}

	public void set(String k, String v) {
		template.opsForValue().set(k, v);
	}
}
