package com.ley.pojo;

import com.ley.tools.VerifyCodeGenerator;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CodeGeneratorCallable implements Callable<Integer> {

	private int index;
	private CountDownLatch latch;

	public CodeGeneratorCallable(CountDownLatch latch, int index) {
		this.index = index;
		this.latch = latch;
	}

	@Override
	public Integer call() throws Exception {
		latch.await();
		new VerifyCodeGenerator().getVerifyCode(index, 4);
		return index;
	}
}
