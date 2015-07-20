package com.gmail.zahusek.tinyprotocolapi.abstracts;

import java.lang.reflect.Constructor;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.zahusek.tinyprotocolapi.Main;
import com.gmail.zahusek.tinyprotocolapi.annotations.PacketHandler;
import com.gmail.zahusek.tinyprotocolapi.utility.Reflection;
import com.gmail.zahusek.tinyprotocolapi.utility.Reflection.Accessor;
import com.gmail.zahusek.tinyprotocolapi.utility.Reflection.ConstructorInvoker;

public abstract class PacketCover {

	private PacketHandler handler;
	protected Object packet;
	protected Accessor[] fields;

	public PacketCover() {
		this.handler = getClass().getAnnotation(PacketHandler.class);
		this.packet = Reflection.getConstructor(
				"{nms}." + handler.packet().getName()).invoke();
		this.fields = Reflection.getField(packet.getClass());
	}

	public PacketCover(int id, Object... obs) {
		this.handler = getClass().getAnnotation(PacketHandler.class);
		Constructor<?>[] cons = Reflection.getClass("{nms}." + handler.packet().getName())
				.getConstructors();
		ConstructorInvoker con = Reflection.getConstructor(
				"{nms}." + handler.packet().getName(), cons[id].getParameterTypes());
		this.packet= con.invoke(obs);
		this.fields = Reflection.getField(packet.getClass());

	}

	protected void $(int id, Object value) {
		fields[id].set(packet, value);
	}

	protected void $(int id) {
		fields[id].get(packet);
	}

	public void handle(Player player) {
		if (handler.packet().hasOut()) {
			Main.getTinyProtocol().sendPacket(player, packet);
			return;
		} else if (handler.packet().hasIn()) {
			Main.getTinyProtocol().receivePacket(player, packet);
			return;
		}
		return;
	}

	public void handleAll() {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		synchronized (players) {
			players.forEach(this::handle);
		}
		return;
	}
}
