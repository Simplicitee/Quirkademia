package me.simp.quirkademia.ability;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.simp.quirkademia.GeneralMethods;
import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.manager.QuirkAbilityManager;
import me.simp.quirkademia.quirk.QuirkUser;

public abstract class QuirkAbility {
	
	protected QuirkPlugin plugin;
	protected GeneralMethods methods;
	protected QuirkAbilityManager manager;
	protected QuirkUser user;
	protected Player player;
	
	private UUID uuid;
	private long initialized;
	
	public QuirkAbility(QuirkUser user) {
		this.user = user;
		this.player = Bukkit.getPlayer(user.getUniqueId());
		this.plugin = QuirkPlugin.get();
		this.methods = plugin.getMethods();
		this.manager = plugin.getAbilityManager();
		this.uuid = UUID.randomUUID();
		this.initialized = System.currentTimeMillis();
	}
	
	public QuirkUser getUser() {
		return user;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	public UUID getUniqueId() {
		return uuid;
	}
	
	public long getInitializationTime() {
		return initialized;
	}
	
	public abstract Location getLocation();
	public abstract boolean progress();
	public abstract void onStart();
	public abstract void onRemove();
	
	public void damage(LivingEntity e, double damage) {
		e.damage(damage, player);
	}
}
