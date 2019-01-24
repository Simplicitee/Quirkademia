package me.simp.quirkademia.quirk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import me.simp.quirkademia.QuirkPlugin;

public class QuirkStamina {

	protected QuirkPlugin plugin;
	
	private QuirkUser user;
	private String name;
	private BarColor color;
	private int maxStamina, recharge, stamina;
	private StaminaBar bar;
	
	public QuirkStamina(QuirkUser user) {
		this.plugin = QuirkPlugin.get();
		this.user = user;
		this.name = user.getQuirk().getStaminaTitle();
		this.color = user.getQuirk().getStaminaColor();
		this.maxStamina = user.getQuirk().getStaminaMax();
		this.recharge = user.getQuirk().getStaminaRecharge();
		this.bar = new StaminaBar(this);
		
		setValue(maxStamina);
	}
	
	public QuirkUser getUser() {
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
	
	public int getRecharge() {
		return recharge;
	}
	
	public StaminaBar getBar() {
		return bar;
	}
	
	public int getValue() {
		return stamina;
	}
	
	public QuirkStamina setValue(int stamina) {
		if (stamina > maxStamina) {
			stamina = maxStamina;
		} else if (stamina < 0) {
			stamina = 0;
		}
		
		this.stamina = stamina;
		this.bar.update();
		return this;
	}
	
	public class StaminaBar {
		
		private QuirkStamina stamina;
		private BossBar bar;
		private Player player;
		
		public StaminaBar(QuirkStamina stamina) {
			this.stamina = stamina;
			this.player = Bukkit.getPlayer(stamina.getUser().getUniqueId());
			this.bar = plugin.getServer().createBossBar(stamina.getName() + " [ Loading ]", stamina.getColor(), BarStyle.SOLID);
			this.bar.addPlayer(player);
		}
		
		public QuirkStamina getStamina() {
			return stamina;
		}
		
		public void update() {
			bar.setTitle(stamina.getName() + " [" + ChatColor.GREEN + stamina.getValue() + ChatColor.WHITE + "/" + stamina.getMaxStamina() + ChatColor.WHITE + "]");
			bar.setProgress(((double) stamina.getValue()) / stamina.getMaxStamina());
		}
		
		public void destroy() {
			this.bar.removePlayer(player);
		}
	}
}
