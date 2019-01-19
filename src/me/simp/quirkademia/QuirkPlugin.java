package me.simp.quirkademia;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.simp.quirkademia.command.Commands;
import me.simp.quirkademia.configuration.Configs;
import me.simp.quirkademia.manager.ManagersRunnable;
import me.simp.quirkademia.manager.PassiveManager;
import me.simp.quirkademia.manager.AbilityManager;
import me.simp.quirkademia.manager.QuirkManager;
import me.simp.quirkademia.manager.UserManager;
import me.simp.quirkademia.storage.StorageManager;

public class QuirkPlugin extends JavaPlugin{

	private static QuirkPlugin plugin;
	private Configs configs;
	private GeneralMethods methods;
	private ManagersRunnable runner;
	private AbilityManager abilManager;
	private UserManager userManager;
	private PassiveManager passManager;
	private Commands commands;
	private StorageManager storage;
	private QuirkManager quirks;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		configs = new Configs(this);
		methods = new GeneralMethods(this);
		runner = new ManagersRunnable(this);
		abilManager = new AbilityManager(this);
		userManager = new UserManager(this);
		passManager = new PassiveManager(this);
		commands = new Commands(this);
		storage = new StorageManager(this);
		quirks = new QuirkManager(this);
		
		new QuirkListener(this);
		
		for (Player player : getServer().getOnlinePlayers()) {
			userManager.login(player.getUniqueId());
		}
	}
	
	@Override
	public void onDisable() {
		for (Player player : getServer().getOnlinePlayers()) {
			userManager.logout(player.getUniqueId());
		}
		
		storage.get().close();
	}
	
	public static QuirkPlugin get() {
		return plugin;
	}
	
	public GeneralMethods getMethods() {
		return methods;
	}
	
	public ManagersRunnable getManagersRunnable() {
		return runner;
	}
	
	public AbilityManager getAbilityManager() {
		return abilManager;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	
	public PassiveManager getPassiveManager() {
		return passManager;
	}
	
	public Commands getCommands() {
		return commands;
	}
	
	public Configs getConfigs() {
		return configs;
	}
	
	public StorageManager getStorageManager() {
		return storage;
	}
	
	public QuirkManager getQuirkManager() {
		return quirks;
	}
}
