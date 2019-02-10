package me.simp.quirkademia.manager;

import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.simp.quirkademia.QuirkPlugin;

public abstract class Manager implements Listener {

	protected QuirkPlugin plugin;
	
	public Manager(QuirkPlugin plugin) {
		this.plugin = plugin;
		
		final Manager man = this;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				plugin.getManagersRunnable().registerManager(man);
			}
			
		}.runTaskLater(plugin, 60);
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	public abstract void run();
}
