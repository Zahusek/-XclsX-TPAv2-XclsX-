package com.gmail.zahusek.tinyprotocolapi.essential;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.gmail.zahusek.tinyprotocolapi.annotations.PacketHandler;
import com.gmail.zahusek.tinyprotocolapi.interfaces.PacketListener;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class Overlay {

	public static final Map<PacketListener, List<Method>> OUT = Maps
			.newHashMap();
	public static final Map<PacketListener, List<Method>> IN = Maps
			.newHashMap();

	public void addListener(PacketListener listener) {
		List<Method> out = Lists.newArrayList(), in = Lists.newArrayList();
		for (Method method : listener.getClass().getDeclaredMethods()) {

			Class<?>[] value = method.getParameterTypes();
			if (value.length != 1 || !PacketEvent.class.equals(value[0]))
				continue;

			PacketHandler type = method.getAnnotation(PacketHandler.class);
			if (type == null)
				continue;

			if (type.packet().hasOut())
				out.add(method);
			else if (type.packet().hasIn())
				in.add(method);

			else
				continue;
		}
		if (!out.isEmpty())
			OUT.put(listener, out);
		if (!in.isEmpty())
			IN.put(listener, in);
	}
}
