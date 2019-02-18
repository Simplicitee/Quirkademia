package me.simp.quirkademia.quirk;

import org.bukkit.ChatColor;

public interface IQuirk {

	public String getName();
	public String getDescription();
	public QuirkType getType();
	public ChatColor getChatColor();
}
