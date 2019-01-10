package me.simp.quirkademia.quirk;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import me.simp.quirkademia.QuirkPlugin;

public class QuirkStamina {

	protected QuirkPlugin plugin;
	
	private QuirkUser user;
	private String name;
	private BarColor color;
	private double maxStamina, recharge, stamina;
	private StaminaBar bar;
	
	public QuirkStamina(QuirkUser user, String name, BarColor color, double maxStamina, double recharge) {
		this.plugin = QuirkPlugin.get();
		this.user = user;
		this.name = name;
		this.color = color;
		this.maxStamina = maxStamina;
		this.recharge = recharge;
		this.stamina = maxStamina;
		this.bar = new StaminaBar(this);
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
	
	public double getMaxStamina() {
		return maxStamina;
	}
	
	public double getRecharge() {
		return recharge;
	}
	
	public double getStamina() {
		return stamina;
	}
	
	public QuirkStamina setStamina(double stamina) {
		if (stamina > maxStamina) {
			stamina = maxStamina;
		}
		
		this.stamina = stamina;
		this.bar.update();
		return this;
	}
	
	public class StaminaBar {
		
		private QuirkStamina stamina;
		private BossBar bar;
		
		public StaminaBar(QuirkStamina stamina) {
			this.stamina = stamina;
			this.bar = plugin.getServer().createBossBar(stamina.getName(), stamina.getColor(), BarStyle.SOLID);
		}
		
		public QuirkStamina getStamina() {
			return stamina;
		}
		
		public void update() {
			bar.setProgress(stamina.getStamina() / stamina.getMaxStamina());
		}
	}
}
