package com.gmail.zahusek.tinyprotocolapi.nms;

import java.util.UUID;

import com.gmail.zahusek.tinyprotocolapi.abstracts.NMSCover;
import com.gmail.zahusek.tinyprotocolapi.annotations.NMSHandler;
import com.gmail.zahusek.tinyprotocolapi.enums.Gamemode;

@NMSHandler(name = "PacketPlayOutPlayerInfo$PlayerInfoData")
public class PlayerInfoData extends NMSCover {

	int ping;
	private Gamemode mode;
	private String name, displayname;

	public PlayerInfoData(int ping, Gamemode mode, String name,
			String displayname) {
		super(0, null, new GameProfile(UUID.randomUUID(), name), ping, mode
				.getValue(), ChatSerializer.toRaw(displayname));
		this.ping = ping;
		this.mode = mode;
		this.name = name;
		this.displayname = displayname;
	}
	public String getName() {
		return name;
	}
	public void setDisplayname(String displayname) {
		$(3, ChatSerializer.toRaw(displayname));
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setGamemode(Gamemode mode) {
		$(1, mode.getValue());
	}

	public Gamemode getGamemode() {
		return mode;
	}
	public int getPing() {
		return ping;
	}
	public void setPing(int ping) {
		$(0, ping);
	}
}
