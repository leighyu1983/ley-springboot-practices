package com.ley.scenario.run;

import com.ley.scenario.httppool.MyHttpConfigurationProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.FutureTask;


@RestController
@Slf4j
public class HttpPoolController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private MyHttpConfigurationProperty property;
	@Autowired
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

	@Data
	private class User {
		private String name;
	}

	@GetMapping("/http/print")
	public String print() {
		return property.toString();
	}

	@GetMapping("/http/status")
	public PoolStats status() {
		PoolStats ps = poolingHttpClientConnectionManager.getTotalStats();
		return ps;
	}

	@GetMapping("/http/r")
	public String r() {

		for (int i = 10; i > 0; i--) {
			FutureTask<String> ft = new FutureTask<>(
					() -> {
						String tName = Thread.currentThread().getName();
						System.out.println("sender thread " + tName + " is sent.");
						String r = null;

						try {
							r = restTemplate.getForObject(
									"http://localhost:8082/http/slow/" + tName, String.class);
						} catch (Exception ex) {
							ex.printStackTrace();
							r = ex.toString();
						}
						System.out.println("sender thread " + tName + " state: " +
								poolingHttpClientConnectionManager.getTotalStats());
						System.out.println("sender thread " + tName + " got return value" + r);
						return r;
					});
			new Thread(ft).start();
		}

		return "done...";
	}

	@GetMapping("/http/slow/{a}")
	public String slow(@PathVariable("a")String a) throws InterruptedException {
		System.out.println("receiver thread got..." + a);
		Thread.sleep(10 * 1000);
		return "sleep 10s..." + a;
	}
}
