package com.gmail.zahusek.tinyprotocolapi.enums;

public enum Packet {
	
	
	PacketLoginInStart("PacketLoginInStart"),
	PacketLoginOutDisconnect("PacketLoginOutDisconnect"),
	PacketLoginOutEncryptionBegin("PacketLoginOutEncryptionBegin"),
	PacketLoginOutSetCompression("PacketLoginOutSetCompression"),
	PacketLoginOutSuccess("PacketLoginOutSuccess"),
	
	PacketPlayInAbilities("PacketPlayInAbilities"),
	PacketPlayInArmAnimation("PacketPlayInArmAnimation"),
	PacketPlayInBlockDig("PacketPlayInBlockDig"),
	PacketPlayInBlockPlace("PacketPlayInBlockPlace"),
	PacketPlayInChat("PacketPlayInChat"),
	PacketPlayInClientCommand("PacketPlayInClientCommand"),
	PacketPlayInCloseWindow("PacketPlayInCloseWindow"),
	PacketPlayInCustomPayload("PacketPlayInCustomPayload"),
	PacketPlayInEnchantItem("PacketPlayInEnchantItem"),
	PacketPlayInEntityAction("PacketPlayInEntityAction"),
	PacketPlayInFlying("PacketPlayInFlying"),
	PacketPlayInHeldItemSlot("PacketPlayInHeldItemSlot"),
	PacketPlayInKeepAlive("PacketPlayInKeepAlive"),
	PacketPlayInLook("PacketPlayInLook"),
	PacketPlayInResourcePackStatus("PacketPlayInResourcePackStatus"),
	PacketPlayInSetCreativeSlot("PacketPlayInSetCreativeSlot"),
	PacketPlayInSettings("PacketPlayInSettings"),
	PacketPlayInSpectate("PacketPlayInSpectate"),
	PacketPlayInSteerVehicle("PacketPlayInSteerVehicle"),
	PacketPlayInTabComplete("PacketPlayInTabComplete"),
	PacketPlayInTransaction("PacketPlayInTransaction"),
	PacketPlayInUpdateSign("PacketPlayInUpdateSign"),
	PacketPlayInUseEntity("PacketPlayInUseEntity"),
	PacketPlayInWindowClick("PacketPlayInWindowClick"),
	
