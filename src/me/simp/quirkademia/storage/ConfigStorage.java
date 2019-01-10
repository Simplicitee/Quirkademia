package me.simp.quirkademia.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.configuration.Config;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.quirk.QuirkUser;

public class ConfigStorage implements Storage {

	private QuirkPlugin plugin;
	private Config players;
	
	public ConfigStorage(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.players = new Config("players_storage.yml");
	}

	@Override
	public void store(QuirkUser user) {
		if (user.getQuirk() == null) {
			return;
		}
		
		String uuid = user.getUniqueId().toString();
		String quirk = user.getQuirk().getName().replace(" ", "-");
		FileConfiguration config = players.get();
		
		config.set(uuid + ".Quirk", quirk);
		
		if (plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("Storage.SaveCooldowns")) {
			for (String name : user.getCooldowns().keySet()) {
				config.set(uuid + ".Cooldowns." + name.replace(" ", "-"), user.getCooldowns().get(name).getRemaining());
			}
		}
		
		players.save();
	}

	@Override
	public Map<String, String> load(UUID uuid) {
		Map<String, String> storage = new HashMap<>();
		FileConfiguration config = players.get();
		
		if (config.contains(uuid.toString())) {
			storage.put("quirk", config.getString(uuid + ".Quirk").replace("-", " "));
			
			if (plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getBoolean("Storage.SaveCooldowns")) {
				ConfigurationSection cooldowns = config.getConfigurationSection(uuid + ".Cooldowns");
				
				if (cooldowns != null) {
					for (String key : cooldowns.getKeys(false)) {
						storage.put("cooldown." + key, "" + cooldowns.getLong(key));
					}
				}
			}
		}
		
		return storage;
	}

	@Override
	public void close() {
		players.save();
	}
}
