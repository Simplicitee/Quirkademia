package me.simp.quirkademia.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.simp.quirkademia.QuirkPlugin;

public abstract class QuirkCommand {
	
	protected QuirkPlugin plugin;
	private String name;
	private String description;
	private String properUse;
	private String[] aliases;
	
	public QuirkCommand(String name, String description, String properUse, String[] aliases) {
		this.plugin = QuirkPlugin.get();
		this.name = name;
		this.description = description;
		this.properUse = properUse;
		this.aliases = aliases;
	}
	
	public QuirkPlugin getPlugin() {
		return plugin;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getProperUse() {
		return properUse;
	}
	
	public String[] getAliases() {
		return aliases;
	}
	
	public abstract void execute(CommandSender sender, List<String> args);
	
	public boolean hasPermission(CommandSender sender, boolean playerOnly) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (player.hasPermission("quirk.admin")) {
				return true;
			} else if (player.hasPermission("quirk.command." + name)) {
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
				return false;
			}
		}
		
		if (playerOnly) {
			sender.sendMessage(ChatColor.RED + "Only a player can run that command!");
			return false;
		}
		
		return true;
	}
	
	public boolean acceptableLength(CommandSender sender, int length, int min, int max) {
		if (length > max) {
			sender.sendMessage(ChatColor.RED + "Too many args given!");
			return false;
		} else if (length < min) {
			sender.sendMessage(ChatColor.RED + "Too few args given!");
			return false;
		} 
		
		return true;
	}
}
