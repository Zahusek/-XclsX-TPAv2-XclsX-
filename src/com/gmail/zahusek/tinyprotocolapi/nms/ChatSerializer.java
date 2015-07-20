package com.gmail.zahusek.tinyprotocolapi.nms;

import com.gmail.zahusek.tinyprotocolapi.utility.Reflection;

public class ChatSerializer {

	public static Object toRaw(String msg) {
		return Reflection.getTypedMethod(
				Reflection.getClass("{nms}.IChatBaseComponent$ChatSerializer"),
				"a", Reflection.getClass("{nms}.IChatBaseComponent"),
				String.class).invoke(null, "{text:'" + msg + "'}");
	}

	public static String toPlain(Object obs) {
		String plain = (String) Reflection.getTypedMethod(
				Reflection.getClass("{nms}.IChatBaseComponent$ChatSerializer"),
				"a", String.class,
				Reflection.getClass("{nms}.IChatBaseComponent")).invoke(null,
				obs);
		return plain.substring(1, plain.lastIndexOf('"'));
	}
}
