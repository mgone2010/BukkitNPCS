package me.stopbox123.core.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

	private HashMap<String, CommandInterface> cmds = new HashMap<String, CommandInterface>();
	
	public void register(String name, CommandInterface cmd) {
		cmds.put(name, cmd);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player)sender;
			if (cmds.containsKey(args[0]) && args.length >= 1 && p.isOp()) {
				CommandInterface command = cmds.get(args[0]);
				
				ArrayList<String> newArgs = new ArrayList<String>();
				Collections.addAll(newArgs, args);
				newArgs.remove(0);
				args = newArgs.toArray(new String[newArgs.size()]);
				
				command.onCommand(sender, cmd, label, args);
			} else {
				sender.sendMessage("That command dosent exist!");
			}
		}
		
		return false;
	}


}
