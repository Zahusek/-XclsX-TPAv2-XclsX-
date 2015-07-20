package com.gmail.zahusek.tinyprotocolapi.api.tabapi;

import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.ChatColor;

import com.gmail.zahusek.tinyprotocolapi.abstracts.PacketCover;
import com.gmail.zahusek.tinyprotocolapi.enums.Gamemode;
import com.gmail.zahusek.tinyprotocolapi.enums.PlayerInfoAction;
import com.gmail.zahusek.tinyprotocolapi.nms.PlayerInfoData;
import com.gmail.zahusek.tinyprotocolapi.packet.PlayerInfo;
import com.gmail.zahusek.tinyprotocolapi.packet.PlayerListHeaderFooter;
import com.google.common.collect.Lists;

public class TabManager {

	private static final String EMPTY = ChatColor.translateAlternateColorCodes('&',"&r");
	private final PlayerInfoData[][] tablist = new PlayerInfoData[4][20];
	private final PlayerListHeaderFooter[] tablisthnf = new PlayerListHeaderFooter[1];

	public void setTabSlot(int x, int y, String msg, int ping) {
		if ((x > 4) || (y > 20))
			return;
		tablist[x][y].setDisplayname(msg == null ? EMPTY : msg);
		tablist[x][y].setPing(ping);
	}
	public void setTabHnF(String header, String footer) {
		tablisthnf[0] = new PlayerListHeaderFooter(header, footer);
	}
	protected List<PacketCover> getDefaultTab() {
		List<PacketCover> packet = Lists.newArrayList();
		IntStream.range(0, 20).forEach((y) -> IntStream.range(0, 4).forEach((x) -> {
			if (tablist[x][y] == null) {
				PlayerInfoData ppopi =  new PlayerInfoData(9999, Gamemode.DQFUQ,  digit(x) + digit(y), EMPTY);
				packet.add(new PlayerInfo(PlayerInfoAction.ADD, ppopi));
				tablist[x][y] = ppopi;
				return;
			}
			tablist[x][y].setDisplayname(EMPTY);
			packet.add(new PlayerInfo(PlayerInfoAction.DISPLAYNAME, tablist[x][y]));
		}));
		tablisthnf[0] = new PlayerListHeaderFooter("", "");
		packet.add(tablisthnf[0]);
		return packet;
	}

	protected List<PacketCover> getRefreshTab() {
		List<PacketCover> packet = Lists.newArrayList();
		IntStream.range(0, 20).forEach((y) -> IntStream.range(0, 4).forEach((x) -> {
			packet.add(new PlayerInfo(PlayerInfoAction.DISPLAYNAME, tablist[x][y]));
			packet.add(new PlayerInfo(PlayerInfoAction.PING, tablist[x][y]));
		}));
		packet.add(tablisthnf[0]);
		return packet;
	}
	protected List<PacketCover> getReload() {
		List<PacketCover> packet = Lists.newArrayList();
		IntStream.range(0, 20).forEach((y) -> IntStream.range(0, 4).forEach((x) -> {
			if (tablist[x][y] == null)
				return;
			packet.add(new PlayerInfo(PlayerInfoAction.REMOVE, tablist[x][y]));
		}));
		packet.add(new PlayerListHeaderFooter("", ""));
		return packet;
	}
	private String digit(int i) {
		return (i > 9 ? "000000" + i : "0000000" + i);
	}
	public void tablist() {

	}
}
