package me.simp.quirkademia.manager;

import java.util.HashSet;
import java.util.Set;

import me.simp.quirkademia.QuirkPlugin;

public class ManagersRunnable implements Runnable {

	private QuirkPlugin plugin;
	private Set<Manager> running;
	
	public ManagersRunnable(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.running = new HashSet<>();
		this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0, 1);
	}
	
	@Override
	public void run() {
		for (Manager man : running) {
			man.run();
		}
	}
	
	public boolean registerManager(Manager manager) {
		return running.add(manager);
	}
}
