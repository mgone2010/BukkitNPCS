package me.stopbox123.nms;

import me.stopbox123.commands.SpawnNPC;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class TickTask extends BukkitRunnable {

	
	@Override
	public void run() {
		for (PlayerNPC npc : SpawnNPC.npcs) {
			for (Entity en : npc.getBukkitEntity().getNearbyEntities(5, 5, 5)) {
				if (en instanceof Player && npc.LookClose == true) {
					npc.lookAt(en.getLocation().add(0, 1, 0), npc.getBukkitEntity().getLocation());
					break;
				}
			}
		}
	}



}
