package me.simp.quirkademia.storage;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.configuration.ConfigType;
import me.simp.quirkademia.storage.DatabaseStorage.DatabaseType;

public class StorageManager {

	private QuirkPlugin plugin;
	private Storage storage;
	
	public StorageManager(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.storage = load();
	}
	
	private Storage load() {
		Storage storage = null;
		String type = plugin.getConfigs().getConfiguration(ConfigType.PROPERTIES).getString("Storage.Type");
		
		if (type.equalsIgnoreCase("sqlite")) {
			storage = new DatabaseStorage(plugin, DatabaseType.SQLITE);
		} else if (type.equalsIgnoreCase("mysql")) {
			storage = new DatabaseStorage(plugin, DatabaseType.MYSQL);
		} else {
			storage = new ConfigStorage(plugin);
		}
		
		return storage;
	}
	
	public Storage get() {
		return storage;
	}
}
