package com.ley.scenario.serialization;

import org.springframework.web.bind.annotation.RestController;

import java.io.*;


public class SerializeDemo {

	private final static String PATH_WITHOUT = "D:\\tmp\\serialization\\serializeObjWithout";
	private final static String PATH_WITH = "D:\\tmp\\serialization\\serializeObjWith";

	public static void serializeWithoutSerialization() {
		SerializeObjWithoutSerialization obj = new SerializeObjWithoutSerialization();
		obj.setAge(10);
		obj.setName("Tom");

		try {
			FileOutputStream fileOut =
					new FileOutputStream(PATH_WITHOUT);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(obj);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Throws java.io.NotSerializableException: com.ley.scenario.serialization.SerializeObj
	 */
	public static void deserializeWithoutSerialization() {
		SerializeObjWithoutSerialization obj = null;
		try {
			FileInputStream fileIn = new FileInputStream(PATH_WITHOUT);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			obj = (SerializeObjWithoutSerialization) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.print(obj);
	}

	public static void serializeWithSerialization() {
		SerializeObjWithSerialization obj = new SerializeObjWithSerialization();
		obj.setAge(10);
		obj.setName("Tom");

		try {
			FileOutputStream fileOut =
					new FileOutputStream(PATH_WITH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(obj);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static void deserializeWithSerialization() {
		SerializeObjWithSerialization obj = null;
		try {
			FileInputStream fileIn = new FileInputStream(PATH_WITH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			obj = (SerializeObjWithSerialization) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.print(obj);
	}


}