	PacketPlayOutAbilities("PacketPlayOutAbilities"),
	PacketPlayOutAnimation("PacketPlayOutAnimation"),
	PacketPlayOutAttachEntity("PacketPlayOutAttachEntity"),
	PacketPlayOutBed("PacketPlayOutBed"),
	PacketPlayOutBlockAction("PacketPlayOutBlockAction"),
	PacketPlayOutBlockBreakAnimation("PacketPlayOutBlockBreakAnimation"),
	PacketPlayOutBlockChange("PacketPlayOutBlockChange"),
	PacketPlayOutCamera("PacketPlayOutCamera"),
	PacketPlayOutChat("PacketPlayOutChat"),
	PacketPlayOutCloseWindow("PacketPlayOutCloseWindow"),
	PacketPlayOutCollect("PacketPlayOutCollect"),
	PacketPlayOutCombatEvent("PacketPlayOutCombatEvent"),
	PacketPlayOutCustomPayload("PacketPlayOutCustomPayload"),
	PacketPlayOutEntity("PacketPlayOutEntity"),
	PacketPlayOutEntityDestroy("PacketPlayOutEntityDestroy"),
	PacketPlayOutEntityEffect("PacketPlayOutEntityEffect"),
	PacketPlayOutEntityEquipment("PacketPlayOutEntityEquipment"),
	PacketPlayOutEntityHeadRotation("PacketPlayOutEntityHeadRotation"),
	PacketPlayOutEntityMetadata("PacketPlayOutEntityMetadata"),
	PacketPlayOutEntityStatus("PacketPlayOutEntityStatus"),
	PacketPlayOutEntityTeleport("PacketPlayOutEntityTeleport"),
	PacketPlayOutEntityVelocity("PacketPlayOutEntityVelocity"),
	PacketPlayOutExperience("PacketPlayOutExperience"),
	PacketPlayOutExplosion("PacketPlayOutExplosion"),
	PacketPlayOutGameStateChange("PacketPlayOutGameStateChange"),
	PacketPlayOutHeldItemSlot("PacketPlayOutHeldItemSlot"),
	PacketPlayOutKeepAlive("PacketPlayOutKeepAlive"),
	PacketPlayOutKickDisconnect("PacketPlayOutKickDisconnect"),
	PacketPlayOutLogin("PacketPlayOutLogin"),
	PacketPlayOutMap("PacketPlayOutMap"),
	PacketPlayOutMapChunk("PacketPlayOutMapChunk"),
	PacketPlayOutMapChunkBulk("PacketPlayOutMapChunkBulk"),
	PacketPlayOutMultiBlockChange("PacketPlayOutMultiBlockChange"),
	PacketPlayOutNamedEntitySpawn("PacketPlayOutNamedEntitySpawn"),
	PacketPlayOutNamedSoundEffect("PacketPlayOutNamedSoundEffect"),
	PacketPlayOutOpenSignEditor("PacketPlayOutOpenSignEditor"),
	PacketPlayOutOpenWindow("PacketPlayOutOpenWindow"),
	PacketPlayOutPlayerInfo("PacketPlayOutPlayerInfo"),
	PacketPlayOutPlayerListHeaderFooter("PacketPlayOutPlayerListHeaderFooter"),
	PacketPlayOutPosition("PacketPlayOutPosition"),
	PacketPlayOutRemoveEntityEffect("PacketPlayOutRemoveEntityEffect"),
	PacketPlayOutResourcePackSend("PacketPlayOutResourcePackSend"),
	PacketPlayOutRespawn("PacketPlayOutRespawn"),
	PacketPlayOutScoreboardDisplayObjective("PacketPlayOutScoreboardDisplayObjective"),
	PacketPlayOutScoreboardObjective("PacketPlayOutScoreboardObjective"),
	PacketPlayOutScoreboardScore("PacketPlayOutScoreboardScore"),
	PacketPlayOutScoreboardTeam("PacketPlayOutScoreboardTeam"),
	PacketPlayOutServerDifficulty("PacketPlayOutServerDifficulty"),
	PacketPlayOutSetCompression("PacketPlayOutSetCompression"),
	PacketPlayOutSetSlot("PacketPlayOutSetSlot"),
	PacketPlayOutSpawnEntity("PacketPlayOutSpawnEntity"),
	PacketPlayOutSpawnEntityExperienceOrb("PacketPlayOutSpawnEntityExperienceOrb"),
	PacketPlayOutSpawnEntityLiving("PacketPlayOutSpawnEntityLiving"),
	PacketPlayOutSpawnEntityPainting("PacketPlayOutSpawnEntityPainting"),
	PacketPlayOutSpawnEntityWeather("PacketPlayOutSpawnEntityWeather"),
	PacketPlayOutSpawnPosition("PacketPlayOutSpawnPosition"),
	PacketPlayOutStatistic("PacketPlayOutStatistic"),
	PacketPlayOutTabComplete("PacketPlayOutTabComplete"),
	PacketPlayOutTileEntityData("PacketPlayOutTileEntityData"),
	PacketPlayOutTitle("PacketPlayOutTitle"),
	PacketPlayOutTransaction("PacketPlayOutTransaction"),
	PacketPlayOutUpdateAttributes("PacketPlayOutUpdateAttributes"),
	PacketPlayOutUpdateEntityNBT("PacketPlayOutUpdateEntityNBT.class"),
	PacketPlayOutUpdateHealth("PacketPlayOutUpdateHealth"),
	PacketPlayOutUpdateSign("PacketPlayOutUpdateSign"),
	PacketPlayOutUpdateTime("PacketPlayOutUpdateTime"),
	PacketPlayOutWindowData("PacketPlayOutWindowData "),
	PacketPlayOutWindowItems("PacketPlayOutWindowItems"),
	PacketPlayOutWorldBorder("PacketPlayOutWorldBorder"),
	PacketPlayOutWorldEvent("PacketPlayOutWorldEvent"),
	PacketPlayOutWorldParticles("PacketPlayOutWorldParticles"),
	
	PacketStatusInListener("PacketStatusInListener"),
	PacketStatusInPing("PacketStatusInPing"),
	PacketStatusInStart("PacketStatusInStart"),
	
	PacketStatusOutListener("PacketStatusOutListener"),
	PacketStatusOutPong("PacketStatusOutPong"),
	PacketStatusOutServerInfo("PacketStatusOutServerInfo");
	
	private String name;

	private Packet(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean hasIn() {
		return (name.startsWith("PacketPlayIn") || name
				.startsWith("PacketStausIn"));
	}

	public boolean hasOut() {
		return (name.startsWith("PacketPlayOut") || name
				.startsWith("PacketStausOut"));
	}
}
