package com.ley;

import java.io.*;
import java.util.Properties;

public class ResourceLoader {

	private final static String DEFAULT_RESOURCE_FILE = "application.properties";

	/**
	 *
	 *
	 * @return
	 * @throws IOException
	 */
	public static Properties load() throws IOException {
		return load(DEFAULT_RESOURCE_FILE);
	}

	public static Properties load(String filename) throws IOException {
		InputStream in = null;
		Properties properties = null;

		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			properties = new Properties();
			properties.load(in);
		} finally {
			if(in != null) {
				in.close();
			}
		}
		return properties;
	}


}


