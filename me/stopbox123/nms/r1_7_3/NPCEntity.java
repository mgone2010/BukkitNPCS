package me.stopbox123.nms.r1_7_3;

import net.minecraft.server.v1_7_R3.DamageSource;
import net.minecraft.server.v1_7_R3.EntityPlayer;
import net.minecraft.server.v1_7_R3.EnumGamemode;
import net.minecraft.server.v1_7_R3.Packet;
import net.minecraft.server.v1_7_R3.PlayerInteractManager;
import net.minecraft.server.v1_7_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R3.CraftServer;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class NPCEntity extends EntityPlayer {
	private boolean gravity = true;

	public NPCEntity(World world, Location location, NPCProfile profile, NPCNetworkManager networkManager) {
		super(((CraftServer) Bukkit.getServer()).getServer(),
				((CraftWorld) world).getHandle(), profile.getHandle(),
				new PlayerInteractManager(((CraftWorld) world).getHandle()));
		playerInteractManager.b(EnumGamemode.SURVIVAL);
		this.playerConnection = new NPCPlayerConnection(networkManager, this);
		this.fauxSleeping = true;
		this.bukkitEntity = new CraftPlayer((CraftServer) Bukkit.getServer(),
				this);
		WorldServer worldServer = ((CraftWorld) world).getHandle();
		setPositionRotation(location.getX(), location.getY(), location.getZ(),
				location.getYaw(), location.getPitch());
		worldServer.addEntity(this);
		worldServer.players.remove(this);
	}

	@Override
	public CraftPlayer getBukkitEntity() {
		return (CraftPlayer) bukkitEntity;
	}

	
	public boolean isGravity() {
		return gravity;
	}

	
	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}


	public void lookAt(Location location) {
		setYaw(getLocalAngle(new Vector(locX, 0, locZ), location.toVector()));
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
		this.aP = yaw;
		this.aO = yaw;
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
	
	private final int RADIUS = Bukkit.getViewDistance() * 16;

	@SuppressWarnings("unused")
	private final void broadcastLocalPacket(Packet packet) {
		for (Player p : getBukkitEntity().getWorld().getPlayers()) {
			if (getBukkitEntity().getLocation()
					.distanceSquared(p.getLocation()) <= RADIUS * RADIUS) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}


	@Override
	public void h() {
		super.h();
		this.C();
		
/*		this.motY = onGround ? Math.max(0.0, motY) : motY;
		move(motX, motY, motZ);
		this.motX *= 0.800000011920929;
		this.motY *= 0.800000011920929;
		this.motZ *= 0.800000011920929;
		if (gravity && !this.onGround) {
			this.motY -= 0.1;
		}*/
		
	}


	@Override
	public boolean damageEntity(DamageSource source, float damage) {
		return false;
	}
}
