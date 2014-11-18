package me.stopbox123.nms.r1_7_4;

import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.metadata.PlayerMetadataStore;

public class NPCMetadataStore extends PlayerMetadataStore {
	@Override
	protected String disambiguate(OfflinePlayer player, String metadataKey) {
		return player.getUniqueId().toString() + ":" + metadataKey;
	}
}