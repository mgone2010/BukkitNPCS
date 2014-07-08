package me.stopbox123.npc;

import me.stopbox123.commands.SpawnNPC;
import me.stopbox123.nms.Equip;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("npc").setExecutor(new SpawnNPC());
		Bukkit.getPluginManager().registerEvents(new Equip(), this);
		// Add Later
		//Update update = new Updater(this, 82251, this.getFile(), UpdateType.NO_DOWNLOAD, true);
	}
}
