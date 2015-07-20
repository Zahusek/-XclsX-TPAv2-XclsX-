package com.gmail.zahusek.tinyprotocolapi.packet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.zahusek.tinyprotocolapi.abstracts.PacketCover;
import com.gmail.zahusek.tinyprotocolapi.annotations.PacketHandler;
import com.gmail.zahusek.tinyprotocolapi.enums.Packet;
import com.gmail.zahusek.tinyprotocolapi.enums.PlayerInfoAction;
import com.gmail.zahusek.tinyprotocolapi.nms.PlayerInfoData;

@PacketHandler(packet = Packet.PacketPlayOutPlayerInfo)
public class PlayerInfo extends PacketCover {

	public PlayerInfo(PlayerInfoAction epia, PlayerInfoData... pid) {
		super();
		Object action = epia.getValue();
		List<Object> data = Arrays.asList(pid).stream().map(o -> o.returned())
				.collect(Collectors.toList());
		$(0, action);
		$(1, data);
	}
	public PlayerInfo(PlayerInfoAction epia, Iterable<Object> players) {
		super(0, epia.getValue(), players);
	}
}
