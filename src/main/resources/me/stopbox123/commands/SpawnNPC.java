package me.stopbox123.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import me.stopbox123.nms.PlayerNPC;
import me.stopbox123.nms.ProfileLoader;
import me.stopbox123.nms.TickTask;
import net.minecraft.server.v1_7_R3.MinecraftServer;
import net.minecraft.server.v1_7_R3.PlayerInteractManager;
import net.minecraft.server.v1_7_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.entity.Player;

public class SpawnNPC implements CommandExecutor {

	public static ArrayList<PlayerNPC> npcs = new ArrayList<PlayerNPC>();
	protected HashMap<Player, PlayerNPC> selected = new HashMap<>();
	public static HashMap<Player, PlayerNPC> equiping = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to use this command!");
			return true;
		} else {
			Player p = (Player)sender;
			createEntity(p, args, label);
			removeAll(p, args, label);
			look(p, args);
			EquipItems(p, args);
		}
		return true;
	}
	/*@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to use this command!");
			return true;
		} else {
			Player p = (Player)sender;
			if (args.length == 2 && p.hasPermission("bukkitnpcs.*") && args[0].equalsIgnoreCase("create") || args.length == 2 && p.hasPermission("bukkitnpcs.create") && args[0].equalsIgnoreCase("create")) {
				try {
					WorldServer nmsWorld = ((CraftWorld)p.getWorld()).getHandle();
					PlayerNPC nmsEntity = new PlayerNPC(MinecraftServer.getServer(), nmsWorld, new ProfileLoader(UUID.randomUUID().toString(), args[1], args[1]).loadProfile(), new PlayerInteractManager(nmsWorld));
					nmsEntity.setPositionRotation(p.getLocation().getX(), 
							p.getLocation().getY(),
							p.getLocation().getZ(),
							p.getLocation().getYaw(), 
							p.getLocation().getPitch());
					nmsWorld.addEntity(nmsEntity);
					nmsEntity.setYaw(p.getLocation().getYaw());
					nmsEntity.setPitch(p.getLocation().getPitch());
					npcs.add(nmsEntity);
					return true;
				} catch (Exception e) {
					p.sendMessage(ChatColor.DARK_RED + "Failed creating NPC there was a error!");
					return true;
				}
			} else if (args.length == 1 && p.hasPermission("bukkitnpcs.*") && args[0].equalsIgnoreCase("killall") || args.length == 1 && p.hasPermission("bukkitnpcs.killall") && args[0].equalsIgnoreCase("killall")) {
					for (PlayerNPC npc : npcs) {
						npc.getBukkitEntity().remove();
				}
					npcs.clear();
			}
		}
		return true;
	}*/
	
	public void createEntity(Player p, String[] args, String label) {
		if (args[0].equalsIgnoreCase("create") && p.hasPermission("bukkitnpcs.*") || args[0].equalsIgnoreCase("create") && p.hasPermission("bukkitnpcs.create")) {
		try {
			WorldServer nmsWorld = ((CraftWorld)p.getWorld()).getHandle();
			PlayerNPC nmsEntity = new PlayerNPC(MinecraftServer.getServer(), nmsWorld, new ProfileLoader(UUID.randomUUID().toString(), args[1], args[1]).loadProfile(), new PlayerInteractManager(nmsWorld));
			nmsEntity.setPositionRotation(p.getLocation().getX(), 
					p.getLocation().getY(),
					p.getLocation().getZ(),
					p.getLocation().getYaw(), 
					p.getLocation().getPitch());
			nmsWorld.addEntity(nmsEntity);
			nmsEntity.setYaw(p.getLocation().getYaw());
			nmsEntity.setPitch(p.getLocation().getPitch());
			npcs.add(nmsEntity);
			selected.put(p, nmsEntity);
			p.sendMessage(ChatColor.GREEN + "You have created a npc named " + nmsEntity.displayName + ".");
		} catch (Exception e) {
			p.sendMessage(ChatColor.DARK_RED + "Failed creating NPC there was a error!");
		}
	}
	}		
		public void removeAll(Player p, String[] args, String label) {
			 if (args[0].equalsIgnoreCase("killall") && p.hasPermission("bukkitnpcs.*") || args[0].equalsIgnoreCase("killall") && p.hasPermission("bukkitnpcs.killall")) {
					for (PlayerNPC npc : npcs) {
						npc.getBukkitEntity().remove();
				}
					npcs.clear();
			}
		}
			 public void look(Player p, String[] args) {
					if (args[0].equalsIgnoreCase("look") && selected.get(p).LookClose == false) {
						selected.get(p).LookClose = true;
						selected.get(p).looktask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("BukkitNPC"), new TickTask(), 1L, 1L);
						p.sendMessage(ChatColor.GREEN + "The npc " + selected.get(p).displayName + " will now look when some one is close.");
					} else if (args[0].equalsIgnoreCase("look") && selected.get(p).LookClose == true) {
						selected.get(p).LookClose = false;
						p.sendMessage(ChatColor.GREEN + "The npc " + selected.get(p).displayName + " will no longer look when some one is close.");
					}
			}
			 
		public void EquipItems(Player p, String[] args) {
			if (args[0].equalsIgnoreCase("equip") && selected.get(p) != null && !equiping.containsKey(p)) {
				equiping.put(p, selected.get(p));
				p.sendMessage(ChatColor.GREEN + "You are now equiping the npc " + selected.get(p).displayName);;
			} else if (args[0].equalsIgnoreCase("equip") && selected.get(p) != null && equiping.containsKey(p)) {
				p.sendMessage(ChatColor.GREEN + "You are no longer equiping the npc " + selected.get(p).displayName);
				equiping.remove(p);
			}
		}
}
