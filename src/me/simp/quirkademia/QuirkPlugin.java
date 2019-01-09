package me.simp.quirkademia;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.simp.quirkademia.command.Commands;
import me.simp.quirkademia.manager.CooldownManager;
import me.simp.quirkademia.manager.ManagersRunnable;
import me.simp.quirkademia.manager.PassiveManager;
import me.simp.quirkademia.manager.QuirkAbilityManager;
import me.simp.quirkademia.manager.StatusEffectManager;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;

public class QuirkPlugin extends JavaPlugin{

	private static QuirkPlugin plugin;
	private GeneralMethods methods;
	private ManagersRunnable runner;
	private QuirkAbilityManager abilManager;
	private StatusEffectManager statManager;
	private CooldownManager coolManager;
	private PassiveManager passManager;
	private Commands commands;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		methods = new GeneralMethods(this);
		runner = new ManagersRunnable(this);
		abilManager = new QuirkAbilityManager(this);
		statManager = new StatusEffectManager(this);
		coolManager = new CooldownManager(this);
		passManager = new PassiveManager(this);
		commands = new Commands(this);
		
		Quirk.loadCoreQuirks();
		
		getServer().getPluginManager().registerEvents(new QuirkListener(), this);
		
		for (Player player : getServer().getOnlinePlayers()) {
			QuirkUser.login(player.getUniqueId());
		}
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
	
	public StatusEffectManager getStatusManager() {
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
}
