package me.stopbox123.core.command;

import java.util.ArrayDeque;

import me.stopbox123.nms.r1_7_4.NPCEntity;
import me.stopbox123.nms.util.ReflectionUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SelectCommand implements CommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player)sender;
		
		NPCEntity ent = getTarget(p, ReflectionUtil.v1_7_4_NPC);
		
		if (ent != null) {
			p.sendMessage("Selected npc " + ent.displayName);
		}
		
		return false;
	}
	
	 private static <T extends Entity> NPCEntity getTarget(Entity entity, ArrayDeque<NPCEntity> npcs) {
		 NPCEntity target = null;
		 double threshold = 1;
		 
		 for (NPCEntity other : npcs) {
			 Vector n = other.getBukkitEntity().getLocation().toVector().subtract(entity.getLocation().toVector());
			 if (entity.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < threshold && n.normalize().dot(entity.getLocation().getDirection().normalize()) >= 0) {
		 if (target == null || target.getBukkitEntity().getLocation().distanceSquared(entity.getLocation()) > other.getBukkitEntity().getLocation().distanceSquared(entity.getLocation()))
			 target = other;
		 }
			 }
		 return target;
		 }

}
