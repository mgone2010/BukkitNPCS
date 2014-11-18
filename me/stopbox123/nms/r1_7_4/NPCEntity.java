package me.stopbox123.nms.r1_7_4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import me.stopbox123.core.BukkitNPC;
import me.stopbox123.nms.r1_7_4.pathing.NPCPath;
import me.stopbox123.nms.r1_7_4.pathing.NPCPathFinder;
import me.stopbox123.nms.r1_7_4.pathing.Node;
import me.stopbox123.nms.r1_7_4.pathing.PathReturn;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EnumGamemode;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class NPCEntity extends EntityPlayer {
	private boolean gravity = true;
	public UUID uuid;
	
	private NPCPathFinder path;
	private Iterator<Node> pathIterator;
	private Node last;
	private NPCPath runningPath;
	private int taskid;
	private Runnable onFail;
	Location loc;

	
	private Location currentLoc;
	private ArrayList<Location> waypoints = new ArrayList<Location>();
	private ArrayList<Location> usedWayPoints = new ArrayList<Location>();
	
	public NPCEntity(World world, Location location, NPCProfile profile, NPCNetworkManager networkManager, ArrayList<Location> waypoints) {
		super(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) world).getHandle(), profile.getHandle(), new PlayerInteractManager(((CraftWorld) world).getHandle()));
		playerInteractManager.b(EnumGamemode.SURVIVAL);
		this.playerConnection = new NPCPlayerConnection(networkManager, this);
		this.fauxSleeping = true;
		this.bukkitEntity = new CraftPlayer((CraftServer) Bukkit.getServer(), this);
		WorldServer worldServer = ((CraftWorld) world).getHandle();
		setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		worldServer.addEntity(this);
		setYaw(location.getYaw());
		this.pitch = location.getPitch();
		worldServer.players.remove(this);
		this.loc = location;
	
		if (waypoints != null) {
			for (Location loc : waypoints) {
				this.waypoints.add(loc);
			}
		}
	}
	
	public NPCEntity(World world, Location location, GameProfile profile, NPCNetworkManager networkManager, ArrayList<Location> waypoints) {
		super(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) world).getHandle(), profile, new PlayerInteractManager(((CraftWorld) world).getHandle()));
		playerInteractManager.b(EnumGamemode.SURVIVAL);
		this.playerConnection = new NPCPlayerConnection(networkManager, this);
		this.fauxSleeping = true;
		this.bukkitEntity = new CraftPlayer((CraftServer) Bukkit.getServer(), this);
		WorldServer worldServer = ((CraftWorld) world).getHandle();
		setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		worldServer.addEntity(this);
		setYaw(location.getYaw());
		this.pitch = location.getPitch();
		worldServer.players.remove(this);
		this.loc = location;
		
		if (waypoints != null) {
			for (Location loc : waypoints) {
				this.waypoints.add(loc);
			}
		}
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
				((CraftPlayer) p).getHandle().playerConnection
						.sendPacket(packet);
			}
		}
	}

	@Override
	public void h() {
		super.h();
		this.C();
		
		if (currentLoc != null) {
			walkTo(currentLoc);
		}
		
		if (currentLoc == null && !waypoints.isEmpty()) {
			currentLoc = waypoints.get(0);
		}
		
//		walkTo(getBukkitEntity().getLocation().add(0, 0, -1));
		
		// this.motY = onGround ? Math.max(0.0, motY) : motY;
		// move(motX, motY, motZ);
		// this.motX *= 0.800000011920929;
		// this.motY *= 0.800000011920929;
		// this.motZ *= 0.800000011920929;
		// if (gravity && !this.onGround) {
		// this.motY -= 0.1;
		// }

	}

	public void lookAtPoint(Location point) {
		if (getBukkitEntity().getWorld() != point.getWorld()) {
			return;
		}
		final Location npcLoc = ((LivingEntity) this.getBukkitEntity())
				.getEyeLocation();
		final double xDiff = point.getX() - npcLoc.getX();
		final double yDiff = point.getY() - npcLoc.getY();
		final double zDiff = point.getZ() - npcLoc.getZ();
		final double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
		final double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff
				* yDiff);
		double newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
		final double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI
				- 90;
		if (zDiff < 0.0) {
			newYaw = newYaw + Math.abs(180 - newYaw) * 2;
		}
		setYaw((float) (newYaw - 90));
		this.pitch = (float) newPitch;
		// ((EntityPlayer) this).aP = (float) (newYaw - 90);
	}

	public void moveTo(Location l) {
		getBukkitEntity().teleport(l);
	}

	public void pathFindTo(Location l, PathReturn callback) {
		pathFindTo(l, 3000, callback);
	}

	public void pathFindTo(Location l, int maxIterations, PathReturn callback) {
		if (path != null) {
			path.cancel = true;
		}
		if (l.getWorld() != getBukkitEntity().getWorld()) {
			final ArrayList<Node> pathList = new ArrayList<>();
			pathList.add(new Node(l.getBlock()));
			callback.run(new NPCPath(null, pathList, l));
		} else {
			path = new NPCPathFinder(getBukkitEntity().getLocation(), l,
					maxIterations, callback);
			path.start();
		}
	}

	public void walkTo(Location l) {
		walkTo(l, 3000);
	}

	public void walkTo(final Location l, final int maxIterations) {
		pathFindTo(l, maxIterations, new PathReturn() {
			@Override
			public void run(NPCPath path) {
				usePath(path, new Runnable() {
					@Override
					public void run() {
						walkTo(l, maxIterations);
					}
				});
			}
		});
	}

	public void usePath(NPCPath path) {
		usePath(path, new Runnable() {
			@Override
			public void run() {
				walkTo(runningPath.getEnd(), 3000);
			}
		});
	}

	public void usePath(NPCPath path, Runnable onFail) {
		if (taskid == 0) {
			taskid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(BukkitNPC.getCore(),
					new Runnable() {
					@Override
					public void run() {
						pathStep();
						}
				}, 6L, 6L);
		}
		pathIterator = path.getPath().iterator();
		runningPath = path;
		this.onFail = onFail;
	}

	private void pathStep() {
		if (pathIterator.hasNext()) {
			final Node n = pathIterator.next();
			if (n.b.getWorld() != getBukkitEntity().getWorld()) {
				getBukkitEntity().teleport(n.b.getLocation());
			} else {
				float angle = this.yaw;
				float look = this.pitch;
				if (last == null || runningPath.checkPath(n, last, true)) {
					if (last != null) {
						angle = (float) Math.toDegrees(Math.atan2(last.b.getX()
								- n.b.getX(), n.b.getZ() - last.b.getZ()));
						look = (float) (Math.toDegrees(Math.asin(last.b.getY()
								- n.b.getY())) / 2);
					}
					this.setPositionRotation(n.b.getX() + 0.5,
							n.b.getY(), n.b.getZ() + 0.5, angle, look);
					
					setYaw(angle);
					//((EntityPlayer) this).aN = angle;
				} else {
					onFail.run();
				}
			}
			last = n;
		} else {
			this.setPositionRotation(runningPath.getEnd().getX(),
					runningPath.getEnd().getY(), runningPath.getEnd().getZ(),
					runningPath.getEnd().getYaw(),
					runningPath.getEnd().getPitch());
			setYaw(runningPath.getEnd().getYaw());
			//((EntityPlayer) this).aN = runningPath.getEnd().getYaw();
			Bukkit.getServer().getScheduler().cancelTask(taskid);
			taskid = 0;
		}
	}

	@Override
	public boolean damageEntity(DamageSource source, float damage) {
		return false;
	}

}
