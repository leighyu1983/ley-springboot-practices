package com.ley.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
}
