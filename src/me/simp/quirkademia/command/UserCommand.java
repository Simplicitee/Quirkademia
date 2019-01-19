package me.simp.quirkademia.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;

public class UserCommand extends QuirkCommand {

	public UserCommand() {
		super("user", "Command for anything dealing with quirkusers", "/quirk user [player [toggle | set <quirk>]]", new String[] {"user", "u", "player"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender, false)) {
			return;
		} else if (!acceptableLength(sender, args.size(), 0, 3)) {
			return;
		}
		
		if (args.size() == 0) {
			sender.sendMessage(ChatColor.YELLOW + "Online Quirk Users:");
			for (QuirkUser user : plugin.getUserManager().getOnlineUsers()) {
				ChatColor color = ChatColor.WHITE;
				
				if (user.getQuirk() != null) {
					color = user.getQuirk().getChatColor();
				}
				
				sender.sendMessage(color + Bukkit.getPlayer(user.getUniqueId()).getName());
			}
			return;
		}
		
		Player target = Bukkit.getPlayer(args.get(0));
		
		if (target == null) {
			sender.sendMessage(ChatColor.RED + "Player not online!");
		} else {
			QuirkUser user = plugin.getUserManager().getUser(target.getUniqueId());
			
			if (user == null) {
				user = plugin.getUserManager().login(target.getUniqueId());
			}
			
			if (args.size() == 1) {
				sendUserInfo(sender, user);
			} else if (args.size() == 2) {
				if (args.get(1).equalsIgnoreCase("toggle")) {
					String state = "";
					if (user.isQuirkDisabled()) {
						user.enableQuirk();
						state = ChatColor.GREEN + "ON";
					} else {
						user.disableQuirk();
						state = ChatColor.RED + "OFF";
					}
					
					String from = "";
					
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (!player.getUniqueId().equals(target.getUniqueId())) {
							from = " by " + player.getName() + " ";
						}
					} else {
						from = " by CONSOLE ";
					}
					
					target.sendMessage(ChatColor.YELLOW + "Your quirk has been toggled " + state + ChatColor.YELLOW + from + "!");
				}
			} else if (args.size() == 3) {
				if (args.get(1).equalsIgnoreCase("set")) {
					if (!sender.hasPermission("quirk.command.user.set")) {
						sender.sendMessage(ChatColor.RED + "You don't have permission to do that");
						return;
					}
					
					Quirk quirk = plugin.getQuirkManager().getQuirk(args.get(2).replace("-", " ").replace("_", " "));
					
					if (quirk == null) {
						sender.sendMessage(ChatColor.RED + "Unknown quirk!");
						return;
					}
					
					user.setQuirk(quirk);
					sender.sendMessage(ChatColor.YELLOW + "Set " + target.getName() + "'s quirk to " + quirk.getChatColor() + quirk.getName());
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (!player.getUniqueId().equals(target.getUniqueId())) {
							target.sendMessage(ChatColor.YELLOW + player.getName() + " has set your quirk to " + quirk.getChatColor() + quirk.getName());
						}
					} else {
						target.sendMessage(ChatColor.YELLOW + "CONSOLE has set your quirk to " + quirk.getChatColor() + quirk.getName());
					}
				}
			}
		}
	}
	

	public void sendUserInfo(CommandSender sender, QuirkUser user) {
		Player target = Bukkit.getPlayer(user.getUniqueId());
		Quirk quirk = user.getQuirk();
		String name;
		
		if (quirk == null) {
			name = ChatColor.WHITE + "Quirkless";
		} else {
			name = user.getQuirk().getChatColor() + user.getQuirk().getName();
		}
		
		sender.sendMessage(ChatColor.YELLOW + target.getName());
		sender.sendMessage(ChatColor.YELLOW + "- Quirk: " + name);
		sender.sendMessage(ChatColor.YELLOW + "- Abilities: ");
		
		if (quirk != null) {
			for (QuirkAbilityInfo info : user.getQuirk().getAbilities().values()) {
				sender.sendMessage(ChatColor.YELLOW + "-- " + user.getQuirk().getChatColor() + info.getName());
			}
		}
	}

	@Override
	public List<String> completer(CommandSender sender, List<String> args) {
		List<String> completions = new ArrayList<>();
		
		if (args.size() == 1) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				completions.add(player.getName());
			}
		} else if (args.size() == 2) {
			completions.addAll(Arrays.asList("set", "toggle"));
		} else if (args.size() == 3 && args.get(1).equalsIgnoreCase("set")) {
			for (Quirk quirk : plugin.getQuirkManager().getQuirks()) {
				completions.add(quirk.getName().replace(" ", "-").toLowerCase());
			}
		}
		
		return completions;
	}
}
