package me.simp.quirkademia;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.simp.quirkademia.command.QuirkCommand;
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
	private PassiveManager passiveManager;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		methods = new GeneralMethods(this);
		runner = new ManagersRunnable(this);
		abilManager = new QuirkAbilityManager(this);
		statManager = new StatusEffectManager(this);
		passiveManager = new PassiveManager(this);
		
		Quirk.loadCoreQuirks();
		
		getServer().getPluginCommand("quirk").setExecutor(new QuirkCommand(this));
		
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
	
	public PassiveManager getPassiveManager() {
		return passiveManager;
	}
}
