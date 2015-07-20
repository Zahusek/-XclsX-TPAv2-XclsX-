package com.gmail.zahusek.tinyprotocolapi.packet;

import com.gmail.zahusek.tinyprotocolapi.abstracts.PacketCover;
import com.gmail.zahusek.tinyprotocolapi.annotations.PacketHandler;
import com.gmail.zahusek.tinyprotocolapi.enums.Packet;
import com.gmail.zahusek.tinyprotocolapi.nms.ChatSerializer;

@PacketHandler(packet = Packet.PacketPlayOutPlayerListHeaderFooter)
public class PlayerListHeaderFooter extends PacketCover {
	
	public PlayerListHeaderFooter(String header, String footer) {
		super();
		$(0, ChatSerializer.toRaw(header));
		$(1, ChatSerializer.toRaw(footer));
	}

}
