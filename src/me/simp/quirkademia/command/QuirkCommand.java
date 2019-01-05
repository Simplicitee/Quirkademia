package me.simp.quirkademia.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;

public class QuirkCommand implements CommandExecutor {
	
	public QuirkPlugin plugin;
	public List<String> aliases;
	
	public QuirkCommand(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.aliases = Arrays.asList("quirk", "q");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (aliases.contains(label.toLowerCase())) {
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("user") && args.length >= 2) {
					Player target = Bukkit.getPlayer(args[1]);
					
					if (target == null) {
						sender.sendMessage(ChatColor.RED + "Player not online!");
						return true;
					} else {
						if (args.length == 2) {
							sendUserInfo(sender, target);
						} else if (args.length == 4) {
							if (args[2].equalsIgnoreCase("set")) {
								Quirk quirk = Quirk.get(args[3].replace("-", " "));
								
								if (quirk == null) {
									sender.sendMessage(ChatColor.RED + "Unknown quirk!");
									return true;
								}
								
								QuirkUser user = QuirkUser.from(target.getUniqueId());
								
								if (user == null) {
									user = QuirkUser.login(target.getUniqueId());
								}
								
								user.setQuirk(quirk);
								sender.sendMessage(ChatColor.YELLOW + "Set " + target.getName() + "'s quirk to " + quirk.getChatColor() + quirk.getName());
							}
						}
						
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public void sendUserInfo(CommandSender sender, Player target) {
		QuirkUser user = QuirkUser.from(target.getUniqueId());
		
		sender.sendMessage(ChatColor.YELLOW + target.getName());
		sender.sendMessage(ChatColor.YELLOW + "- Quirk: " + user.getQuirk().getChatColor() + user.getQuirk().getName());
		sender.sendMessage(ChatColor.YELLOW + "- Abilities: ");
		for (QuirkAbilityInfo info : user.getQuirk().getAbilities().values()) {
			sender.sendMessage(ChatColor.YELLOW + "-- " + user.getQuirk().getChatColor() + info.getName());
		}
	}
}
