package me.stopbox123.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor
{

	private static HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();
	
	public void register(String name, CommandInterface cmd) {
		commands.put(name, cmd);
	}
	
	public boolean exists(String name) {
		return commands.containsKey(name);
	}

	public CommandInterface getExecutor(String name) {
		return commands.get(name);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if(sender instanceof Player) {

			if(args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Need args! eg: create <name> | killall | equip");
				return true;
			}

			if(args.length > 0) {

				if(exists(args[0])){
					
					getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
					return true;
				} else {
					
					sender.sendMessage("This command doesn't exist!");
					return true;
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
			return true;
		}
		return false;
	}

}
