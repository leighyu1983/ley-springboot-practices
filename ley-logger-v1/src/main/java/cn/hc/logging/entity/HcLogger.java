package cn.hc.logging.entity;

import org.slf4j.Logger;
/**
 * Please note
 * 1. The object passed in as parameter should implement toString().
 * 2. slf4j-api dependency should be added in project; slf4j-api is automatically included by springboot.
 * 3. If to be printed value is an object, only print the first level fields.
 *
 */
public class HcLogger {

	/**
	 * Cannot initialize
	 */
	private HcLogger() {
		Logger log;
		log.debug();
	}


	public static class Builder {

	}


}
