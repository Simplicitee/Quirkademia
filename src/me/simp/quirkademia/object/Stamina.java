package me.simp.quirkademia.object;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import me.simp.quirkademia.QuirkPlugin;

public class Stamina {

	protected QuirkPlugin plugin;
	
	private UUID user;
	private String name;
	private BarColor color;
	private int maxStamina, stamina;
	private StaminaBar bar;
	
	public Stamina(UUID user, String name, BarColor color, int stamina, int max) {
		this.plugin = QuirkPlugin.get();
		this.user = user;
		this.name = name;
		this.color = color;
		this.maxStamina = max;
		this.bar = new StaminaBar(this);
		
		setValue(stamina);
	}
	
	public UUID getUser() {
		return user;
	}
	
	public String getName() {
		return name;
	}
	
	public BarColor getColor() {
		return color;
	}
	
	public int getMaxStamina() {
		return maxStamina;
	}
	
	public StaminaBar getBar() {
		return bar;
	}
	
	public int getValue() {
		return stamina;
	}
	
	public Stamina setValue(int stamina) {
		if (stamina > maxStamina) {
			stamina = maxStamina;
		} else if (stamina < 0) {
			stamina = 0;
		}
		
		this.stamina = stamina;
		this.bar.update(Bukkit.getPlayer(user));
		return this;
	}
	
	public class StaminaBar {
		
		private Stamina stamina;
		private BossBar bar;
		
		public StaminaBar(Stamina stamina) {
			this.stamina = stamina;
			this.bar = plugin.getServer().createBossBar(stamina.getName() + " [ Loading ]", stamina.getColor(), BarStyle.SOLID);
		}
		
		public Stamina getStamina() {
			return stamina;
		}
		
		public void update(Player player) {
			bar.setTitle(stamina.getName() + " [" + ChatColor.GREEN + stamina.getValue() + ChatColor.WHITE + "/" + stamina.getMaxStamina() + ChatColor.WHITE + "]");
			bar.setProgress(((double) stamina.getValue()) / stamina.getMaxStamina());
			
			if (!bar.getPlayers().contains(player)) {
				bar.addPlayer(player);
			}
		}
		
		public void destroy(Player player) {
			this.bar.removePlayer(player);
		}
	}
}
