package me.simp.quirkademia;

import org.bukkit.plugin.java.JavaPlugin;

public class QuirkPlugin extends JavaPlugin{

	private static QuirkPlugin plugin;
	private static GeneralMethods methods;
	
	@Override
	public void onEnable() {
		plugin = this;
		methods = new GeneralMethods(this);
		
		getServer().getPluginManager().registerEvents(new QuirkListener(), this);
	}
	
	public static QuirkPlugin get() {
		return plugin;
	}
	
	public static GeneralMethods getMethods() {
		return methods;
	}
}
