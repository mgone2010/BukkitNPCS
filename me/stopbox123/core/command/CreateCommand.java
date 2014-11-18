package me.stopbox123.core.command;

import java.util.UUID;

import me.stopbox123.nms.util.ReflectionUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements CommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player)sender;
		ReflectionUtil.spawnNPC(p.getLocation(), args[0], UUID.randomUUID().toString());
		return false;
	}

}
