package com.gmail.zahusek.tinyprotocolapi.api.tabapi;

import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.gmail.zahusek.tinyprotocolapi.abstracts.PacketCover;
import com.google.common.collect.Maps;

public class TabAPI {

	protected static final Map<UUID, TabManager> LIST = Maps.newHashMap();

	private static TabManager getTab(Player p) {
		if (LIST.get(p.getUniqueId()) == null)
			LIST.put(p.getUniqueId(), new TabManager());
		return LIST.get(p.getUniqueId());
	}
	
	public static void setTabSlot(Player player, int x, int y, String msg, int ping) {
		getTab(player).setTabSlot(x, y, msg, ping);
	}
	public static void setTabHnF(Player player, String header, String footer) {
		getTab(player).setTabHnF(header, footer);
	}
	public static void modifyTab(Player player) {
		TabManager tab = getTab(player);
		for (PacketCover packet : tab.getRefreshTab())
			packet.handle(player);
	}
	public static void defaultTab(Player player) {
		TabManager tab = getTab(player);
		for (PacketCover packet : tab.getDefaultTab())
			packet.handle(player);
	}
	protected static void reloadTab(Player player) {
		TabManager tab = getTab(player);
		for (PacketCover packet : tab.getReload())
			packet.handle(player);
	}
}
