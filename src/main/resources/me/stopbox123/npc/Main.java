package me.stopbox123.npc;

import me.stopbox123.commands.CommandHandler;
import me.stopbox123.commands.SpawnNPC;
import me.stopbox123.io.Updater;
import me.stopbox123.io.Updater.UpdateResult;
import me.stopbox123.io.Updater.UpdateType;
import me.stopbox123.nms.Equip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	boolean NEEDS_UPDATE = false;
	Updater update;
	
	@Override
	public void onEnable() {
		CommandHandler handler = new CommandHandler();
		handler.register("create", new SpawnNPC());
		Bukkit.getPluginManager().registerEvents(new Equip(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
/*		update = new Updater(this, 82251, this.getFile(), UpdateType.NO_DOWNLOAD, true);
		if (update.getLatestName().contains(Updater.AUTO_UPDATE_TAG.toString()) && !(update.getResult() == UpdateResult.FAIL_DBO)) {
			System.out.println("Downloading a Crictial update! the previous version that you have could brake you're server! don't worry the file is approved by bukkit dev staff");
			update = new Updater(this, 82251, this.getFile(), UpdateType.DEFAULT, true);
		}
		if (update.getResult() == UpdateResult.UPDATE_AVAILABLE && !(update.getResult() == UpdateResult.FAIL_DBO)) {
			NEEDS_UPDATE = true;
		}*/
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().isOp() && NEEDS_UPDATE) {
			e.getPlayer().sendMessage(ChatColor.BLACK + "[" + ChatColor.RED + "BukkitNPC" + ChatColor.BLACK + "] " + ChatColor.GREEN + "Update " + update.getLatestName() + " is ready for download!");
		}
	}
}
