package com.gmail.zahusek;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.zahusek.protocol.PacketListener;
import com.gmail.zahusek.protocol.TinyProtocol;

public class Main extends JavaPlugin {
	
	private TinyProtocol manager = new TinyProtocol(this);
	
	@Override
	public void onEnable() {
		manager.register();
	}
	
	public void registerPacketListener(){
		manager.addListener(new PacketListener() {/**/});
	}
}
