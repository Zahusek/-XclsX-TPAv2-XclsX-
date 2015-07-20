package com.gmail.zahusek.tinyprotocolapi.enums;

import com.gmail.zahusek.tinyprotocolapi.utility.Reflection;

public enum Gamemode {

	DQFUQ(0), SURVIVAL(1), CREATIVE(2), ADVENTURE(3), SPECTATOR(4);

	public final Object[] eg = Reflection.getClass(
			"{nms}.WorldSettings$EnumGamemode").getEnumConstants();
	public final Object value;

	Gamemode(int value) {
		this.value = eg[value];
	}

	public Object getValue() {
		return value;
	}
}
