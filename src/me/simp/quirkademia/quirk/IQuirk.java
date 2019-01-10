package me.simp.quirkademia.quirk;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;

public interface IQuirk {

	public String getName();
	public String getDescription();
	public QuirkType getType();
	public ChatColor getChatColor();
	public String getStaminaTitle();
	public BarColor getStaminaColor();
	public int getStaminaMax();
	public int getStaminaRecharge();
}
