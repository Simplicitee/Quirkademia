package me.simp.quirkademia.ability;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;

public abstract class QuirkAbility {
	
	protected QuirkPlugin plugin;
	protected QuirkUser user;
	protected Player player;
	
	private UUID uuid;
	private long initialized;
	
	public QuirkAbility(QuirkUser user) {
		this.user = user;
		this.player = Bukkit.getPlayer(user.getUniqueId());
		this.plugin = QuirkPlugin.get();
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
	
	public QuirkAbilityInfo getInfo() {
		return user.getQuirk().getAbilities().get(getActivation());
	}
	
	public abstract ActivationType getActivation();
	public abstract Location getLocation();
	public abstract boolean progress();
	public abstract void onStart();
	public abstract void onRemove();
}
