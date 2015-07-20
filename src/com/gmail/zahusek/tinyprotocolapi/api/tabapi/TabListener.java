package com.gmail.zahusek.tinyprotocolapi.api.tabapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import com.gmail.zahusek.tinyprotocolapi.annotations.PacketHandler;
import com.gmail.zahusek.tinyprotocolapi.enums.Packet;
import com.gmail.zahusek.tinyprotocolapi.essential.PacketEvent;
import com.gmail.zahusek.tinyprotocolapi.interfaces.PacketListener;

public class TabListener implements PacketListener, Listener {
	
	@PacketHandler(packet = Packet.PacketPlayOutLogin)
	public void $1(PacketEvent e) {
		TabAPI.defaultTab(e.getPlayer());
	}
	@EventHandler
	public void $2(PlayerQuitEvent e) {
		TabAPI.LIST.remove(e.getPlayer().getUniqueId());
	}
	@EventHandler
	public void $3(PlayerKickEvent e) {
		TabAPI.LIST.remove(e.getPlayer().getUniqueId());
	}
	@EventHandler
	public void $3(PluginDisableEvent e) {
		for(Player player : Bukkit.getOnlinePlayers())
		TabAPI.reloadTab(player);
	}
}
