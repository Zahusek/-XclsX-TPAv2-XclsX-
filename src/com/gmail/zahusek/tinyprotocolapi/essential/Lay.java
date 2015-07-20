package com.gmail.zahusek.tinyprotocolapi.essential;

import com.gmail.zahusek.tinyprotocolapi.utility.Reflection;
import com.gmail.zahusek.tinyprotocolapi.utility.Reflection.Accessor;

public class Lay {

	private Object object;
	private Accessor[] accessor;

	public Lay(Object object) {
		this.object = object;
		this.accessor = Reflection.getField(object.getClass());
	}

	public void set(int id, Object value) {
		accessor[id].set(object, value);
	}

	public Object get(int id) {
		return accessor[id].get(object);
	}

	public Object returned() {
		return object;
	}
}
