package com.gmail.zahusek.tabtest;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.zahusek.api.tabapi.TabAPI;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach((player) -> {
					updateTab(player);
				});
			}
		}.runTaskTimer(this, 20L, 5L);
	}
	public void updateTab(Player p){
		
		TabAPI.setTabString(p, 1, 1, "    " +toRainbow(p.getName()));
		TabAPI.modifyTab(p);
	}
	public String $(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e){
		Player p = e.getPlayer();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				TabAPI.setTabSettings(p, 3, 20, 0);
				
				TabAPI.setTabString(p, 0, 0, $("&6o&eO&6o&eO&6o&eOO&6o&eO&6o&eO&6o"));
				TabAPI.setTabString(p, 1, 0, $("&6o&eO&6o&eO&6o&eOO&6o&eO&6o&eO&6o"));
				TabAPI.setTabString(p, 2, 0, $("&6o&eO&6o&eO&6o&eOO&6o&eO&6o&eO&6o"));
				
				TabAPI.setTabString(p, 0, 1, $("&6o&eO&6o&eO&6o&eOO&6o&eO&6o&eO&6o"));
				TabAPI.setTabString(p, 2, 1, $("&6o&eO&6o&eO&6o&eOO&6o&eO&6o&eO&6o"));
				
				TabAPI.setTabString(p, 0, 2, $("&6o&eO&6o&eO&6o&eOO&6o&eO&6o&eO&6o"));
				TabAPI.setTabString(p, 1, 2, $("&6o&eO&6o&eO&6o&eOO&6o&eO&6o&eO&6o"));
				TabAPI.setTabString(p, 2, 2, $("&6o&eO&6o&eO&6o&eOO&6o&eO&6o&eO&6o"));
				TabAPI.modifyTab(p);
			}
		}.runTaskLaterAsynchronously(this, 20L);
	}
	private static final char[] COLORS = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	Random rand = new Random();
	public String toRainbow(String name) {
		return ChatColor.translateAlternateColorCodes('&', "&" + COLORS[rand.nextInt(COLORS.length)] + name);
	}
}
