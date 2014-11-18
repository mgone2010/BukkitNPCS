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
		
		Entity ent = getTarget(p, ReflectionUtil.v1_7_4_NPC);
		
		if (ent != null) {
			p.sendMessage("Selected npc " + ent.displayName);
		}
		
		return false;
	}
	
	 private static <T extends Entity> Entity getTarget(Entity entity, ArrayDeque<Entity> list) {
		 T target = null;
		 double threshold = 1;
		 
		 for (T other : list) {
			 Vector n = other.getLocation().toVector().subtract(entity.getLocation().toVector());
			 if (entity.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < threshold && n.normalize().dot(entity.getLocation().getDirection().normalize()) >= 0) {
		 if (target == null || target.getLocation().distanceSquared(entity.getLocation()) > other.getLocation().distanceSquared(entity.getLocation()))
			 target = other;
		 }
			 }
		 return target;
		 }

}
