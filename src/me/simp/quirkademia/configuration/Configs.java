package me.simp.quirkademia.configuration;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import me.simp.quirkademia.QuirkPlugin;

public class Configs {

	private QuirkPlugin plugin;
	private Map<ConfigType, Config> configs;
	
	public Configs(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.configs = new HashMap<>();
		this.load();
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	private void load() {
		Config c = new Config(ConfigType.QUIRKS.getPath());
		FileConfiguration config = c.get();
		//quirk properties config
		
		config.addDefault("Quirks.OneForAll.Stamina.Title", "Stamina");
		config.addDefault("Quirks.OneForAll.Stamina.Color", "GREEN");
		config.addDefault("Quirks.OneForAll.Stamina.Max", 1000);
		config.addDefault("Quirks.OneForAll.Stamina.Recharge", 75);
	}
	
	public Config get(ConfigType type) {
		if (type != null) {
			return configs.get(type);
		}
		
		return null;
	}
}
