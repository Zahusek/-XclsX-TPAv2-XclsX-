package com.gmail.zahusek.tinyprotocolapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.zahusek.tinyprotocolapi.api.tabapi.TabAPI;
import com.gmail.zahusek.tinyprotocolapi.api.tabapi.TabListener;
import com.gmail.zahusek.tinyprotocolapi.essential.TinyProtocol;

public class Main extends JavaPlugin {
	
	public static void main(String[] args) {
	}
	
	private static JavaPlugin tinyprotocolapi; 	
	private static TinyProtocol tinyprotocol;
	
	@Override
	public void onEnable() {
		tinyprotocolapi = this;
		tinyprotocol = new TinyProtocol(this);
		getTinyProtocol().addListener(new TabListener());
		Bukkit.getPluginManager().registerEvents(new TabListener(), this);
		for(Player player : Bukkit.getOnlinePlayers())
			TabAPI.defaultTab(player);
	}
	public static TinyProtocol getTinyProtocol(){
		return tinyprotocol;
	}
	public static JavaPlugin getTinyProtocolAPI(){
		return tinyprotocolapi;
	}
}
