package com.ley.tools;

import com.ley.pojo.CodeGeneratorCallable;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class VerifyCodeGenerator {

	private final static String[] VERIFY_STRING =
			new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };


	public String getVerifyCode(int index, int digits) {

		Random random = new Random(); // System.currentTimeMillis()
		StringBuilder verifyBuilder = new StringBuilder();
		for (int i = 0; i < digits; i++) {
			int rd = random.nextInt(10);
			verifyBuilder.append(VERIFY_STRING[rd]);
		}
		String verifyCode = verifyBuilder.toString();
		log.info("--->" + index + "---->" + verifyCode);
		return verifyCode;
	}

	public static String getVerifyCodeMultipleThreading(int digits) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 60,
				TimeUnit.SECONDS, new LinkedBlockingQueue<>(90));

		CountDownLatch latch = new CountDownLatch(100);
		for(int i = 0; i < 100; i++) {
			executor.submit(new CodeGeneratorCallable(latch, i));
			latch.countDown();
		}

		return "done";
	}
}
