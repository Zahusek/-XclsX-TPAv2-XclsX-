package com.gmail.zahusek.tinyprotocolapi.enums;

import com.gmail.zahusek.tinyprotocolapi.utility.Reflection;

public enum PlayerInfoAction {

	ADD(0), GAMEMODE(1), PING(2), DISPLAYNAME(3), REMOVE(4);

	private final Object[] ea = Reflection.getClass(
			"{nms}.PacketPlayOutPlayerInfo$EnumPlayerInfoAction")
			.getEnumConstants();
	public final Object value;

	PlayerInfoAction(int value) {
		this.value = ea[value];
	}

	public Object getValue() {
		return value;
	}
}
