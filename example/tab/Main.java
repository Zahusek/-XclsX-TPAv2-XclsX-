package com.gmail.zahusek.test;

import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.zahusek.tinyprotocolapi.api.tabapi.TabAPI;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers())
					getTab(player);
			}
		}.runTaskTimer(this, 0, 5);
	}
	public void getTab(Player player) {
		IntStream.range(0, 20).forEach((y) -> IntStream.range(0, 4).forEach((x) -> {
			setTabSlot(player, x, y, ChatColor.translateAlternateColorCodes('&', "&8##########"));
		}));
		setTabSlot(player, 1, 1, ChatColor.translateAlternateColorCodes('&', "&6Wlecome"));
		setTabSlot(player, 2, 1, ChatColor.translateAlternateColorCodes('&', "&a" + player.getName()));
		TabAPI.setTabHnF(player, ChatColor.translateAlternateColorCodes('&', "&aHeader"), ChatColor.translateAlternateColorCodes('&', "&6Footer"));
		TabAPI.modifyTab(player);
	}
	public void setTabSlot(Player player, int x, int y, String msg){
		TabAPI.setTabSlot(player, x, y, msg, 0);
	}

}
