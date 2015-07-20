package com.gmail.zahusek.tinyprotocolapi.essential;

import org.bukkit.entity.Player;

public class PacketEvent {

	private Player player;
	private Object packet;
	private Lay laypacket;

	public PacketEvent(Player player, Object packet) {
		this.player = player;
		this.packet = packet;
		this.laypacket = new Lay(packet);
	}

	public Player getPlayer() {
		return player;
	}

	public Object getPacket() {
		return packet;
	}

	public void setPacket(Object packet) {
		this.packet = packet;
	}

	public Lay getLayPacket() {
		return laypacket;
	}

	public void setLayPacket(Lay laypacket) {
		this.laypacket = laypacket;
	}

	public void setCancelled(boolean cancel) {
		if (cancel)
			this.packet = null;
	}
}
