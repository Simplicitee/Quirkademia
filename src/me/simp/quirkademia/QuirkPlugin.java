package me.simp.quirkademia;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.simp.quirkademia.command.Commands;
import me.simp.quirkademia.configuration.Configs;
import me.simp.quirkademia.manager.CooldownManager;
import me.simp.quirkademia.manager.ManagersRunnable;
import me.simp.quirkademia.manager.PassiveManager;
import me.simp.quirkademia.manager.QuirkAbilityManager;
import me.simp.quirkademia.manager.StatusManager;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.storage.StorageManager;

public class QuirkPlugin extends JavaPlugin{

	private static QuirkPlugin plugin;
	private Configs configs;
	private GeneralMethods methods;
	private ManagersRunnable runner;
	private QuirkAbilityManager abilManager;
	private StatusManager statManager;
	private CooldownManager coolManager;
	private PassiveManager passManager;
	private Commands commands;
	private StorageManager storage;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		configs = new Configs(this);
		methods = new GeneralMethods(this);
		runner = new ManagersRunnable(this);
		abilManager = new QuirkAbilityManager(this);
		statManager = new StatusManager(this);
		coolManager = new CooldownManager(this);
		passManager = new PassiveManager(this);
		commands = new Commands(this);
		storage = new StorageManager(this);
		
		Quirk.loadCoreQuirks();
		
		new QuirkListener(this);
		
		for (Player player : getServer().getOnlinePlayers()) {
			QuirkUser.login(player.getUniqueId());
		}
	}
	
	@Override
	public void onDisable() {
		for (Player player : getServer().getOnlinePlayers()) {
			QuirkUser.logout(player.getUniqueId());
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
	
	public QuirkAbilityManager getAbilityManager() {
		return abilManager;
	}
	
	public StatusManager getStatusManager() {
		return statManager;
	}
	
	public CooldownManager getCooldownManager() {
		return coolManager;
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
}
