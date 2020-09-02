package com.ley.scenario.mystring;

import com.ley.scenario.configprocessor.MyStringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyStringSample {
	public static void testOne() {
		String str = "abc";
		String str1 = new String("abc");
		System.out.println("str hashCode:"+str.hashCode());
		System.out.println("str1 hashCode:"+str1.hashCode());
		System.out.println(str.equals(str1));
		System.out.println(str == str1);
	}

	@Autowired private MyStringConfig myStringConfig;
	public void testTwo() {
		System.out.println(myStringConfig.getName());
	}
}
