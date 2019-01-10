package me.simp.quirkademia.command;

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
		super("user", "Command for anything dealing with quirkusers", "/quirk user <player> [args...]", new String[] {"user", "u", "player"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender, false)) {
			return;
		} else if (!acceptableLength(sender, args.size(), 1, 3)) {
			return;
		}
		
		Player target = Bukkit.getPlayer(args.get(0));
		
		if (target == null) {
			sender.sendMessage(ChatColor.RED + "Player not online!");
		} else {
			QuirkUser user = QuirkUser.from(target.getUniqueId());
			
			if (user == null) {
				user = QuirkUser.login(target.getUniqueId());
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
					Quirk quirk = Quirk.get(args.get(2).replace("-", " ").replace("_", " "));
					
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
}
