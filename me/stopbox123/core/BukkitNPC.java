package me.stopbox123.core;

import java.io.File;
import java.io.IOException;

import me.stopbox123.core.command.CommandHandler;
import me.stopbox123.core.command.CreateCommand;
import me.stopbox123.core.command.SelectCommand;
import me.stopbox123.core.mcstats.Metrics;
import me.stopbox123.nms.util.ReflectionUtil;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitNPC extends JavaPlugin {
	
	public File npcFolder;
	
	private static BukkitNPC core;
	
	@Override
	public void onEnable() {
		core = this;
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		saveConfig();
		
		ReflectionUtil.init();
		
		CommandHandler handler = new CommandHandler();
		handler.register("create", new CreateCommand());
		handler.register("select", new SelectCommand());
		
		getCommand("npc").setExecutor(handler);
		
		try {
			npcFolder = new File(getDataFolder().getCanonicalPath() + "/npcs");
		} catch (IOException e) {
			System.out.println("[BukkitNPCS] Failed to load npcs!");
		}
		
		if (!npcFolder.exists()) {
			npcFolder.mkdir();
		} else {
			for (File f : npcFolder.listFiles()) {
				if (f.getName().endsWith(".yml")) {
					File fconfig = f;
					YamlConfiguration npcFile = YamlConfiguration.loadConfiguration(fconfig);
					
					String name = npcFile.getString("data.name");
					String uuid = npcFile.getString("data.uuid");
					Location loc = ReflectionUtil.unserializeLocation(npcFile.getConfigurationSection("data.loc"));
					ReflectionUtil.spawnNPC(loc, name, uuid);
				}
			}
		}
		
		if (getConfig().getBoolean("opt-out") == false) {
			Metrics metrics = null;
			try {
				metrics = new Metrics(this);
			} catch (IOException e1) {
				//Failed to init metrics
			}
		
			try {
				metrics.start();
			} catch (Exception e) {
				// Failed to submit metrics ;-(
			}
		}
	}

	@Override
	public void onDisable() {
		ReflectionUtil.destroyAll();
	}
	
	public static BukkitNPC getCore() {
		return core;
	}
}
