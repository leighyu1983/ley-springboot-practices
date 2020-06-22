package com.ley.controller;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/agent")
public class AgentController {

	@SneakyThrows
	@GetMapping("loop")
	public String ping() {
		for (int i = 0; i < 3; i++) {
			System.out.println("i: " + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println(e);
			}
		}

		URL url = new URL("http://www.baidu.com");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		byte[] data = toByteArray(inStream);
		String result = new String(data, "UTF-8");
		System.out.println("=============finish requesting============");
		System.out.println(result);


		return "1";
	}

	private static byte[] toByteArray(InputStream input) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	@SneakyThrows
	@GetMapping("/me")
	public String me(HttpServletRequest request) {
		String h = request.getHeader("ley");
		String v = "requesting.....me....." + callme1() + "   added request header is ->" + h;
		System.out.println(v);
		return v;
	}

	private String callme1() {
		String v = "calling me...1....";
		System.out.println(v);
		return v;
	}

	@SneakyThrows
	@GetMapping("/cool")
	public String cool() {
		Request request = new Request.Builder()
				.url("http://localhost:8080/agent/me")
				.get()
				.build();

		OkHttpClient client = new OkHttpClient
				.Builder()
				.connectTimeout(60, TimeUnit.SECONDS)

				.writeTimeout(60, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS)
				.build();

		String result = "";
		Response response = client.newCall(request).execute();
		result = response.body().string();

		System.out.println("get result....");
		return result;
	}
}
