package me.simp.quirkademia.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.simp.quirkademia.QuirkPlugin;

public class Config {

	private QuirkPlugin plugin;
	private File file;
	private FileConfiguration config;
	
	public Config(String name) {
		this.plugin = QuirkPlugin.get();
		this.file = new File(plugin.getDataFolder(), name);
		this.config = YamlConfiguration.loadConfiguration(file);
		this.reload();
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	public File getFile() {
		return file;
	}
	
	public FileConfiguration get() {
		return config;
	}
	
	public void create() {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void load() {
		try {
			config.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void reload() {
		this.create();
		this.load();
	}
	
	public void save() {
		config.options().copyDefaults(true);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
