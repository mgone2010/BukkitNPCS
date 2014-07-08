package me.stopbox123.nms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.stopbox123.nms.network.NPCNetworkManager;
import me.stopbox123.nms.network.NPCPlayerConnection;
import net.minecraft.server.v1_7_R3.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;

@SerializableAs(value = "NPC")
public class PlayerNPC extends EntityPlayer {
	
	public boolean LookClose = false;
	public int looktask;
	
	public PlayerNPC(MinecraftServer MCServer, WorldServer WServer, GameProfile GProfile, PlayerInteractManager PIManager) throws Exception {
		super(MCServer, WServer, GProfile, PIManager);
		NetworkManager NullManager = new NPCNetworkManager();
		playerConnection = (PlayerConnection) new NPCPlayerConnection(MCServer, NullManager, this);
		NullManager.a(playerConnection);
	}
	

	private final float getLocalAngle(Vector point1, Vector point2) {
		double dx = point2.getX() - point1.getX();
		double dz = point2.getZ() - point1.getZ();
		float angle = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
		if (angle < 0) {
			angle += 360.0F;
		}
		return angle;
	}

	public void lookAt(Location location, Location loc2) {
		setYaw(getLocalAngle(new Vector(locX, 0, locZ), location.toVector()));
		final double xDiff = loc2.getX() - getBukkitEntity().getLocation().getX();
        final double yDiff = loc2.getY() - getBukkitEntity().getLocation().getY();
        final double zDiff = loc2.getZ() - getBukkitEntity().getLocation().getZ();
        final double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        final double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
        final double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
        this.pitch = (float) newPitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
		this.aP = yaw;
		this.aO = yaw;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void sendUpatePackets() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(getBukkitEntity());
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.showPlayer(getBukkitEntity());
		}
	}
	  
	  @Override
	  public boolean damageEntity(DamageSource damagesource, float f) {
		  return false;
	  }
}
