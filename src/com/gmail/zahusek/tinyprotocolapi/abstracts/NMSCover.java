package com.gmail.zahusek.tinyprotocolapi.abstracts;

import java.lang.reflect.Constructor;

import com.gmail.zahusek.tinyprotocolapi.annotations.NMSHandler;
import com.gmail.zahusek.tinyprotocolapi.utility.Reflection;
import com.gmail.zahusek.tinyprotocolapi.utility.Reflection.*;

public class NMSCover {

	protected Object data;
	protected Accessor[] fields;

	public NMSCover() {
		NMSHandler nms = getClass().getAnnotation(NMSHandler.class);
		this.data = Reflection.getConstructor("{nms}." + nms.name()).invoke();
		this.fields = Reflection.getField(data.getClass());

	}

	public NMSCover(int id, Object... obs) {
		NMSHandler nms = getClass().getAnnotation(NMSHandler.class);
		Constructor<?>[] cons = Reflection.getClass("{nms}." + nms.name())
				.getConstructors();
		ConstructorInvoker con = Reflection.getConstructor(
				"{nms}." + nms.name(), cons[id].getParameterTypes());
		this.data = con.invoke(obs);
		this.fields = Reflection.getField(data.getClass());

	}

	protected void $(int id, Object value) {
		fields[id].set(data, value);
	}

	protected Object $(int id) {
		return fields[id].get(data);
	}

	public Object returned() {
		return data;
	}
}
