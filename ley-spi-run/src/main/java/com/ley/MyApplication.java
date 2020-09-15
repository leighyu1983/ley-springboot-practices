package com.ley;

import com.gh.ISay;

import java.util.ServiceLoader;

public class MyApplication {

	public void test(int[] a) {
		a[0] = 1;
	}

	public void test2() {
		int[] a = new int[2];
		a[0] = 10;
		a[1] = 9;
		this.test(a);
		System.out.print(a);
	}



	public static void main(String[] args) throws InterruptedException {

		new MyApplication().test2();

		// Thread.currentThread().getContextClassLoader()
		ServiceLoader<ISay> iSays = ServiceLoader.load(ISay.class);
		System.out.println("MyApplication:    " + MyApplication.class.getClassLoader().toString());
		try {
			System.out.println("Main Thread-current:     " + Thread.currentThread().getClass().getClassLoader().toString());
		} catch (NullPointerException ex) {
			System.out.println("Main Thread-current:     " + " bootstrap loader");
		}

		System.out.println("Main Thread-notUnderstand():     " + Thread.currentThread().getContextClassLoader().toString());
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Sub MyApplication:      " + MyApplication.class.getClassLoader().toString());
				//System.out.println("Sub Thread:" + Thread.currentThread().getClass().getClassLoader().toString());
				System.out.println("Sub Thread-notUnderstand():      " + Thread.currentThread().getContextClassLoader().toString());
			}
		}).start();

		for(ISay iSay : iSays) {
			System.out.println(iSay.hi());
		}
		Thread.sleep(100 * 1000);
	}
}
