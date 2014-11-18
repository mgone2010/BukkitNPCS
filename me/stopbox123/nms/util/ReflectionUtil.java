package me.stopbox123.nms.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.UUID;

import me.stopbox123.core.BukkitNPC;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.util.com.google.gson.JsonParser;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.metadata.PlayerMetadataStore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ReflectionUtil {
	
//	private static String NMS_VERSION = "";
	
	 private boolean spigot;
	 @SuppressWarnings("unused")
	private Method removePlayerInfo;
	
//	private static ArrayDeque<me.stopbox123.nms.r1_7_3.NPCEntity> v1_7_3_NPC = new ArrayDeque<me.stopbox123.nms.r1_7_3.NPCEntity>();
	public static ArrayDeque<me.stopbox123.nms.r1_7_4.NPCEntity> v1_7_4_NPC = new ArrayDeque<me.stopbox123.nms.r1_7_4.NPCEntity>();
	
	public static void init() {
//		NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().substring(23);
		CraftServer server = (CraftServer) Bukkit.getServer();
		try {
			Field field = server.getClass().getDeclaredField("playerMetadata");
			field.setAccessible(true);
			PlayerMetadataStore metadata = (PlayerMetadataStore) field.get(server);
			if(!(metadata instanceof me.stopbox123.nms.r1_7_4.NPCMetadataStore)) {
				field.set(server, new me.stopbox123.nms.r1_7_4.NPCMetadataStore());
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	 private void checkSpigot() {
		 this.spigot = Bukkit.getVersion().contains("Spigot");
		 if (this.spigot) {
			 try {
				 this.removePlayerInfo = PacketPlayOutPlayerInfo.class.getDeclaredMethod("removePlayer", EntityPlayer.class);
			 } catch (NoSuchMethodException e) {
				 BukkitNPC.getCore().getLogger().severe("Failed to get removePlayer(EntityPlayer) from PacketPlayOutPlayerInfo:");
				 e.printStackTrace();
			 }
		 	}
		 }
	
	public static void deleteNPC(me.stopbox123.nms.r1_7_4.NPCEntity nmsEntity) {
		File file = new File(BukkitNPC.getCore().npcFolder.getAbsolutePath() + "/" + nmsEntity.uuid.toString() + ".yml");
		file.delete();
	}
	
	public static void destroyAll() {
		for (me.stopbox123.nms.r1_7_4.NPCEntity nmsEntity : v1_7_4_NPC) {
			nmsEntity.getBukkitEntity().remove();
		}
	}
	
	public static void spawnNPC(Location loc, String name, String uuid) {
		if (!new File(BukkitNPC.getCore().npcFolder.getAbsolutePath() + "/" + uuid + ".yml").exists()) {
			me.stopbox123.nms.r1_7_4.NPCEntity nmsEntity = new me.stopbox123.nms.r1_7_4.NPCEntity(
					loc.getWorld(),
					loc, me.stopbox123.nms.r1_7_4.NPCProfile.loadProfile(name, name),
					new me.stopbox123.nms.r1_7_4.NPCNetworkManager(), null);
			nmsEntity.uuid = UUID.fromString(uuid);
			v1_7_4_NPC.add(nmsEntity);	
			save(name, nmsEntity);
		} else {
			YamlConfiguration config = load(uuid);
			Property texture = new Property(config.getString("data.auth.name"), config.getString("data.auth.value"), config.getString("data.auth.signature"));
			GameProfile profile = new GameProfile(UUID.fromString(uuid), name);
			profile.getProperties().put("textures", texture);
			
			me.stopbox123.nms.r1_7_4.NPCEntity nmsEntity = new me.stopbox123.nms.r1_7_4.NPCEntity(
					loc.getWorld(),
					loc, profile,
					new me.stopbox123.nms.r1_7_4.NPCNetworkManager(), null);
			nmsEntity.uuid = UUID.fromString(uuid);
			v1_7_4_NPC.add(nmsEntity);	
		}
	}
	
	@SuppressWarnings("unused")
	private static void save(String name, me.stopbox123.nms.r1_7_3.NPCEntity nmsEntity) {
		File file = new File(BukkitNPC.getCore().npcFolder.getAbsolutePath() + "/" + UUID.randomUUID().toString() + ".yml");
		try {
			file.createNewFile();
		} catch (IOException e) { }
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("data.name", name);
		config.set("data.loc.world", nmsEntity.getBukkitEntity().getWorld().getName());
		config.set("data.loc.x", nmsEntity.getBukkitEntity().getLocation().getX());
		config.set("data.loc.y", nmsEntity.getBukkitEntity().getLocation().getY());
		config.set("data.loc.z", nmsEntity.getBukkitEntity().getLocation().getZ());
	}
	
	private static YamlConfiguration load(String uuid) {
		File file = new File(BukkitNPC.getCore().npcFolder.getAbsolutePath() + "/" + uuid + ".yml");
		return YamlConfiguration.loadConfiguration(file);
	}
	
	private static void save(String name, me.stopbox123.nms.r1_7_4.NPCEntity nmsEntity) {
		String uuid = nmsEntity.uuid.toString();
		File file = new File(BukkitNPC.getCore().npcFolder.getAbsolutePath() + "/" + uuid + ".yml");
		try {
			file.createNewFile();
		} catch (IOException e) { }
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("data.name", name);
		config.set("data.uuid", uuid);
		config.set("data.loc.world", nmsEntity.getBukkitEntity().getWorld().getName());
		config.set("data.loc.x", nmsEntity.getBukkitEntity().getLocation().getX());
		config.set("data.loc.y", nmsEntity.getBukkitEntity().getLocation().getY());
		config.set("data.loc.z", nmsEntity.getBukkitEntity().getLocation().getZ());
		config.set("data.loc.yaw", nmsEntity.getBukkitEntity().getLocation().getYaw());
		config.set("data.loc.pitch", nmsEntity.getBukkitEntity().getLocation().getPitch());
		
		//Save Skin Stuff !!!! Can Result in Large file size for a .yml !!!!
		
		PropertyMap authmap = nmsEntity.getProfile().getProperties();
		
		for (Property prop : authmap.get("textures")) {
			config.set("data.auth.name", prop.getName());
			config.set("data.auth.value", prop.getValue());
			config.set("data.auth.signature", prop.getSignature());
		}
		
		try {
			config.save(file);
		} catch (IOException e) { }
	}
	
	public static String[] serializeLocation(Location loc) {
		String[] args = new String[6];
		args[0] = loc.getWorld().getName();
		args[1] = String.valueOf(loc.getX());
		args[2] = String.valueOf(loc.getY());
		args[3] = String.valueOf(loc.getZ());
		args[4] = String.valueOf(loc.getYaw());
		args[5] = String.valueOf(loc.getPitch());
		return args;
	}

	public static Location unserializeLocation(ConfigurationSection sec) {
		World world = Bukkit.getWorld(sec.getString("world"));
		double x = sec.getDouble("x");
		double y = sec.getDouble("y");
		double z = sec.getDouble("z");
		float pitch = (float) sec.getDouble("pitch");
		float yaw = (float) sec.getDouble("yaw");
		
		return new Location(world, x, y, z, pitch, yaw);
	}
}
