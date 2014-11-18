package me.stopbox123.nms.r1_7_4;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Iterables;

import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;

import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NPCProfile {
	private static final Cache<String, Property> TEXTURE_CACHE = CacheBuilder
			.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES)
			.build(new CacheLoader<String, Property>() {
				@Override
				public Property load(String key) throws Exception {
					return loadTextures(key);
				}
			});
	
	private static Object lock = new Object();

	@SuppressWarnings("deprecation")
	private static final Property loadTextures(String name) {
		GameProfile profile = new GameProfile(Bukkit.getOfflinePlayer(name).getUniqueId(), name);
		MinecraftServer.getServer().av().fillProfileProperties(profile, true);
		return Iterables.getFirst(profile.getProperties().get("textures"), null);
	}
	
	public static NPCProfile loadProfile(String name, String skinOwner) {
		synchronized (lock) {
			try {
				final GameProfile profile = new GameProfile(UUID.randomUUID(), name);
				profile.getProperties().put("textures", TEXTURE_CACHE.get(skinOwner));
				return new NPCProfile(profile);
				} catch (Exception e) {
					return new NPCProfile(name);
				}
			}
	}

	private final GameProfile handle;
	
	public NPCProfile(String name) {
		this(new GameProfile(UUID.randomUUID(), name));
	}

	private NPCProfile(GameProfile handle) {
		this.handle = handle;
	}

	/**
	 * Get the profile UUID
	 * 
	 * @return Profile UUID
	 */
	public UUID getUUID() {
		return handle.getId();
	}

	/**
	 * Get the profile display name
	 * 
	 * @return Display name
	 */
	public String getDisplayName() {
		return handle.getName();
	}

	/**
	 * Get the original game profile
	 * 
	 * @return Original game profile
	 */
	public GameProfile getHandle() {
		return handle;
	}
}